pipeline {
    agent any
    stages {
        stage('build') {
            steps {
                sh 'cd changuii && ./gradlew clean build'
                sh 'ls'
            }
        }
        stage('key mv'){
            steps{
                sh 'sudo cp keystore.p12 changuii/src/main/resources/keystore.p12'
            }
        }
        stage('doker build'){
            steps{
                sh 'sudo docker build -t changuii/blog:latest .'
            }
        }
        stage('docker stop'){
            steps{
                sh 'sudo docker stop blogServer'
                sh 'sudo docker rm blogServer'
            }
        }
        stage('docker run'){
            steps{
                sh 'sudo docker run -d -p 8443:8443 --network changuii --name blogServer changuii/blog:latest'
            }
        }
    }
}
