def call(){
    node('workstation'){
        sh "find .| sed -e '1d'|xargs rm -rf  "
        git "${BRANCH_NAME}":'main', url:"https://github.com/Gorthivani/${component}"
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