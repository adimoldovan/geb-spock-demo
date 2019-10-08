pipeline {
    agent {label 'host'}
    stages {
        stage('Pull code and images') {
            steps {
                git 'https://github.com/adimoldovan/geb-spock-demo.git'
                sh 'docker pull selenium/hub'
                sh 'docker pull selenium/node-chrome-debug'
            }
        }
        stage('Start the grid') {
            steps {
                sh 'docker network create grid'
                sh 'docker run --rm -d -p 4444:4444 --net grid --name selenium-hub selenium/hub'
                sh 'docker run --rm -d -p 5900:5900 --net grid -e HUB_HOST=selenium-hub -v /dev/shm:/dev/shm --name=selenium-node-chrome selenium/node-chrome-debug'
            }
        }
        stage('Run tests') {
            steps {
                sh 'mvn clean test -Dsleep=0 -Dgeb.env=grid'
            }
        }
    }
    post {
        always {
            sh 'docker container stop $(docker container ls -q --filter name=selenium*)'
            sh 'docker network rm grid'
        }
    }
}