pipeline {
    agent {
            docker {
                image 'docker:24.0-cli'  // Docker CLI image
                args '-v /var/run/docker.sock:/var/run/docker.sock'
            }
        }

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timeout(time: 30, unit: 'MINUTES')
    }

    tools {
            maven 'Maven'
        }

    environment {
            IMAGE_NAME = "back-api"
            IMAGE_TAG = "latest"
            DOCKER_REGISTRY = "oulhent/back-api"
        }

    stages {
        stage('Checkout') {
            steps {
                echo '===== Checkout du projet depuis GitHub ====='
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/master']],
                    userRemoteConfigs: [[
                        url: 'https://github.com/aoulhent/microservices.git'
                    ]]
                ])
                sh 'echo "Repository cloning successful"'
            }
        }

        stage('Build') {
            steps {
                echo '===== Construction du projet ====='
                sh 'mvn clean package -DskipTests'
            }
        }

//         stage('Test') {
//             steps {
//                 echo '===== Exécution des tests ====='
//                 sh 'mvn test'
//             }
//         }

        stage('Build Docker Image') {
            steps {
                sh """
                docker build -t $IMAGE_NAME:$IMAGE_TAG .
                """
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'docker-credentials',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh """
                    echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                    docker push $IMAGE_NAME:$IMAGE_TAG
                    """
                }
            }
        }
    }

    post {
        always {
            echo '===== Pipeline terminé ====='
            cleanWs()
        }
        success {
            echo '✓ Build et tests réussis!'
        }
        failure {
            echo '✗ Build ou tests échoués!'
        }
    }
}

