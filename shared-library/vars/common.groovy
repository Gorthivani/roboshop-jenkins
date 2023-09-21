def compile(){
    if(env.codeType= "maven"){
        sh '/home/centos/maven/bin/mvn package' //download the packages maven
    }
    if(env.codeType= "nodejs"){
        print 'Nodejs'
    }
    if(env.codeType= "python"){
        print 'Python'
    }
    if(env.codeType= "static"){
        print 'Static'
    }
}
