def compile() {
    if (env.codeType == "python" || env.codeType == "static") {
        return "Return, Do not need Compilation"
    }
    stage('Compile Code') {
        if (env.codeType = "maven") {
            sh '/home/centos/maven/bin/mvn package' //download the packages maven
        }
        if (env.codeType = "nodejs") {
            sh 'npm install'
        }

    }
}
def test() {
    stage('Test Cases') {
        if (env.codeType = "maven") {
            sh '/home/centos/maven/bin/mvn test' //download the packages maven
        }
        if (env.codeType = "nodejs") {
            sh 'npm test'
        }
        if (env.codeType = "python") {
            sh 'python3.6 -m unittest'
        }
    }
}
def codeQuality() {
    stage('Code Quality') {
        env.sonaruser = sh(script: 'aws ssm get-parameter --name "sonarqube.user" --with-decryption --query="Parameter.Value" |xargs', returnStdout: true).trim()
        env.sonarpass = sh(script: 'aws ssm get-parameter --name "sonarqube.pass" --with-decryption --query="Parameter.Value" |xargs', returnStdout: true).trim()
        wrap([$class: "MaskPasswordsBuildWrapper", varPasswordPairs: [[password: sonarpass]]]) {
            if (env.codeType = "maven"){
                sh 'sonar-scanner -Dsonar.host.url=http://172.31.89.117:9000 -Dsonar.login=${sonaruser} -Dsonar.password=${sonarpass} -Dsonar.projectKey=${component} -Dsonar.qualitygate.wait=true -Dsonar-java.binaries=./target'
            }else{
                sh 'sonar-scanner -Dsonar.host.url=http://172.31.89.117:9000 -Dsonar.login=${sonaruser} -Dsonar.password=${sonarpass} -Dsonar.projectKey=${component} -Dsonar.qualitygate.wait=true'
            }

        }
    }
}
def codeSecurity() {
    stage('Code Security') {
        print 'Code Security'
    }
}
def release() {
    stage('Release') {
        env.nexususer = sh(script: 'aws ssm get-parameter --name "nexus.username" --with-decryption --query="Parameter.Value" |xargs', returnStdout: true).trim()
        env.nexuspass = sh(script: 'aws ssm get-parameter --name "nexus.password" --with-decryption --query="Parameter.Value" |xargs', returnStdout: true).trim()
        wrap([$class: "MaskPasswordsBuildWrapper", varPasswordPairs: [[password: nexuspass]]]) {
            sh 'echo ${TAG_NAME} >VERSION'
            if(env.codeType == "nodejs") {
                sh 'zip -r ${component}-${TAG_NAME}.zip server.js node_modules VERSION ${schemadir}'
            } else if(env.codeType == "maven") {
                sh 'cp target/${component}-1.0.jar ${component}.jar; zip -r ${component}-${TAG_NAME}.zip ${component}.jar VERSION ${schemadir}'
            } else {
                sh 'zip -r ${component}-${TAG_NAME}.zip *'
            }

            sh 'curl -v -u ${nexususer}:${nexuspass} --upload-file ${component}-${TAG_NAME}.zip http://172.31.24.24:8081/repository/${component}/${component}-${TAG_NAME}.zip'
        }
    }
}

