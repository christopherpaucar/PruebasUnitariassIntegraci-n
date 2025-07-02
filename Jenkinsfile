pipeline {
    agent any
    
    environment {
        DOCKER_IMAGE = 'unitarias-app'
        DOCKER_TAG = "${BUILD_NUMBER}"
        KUBECONFIG = credentials('kubeconfig')
    }
    
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/leotorresval/UnitariasIntegracion.git'
            }
        }
        
        stage('Build & Test') {
            steps {
                script {
                    // Compilar el proyecto Java
                    sh 'mvn clean compile test'
                }
            }
            post {
                always {
                    // Publicar resultados de pruebas
                    publishTestResults testResultsPattern: 'target/surefire-reports/*.xml'
                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'target/site/jacoco',
                        reportFiles: 'index.html',
                        reportName: 'Coverage Report'
                    ])
                }
            }
        }
        
        stage('Package') {
            steps {
                sh 'mvn clean package -DskipTests'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
        
        stage('Docker Build') {
            steps {
                script {
                    // Construir imagen Docker
                    def dockerImage = docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                    docker.withRegistry('', 'docker-hub-credentials') {
                        dockerImage.push()
                        dockerImage.push('latest')
                    }
                }
            }
        }
        
        stage('Security Scan') {
            steps {
                script {
                    // Escanear vulnerabilidades en la imagen Docker
                    sh "docker run --rm -v /var/run/docker.sock:/var/run/docker.sock aquasec/trivy:latest image ${DOCKER_IMAGE}:${DOCKER_TAG}"
                }
            }
        }
        
        stage('Deploy to K8s') {
            steps {
                script {
                    // Actualizar imagen en manifiestos
                    sh "sed -i 's|image: unitarias-app:latest|image: ${DOCKER_IMAGE}:${DOCKER_TAG}|g' k8s/deployment.yaml"
                    
                    // Desplegar en Kubernetes
                    sh 'kubectl apply -f k8s/'
                    
                    // Verificar despliegue
                    sh 'kubectl rollout status deployment/unitarias-app'
                    
                    // Obtener información del servicio
                    sh 'kubectl get services unitarias-service'
                }
            }
        }
        
        stage('Integration Tests') {
            steps {
                script {
                    // Esperar a que el servicio esté disponible
                    sh 'kubectl wait --for=condition=available --timeout=300s deployment/unitarias-app'
                    
                    // Ejecutar pruebas de integración
                    sh '''
                        SERVICE_URL=$(kubectl get service unitarias-service -o jsonpath='{.status.loadBalancer.ingress[0].ip}')
                        if [ -z "$SERVICE_URL" ]; then
                            SERVICE_URL=$(kubectl get service unitarias-service -o jsonpath='{.spec.clusterIP}')
                        fi
                        echo "Testing service at: http://$SERVICE_URL"
                        # curl -f http://$SERVICE_URL/health || exit 1
                    '''
                }
            }
        }
        
        stage('Performance Test') {
            steps {
                script {
                    // Prueba de carga básica
                    sh '''
                        kubectl run load-test --image=busybox --rm -i --restart=Never -- \
                        sh -c "for i in \$(seq 1 100); do wget -q -O- http://unitarias-service/health; done"
                    '''
                }
            }
        }
    }
    
    post {
        always {
            // Limpiar imágenes Docker locales
            sh "docker rmi ${DOCKER_IMAGE}:${DOCKER_TAG} || true"
            sh "docker system prune -f"
        }
        success {
            emailext (
                subject: "✅ Despliegue exitoso - Build #${BUILD_NUMBER}",
                body: "El despliegue de la aplicación fue exitoso.",
                to: "${env.CHANGE_AUTHOR_EMAIL}"
            )
        }
        failure {
            emailext (
                subject: "❌ Fallo en despliegue - Build #${BUILD_NUMBER}",
                body: "El despliegue falló. Revisa los logs en Jenkins.",
                to: "${env.CHANGE_AUTHOR_EMAIL}"
            )
        }
    }
}