pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'unitarias-app:6'
        KUBECONFIG_CREDENTIAL_ID = 'kubeconfig'
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
            echo '🧹 Limpiando imagen Docker...'
            sh 'docker rmi $DOCKER_IMAGE || true'
            sh 'docker system prune -f || true'
        }
    }
}
