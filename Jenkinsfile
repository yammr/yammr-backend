pipeline {
    agent {
        docker { image 'openjdk:8-jdk' }
    }
    stages {
        stage('Build') {
            steps {
                sh './gradlew build'
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }
    }
}
