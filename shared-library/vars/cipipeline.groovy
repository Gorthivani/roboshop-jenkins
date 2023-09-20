def call(){
    node('workstation'){
        stage('Compile') {
            common.compile()
            print 'Hello World'
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