pipeline {
    agent any
// El Boss v4
    environment {
         MVN_HOME = tool 'Maven' // Replace 'M3' with the name of your actuald Maven tool
        NODEJS_HOME = 'NodeJS' // Reeplace with the actual path to Node.js if you need to define it manually
        NEXUS_USER ='admin'
        NEXUS_PASSWORD ='admin'
        SNAP_REPO ='devopsproject-snapshot'
        RELEASE_REPO ='devopsproject-release'
        CENTRAL_REPO ='devopsproject--central-repo'
        NEXUS_GRP_REPO ='devopsproject--grp-repo'
        NEXUS_IP='localhost'
        NEXUS_PORT='8081'
        NEXUS_LOGIN ='6f548789-434c-461e-b179-a1b41fe5eb43'
        

   
}

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from the source control
               checkout scm
            }
        }

      stage('Unit Test') {
            steps {
                dir('DevOps_Backend') { // Change to your backend directory
                    script {
                        sh 'mvn clean test'
                    }
                }
            }
        
        }

        stage('Build Backend') {
            steps {
                dir('DevOps_Backend') { // Change to your backend directory
                    script {
                        sh '${MVN_HOME}/bin/mvn clean package'
                    }
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Build Frontend') {
            steps {
                dir('DevOps_Front') {
                    echo 'Installing dependencies...'
                    sh 'npm install'
                    echo 'Building Angular project...'
                    sh 'ng build'
                }
            }
            post {
                success {
                    echo 'Frontend build successful.'
                }
                failure {
                    echo 'Frontend build failed.'
                    // Handle failure (e.g., send notifications, perform cleanup, etc.)
                }
            }
        }

        stage('Deploy to Nexus') {
            steps {
                nexusArtifactUploader(
                nexusVersion: 'nexus3',
                protocol: 'http',
                nexusUrl: "${NEXUS_IP}:${NEXUS_PORT}",
                groupId: 'QA',
                version: "${env.BUILD_ID}-${env.BUILD_TIMESTEMP}",
                repository: "${RELEASE_REPO}",
                credentialsId: "${NEXUS_LOGIN}",
                artifacts: [
                    [artifactId: 'DevopsProject',
                     classifier: '',
                     file: 'my-service-' + version + '.jar',
                     type: 'jar']
                ]
                 )
                }
        }


    

   
    }

    post {
        always {
            // Perform some cleanup
            cleanWs()
        }
    }
}
