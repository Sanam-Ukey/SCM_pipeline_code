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
                sh '''mvn sonar:sonar \\
                     -Dsonar.projectKey=studentapp \\
                       -Dsonar.host.url=http://172.31.20.218:9000 \\
                       -Dsonar.login=644818a6f749cabd8f81d213a3d9c296ffa2002f'''
            }
        }
         stage('deploy-stage') {
            steps {
                echo 'code deploy sucessfully'
            }
        }
    }
}
