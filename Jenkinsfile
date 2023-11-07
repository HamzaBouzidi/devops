pipeline {
    agent any
// El Boss v2
    environment {
         MVN_HOME = tool 'Maven' // Replace 'M3' with the name of your actuald Maven tool
        NODEJS_HOME = 'NodeJS' // Reeplace with the actual path to Node.js if you need to define it manually

   
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
        withCredentials([usernamePassword(credentialsId: 'b556b19d-561b-4293-be6d-53c092fff139', usernameVariable: 'admin', passwordVariable: 'admin')]) {
            dir('DevOps_Backend') {
                script {
                    sh "${MVN_HOME}/bin/mvn deploy -DskipTests -Dusername=$NEXUS_USERNAME -Dpassword=$NEXUS_PASSWORD"
                }
            }
        }
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
