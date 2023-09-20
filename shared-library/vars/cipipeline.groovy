def call(){
    pipeline {
        agent any

        stages {
            stage('Compile') {
                steps {
                    sh 'env'
                }
            }


            stage('Test') {
                steps {
                    echo 'Hello World'
                }
            }

            stage('Code Quality') {
                steps {
                    echo 'Hello World'
                }
            }
            stage('Code Security') {
               when{

                }
            }
            stage('Release') {
                steps {
                    echo 'Hello World'
                }
            }
        }
    }
}