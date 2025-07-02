pipeline {
    agent {
        docker {
            image 'maven:3.9.6-eclipse-temurin-17'
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
    }

    environment {
        DOCKER_IMAGE = 'unitarias-app:6'
        KUBECONFIG_CREDENTIAL_ID = 'kubeconfig'
        DOCKER_HUB_CREDENTIAL_ID = 'docker-hub-credentials'
    }

    stages {
        stage('Build & Test') {
            steps {
                echo '🧪 Compilando y ejecutando pruebas...'
                sh 'mvn clean compile test'
            }
        }

        stage('Package') {
            steps {
                echo '📦 Empaquetando aplicación...'
                sh 'mvn package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                echo '🐳 Construyendo imagen Docker...'
                sh 'docker build -t $DOCKER_IMAGE .'
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                echo '🚀 Desplegando en Kubernetes...'
                withKubeConfig(credentialsId: "${KUBECONFIG_CREDENTIAL_ID}") {
                    sh 'kubectl apply -f k8s/deployment.yaml'
                    sh 'kubectl apply -f k8s/service.yaml'
                }
            }
        }
    }

    post {
        always {
            echo '🧹 Limpiando recursos Docker...'
            sh 'docker rmi $DOCKER_IMAGE || true'
            sh 'docker system prune -f || true'
        }
    }
}
