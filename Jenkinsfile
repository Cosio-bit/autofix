pipeline {
    agent any
    tools {
        maven "maven"
    }
    stages {
        stage("Build JAR File") {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], userRemoteConfigs: [[url: 'https://github.com/cosio-bit/autofix']]])
                bat "mvn clean install"
            }
        }
        stage("Test") {
            steps {
                bat "mvn test"
            }
        }
        stage("Build and Push Docker Image") {
            steps {
                script {
                    withDockerRegistry([credentialsId: 'docker-credentials']) {
                        bat "docker build -t cosiobit/autofix-backend ."
                        bat "docker push cosiobit/autofix-backend"
                    }
                }
            }
        }
    }
}
