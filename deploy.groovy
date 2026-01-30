pipeline {
    agent { label 'slave' }

    stages {

        stage('git-pull-stage') {
            steps {
                git branch: 'main', url: 'https://github.com/Sanam-Ukey/student-ui-app.git'
            }
        }

        stage('build-stage') {
            steps {
                sh '/opt/maven/bin/mvn clean package'
            }
        }

        stage('test-stage') {
            steps {
                withSonarQubeEnv(installationName: 'sonar', credentialsId: 'sonar-cred') {
                    sh '/opt/maven/bin/mvn clean verify sonar:sonar'
                }
            }
        }

        stage('Quality_Gate') {
            steps {
                timeout(2) {
   
                }
                waitForQualityGate true
            }
        }

        stage('deploy-stage') {
            steps {
                deploy adapters: [tomcat9(alternativeDeploymentContext: '', credentialsId: 'tomcat-cred', path: '', url: 'http://54.169.146.128:8080/')], contextPath: '/', war: '**/*.war'
            }
        }
    }
}
