pipeline {
    agent any
    environment {
        DOCKER_CREDENTIALS = 'docker-credentials'
        DOCKER_IMAGE_NAME = 'cosiobit/autofix-backend'
    }
    tools {
        maven "maven" // Assuming "maven" is a valid tool installation in Jenkins
    }
    stages {
        stage("Build JAR File") {
            steps {
                script {
                    checkout scm
                    bat "mvn clean install" // Changed "maven" to "mvn"
                }
            }
        }
        stage("Test") {
            steps {
                script {
                    bat "mvn test" // Changed "maven" to "mvn"
                }
            }
        }
        stage("Build and Push Docker Image") {
            steps {
                script {
                    withDockerRegistry(credentialsId: DOCKER_CREDENTIALS, url: "") {
                        bat "docker build -t ${DOCKER_IMAGE_NAME} ."
                        bat "docker push ${DOCKER_IMAGE_NAME}"
                    }
                }
            }
        }
    }
}
