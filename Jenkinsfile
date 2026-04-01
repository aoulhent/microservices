pipeline {
    agent any

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timeout(time: 30, unit: 'MINUTES')
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

        stage('Test') {
            steps {
                echo '===== Exécution des tests ====='
                sh 'mvn test'
            }
        }

        stage('Archive Results') {
            steps {
                echo '===== Archivage des résultats ====='
                junit 'target/surefire-reports/**/*.xml'
                archiveArtifacts artifacts: 'target/*.jar',
                                  allowEmptyArchive: true
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

