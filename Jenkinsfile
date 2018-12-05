pipeline {
    agent {
        docker { image '4ndr3w/java-8-aws-base' }
    }

    environment {
        AWS_ACCESS_KEY_ID = credentials('GIBGAB_AWS_KEY_ID')
        AWS_SECRET_ACCESS_KEY = credentials('GIBGAB_AWS_SECRET')
    }

    stages {
        stage('Build') {
            steps {
                sh './gradlew build'
            }
        }
/*        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }*/

        stage('Deploy') {
            when {
                branch 'master'
            }
            steps {
                sh 'eb deploy Gibgab-env'
            }
        }
    }
}
