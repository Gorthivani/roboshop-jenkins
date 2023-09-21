def call(){
    node('workstation'){
        sh "find .| sed -e '1d'|xargs rm -rf  "
        sh 'env'
        if(env.TAG_NAME ==~ ".*"){
            env.branch_name="refs/tags/${env.TAG_NAME}"
        }else{
            env.branch_name="${env.BRANCH_NAME}"
        }

        checkout scmGit(
                branches: [[name: branch_name]],
                userRemoteConfigs: [[url: "https://github.com/Gorthivani/${component}"]]
        )

        //git "${BRANCH_NAME}":'main', url:"https://github.com/Gorthivani/${component}"
        stage('Compile code') {
            common.compile()
        }

        if(env.TAG_NAME == null) {

            stage('Test') {
                print 'Hello World'
            }

            stage('Cody Quality') {
                print 'Hello World'
            }
        }
        if(env.BRANCH_NAME == "main") {
            stage('Code Security') {
                print 'Hello World'
            }
        }
        if(env.TAG_NAME ==~ ".*") {
            stage('Release') {
                print 'Hello World'
            }
        }
    }
}