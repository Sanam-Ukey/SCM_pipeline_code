pipeline {
    agent { label 'slave' }
    stages {
        stage('git-pull-stage') {
            steps {
                git branch: 'main', url: 'https://github.com/Anilbamnote/student-ui-app.git'
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
                     sh '/opt/maven/bin/mvn sonar:sonar'
                 }
              //  sh '''/opt/maven/bin/mvn sonar:sonar \\
             //    -Dsonar.projectKey=newstudentapp \\
               //       -Dsonar.host.url=http://18.141.224.169:9000 \\
                 //     -Dsonar.login=19fdca4e72f16ff41dd126cec2dda68d2735e43c'''
            }
        }
         stage('deploy-stage') {
            steps {
                echo 'code deploy sucessfully'
            }
        }
    }
}
