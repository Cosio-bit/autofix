pipeline{
    agent any
    tools{
        maven "maven"

    }
    stages{
        stage("Build JAR File"){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/cosio-bit/autofix']])
                dir(""){
                    bat "mvn clean install"
                }
            }
        }
        stage("Test"){
            steps{
                dir(""){
                    bat "mvn test"
                }
            }
        }
        stage("Build and Push Docker Image"){
            steps{
                dir(""){
                    script{
                         withDockerRegistry(credentialsId: 'docker-credentials'){
                            bat "docker build -t cosiobit/autofix-backend ."
                            bat "docker push cosiobit/autofix-backend"
                        }
                    }
                }
            }
        }
    }
}