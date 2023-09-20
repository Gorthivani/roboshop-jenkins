def call(){
    node('workstation'){
        stage('Compile code') {
            common.compile()

        }
        stage('Test') {
            print 'Hello World'
        }
        stage('Cody Quality') {
            print 'Hello World'
        }
        stage('Code Security') {
            print 'Hello World'
        }
        stage('Release') {
            print 'Hello World'
        }
    }
}