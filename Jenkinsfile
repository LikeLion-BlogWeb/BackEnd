pipeline {
    agent any
    stages {
        // 프로젝트 빌드
        stage('build') {
            steps {
                sh 'cd changuii && ./gradlew clean build'
                sh 'ls'
            }
        }
        // SSL
        stage('key mv'){
            steps{
                sh 'sudo cp keystore.p12 changuii/src/main/resources/keystore.p12'
            }
        }
        // latest 태그로 빌드
        stage('doker build'){
            steps{
                sh 'sudo docker build -t changuii/blog:latest .'
            }
        }
        // 기존에 실행중이던 컨테이너 stop 후 삭제
        stage('docker stop'){
            steps{
                sh 'sudo docker stop blogServer'
                sh 'sudo docker rm blogServer'
            }
        }
        // docker container 실행
        stage('docker run'){
            steps{
                sh 'sudo docker run -d -p 8443:8443 --network changuii --name blogServer changuii/blog:latest'
            }
        }
    }
}
