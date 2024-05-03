pipeline {
    agent any
    tools {
        maven "maven"
    }
    stages {
        stage("Build JAR File") {
            steps {
                checkout scm
                script {
                    bat "mvn clean install"
                }
            }
        }
        stage("Test") {
            steps {
                script {
                    bat "mvn test"
                }
            }
        }
        stage("Build and Push Docker Image") {
            steps {
                script {
                    withDockerRegistry(credentialsId: 'docker-credentials') {
                        bat "docker build -t cosiobit/autofix-backend ."
                        bat "docker push cosiobit/autofix-backend"
                    }
                }
            }
        }
    }
}
