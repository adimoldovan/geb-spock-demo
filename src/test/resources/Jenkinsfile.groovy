pipeline {
    agent { label 'host' }
    stages {
        stage('Set up') {
            parallel {
                stage('Checkout Code') {
                    steps {
                        git 'https://github.com/adimoldovan/geb-spock-demo.git'
                    }
                }
                stage('Pull images') {
                    steps {
                        sh 'docker pull selenium/hub'
                        sh 'docker pull selenium/node-chrome-debug'
                    }
                }
            }
        } stage('Start Grid') {
            steps {
                sh 'docker network create grid'
                sh 'docker run --rm -d -p 4444:4444 --net grid --name selenium-hub selenium/hub'
                sh 'docker run --rm -d -p 5900:5900 --net grid -e HUB_HOST=selenium-hub -v /dev/shm:/dev/shm --name=selenium-node-chrome selenium/node-chrome-debug'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn clean test -Dsleep=5000 -Dgeb.env=grid'
            }
        }


    }
    post('Clean up') {
        always {
            sh 'docker container stop $(docker container ls -q --filter name=selenium*)'
            sh 'docker network rm grid'
        }
    }

}