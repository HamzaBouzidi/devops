pipeline {
    agent any
//just for trigger test
    environment {
        MVN_HOME = tool 'Maven' // Make sure 'Maven' is the name of the tool configured in Jenkins
        NODEJS_HOME = tool 'NodeJS' // Make sure 'NodeJS' is the name of the tool configured in Jenkins
        NEXUS_USER = 'admin'
        NEXUS_PASSWORD = 'admin'
        SNAP_REPO = 'devopsproject-snapshot'
        RELEASE_REPO = 'devopsproject-release'
        CENTRAL_REPO = 'devopsproject-central-repo'
        NEXUS_GRP_REPO = 'devopsproject-grp-repo'
        NEXUS_IP = 'localhost'
        NEXUS_PORT = '8081'
        NEXUS_LOGIN = 'b556b19d-561b-4293-be6d-53c092fff139'
        
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Unit Test') {
            steps {
                dir('DevOps_Backend') {
                    script {
                        sh "${MVN_HOME}/bin/mvn clean test"
                    }
                }
            }
        }
        stage('JaCoCo Results') {
            steps {
                script {
                    def jacocoReportPath = 'DevOps_Backend/target/site/jacoco'

                    publishHTML(
                        target: [
                            allowMissing: false,
                            alwaysLinkToLastBuild: true,
                            keepAll: true,
                            reportDir: jacocoReportPath,
                            reportFiles: 'index.html',
                            reportName: 'JaCoCo Code Report'
                        ]
                    )
                }
            }
        }

        stage('Build Backend') {
            steps {
                dir('DevOps_Backend') {
                    script {
                        sh "${MVN_HOME}/bin/mvn clean package"
                    }
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
            post {
        success {
            mail to: 'hamza.bouzidi@esprit.tn',
                 subject: "Build Backend",
                 body: "Build Backend succeeded."
        }
        failure {
            mail to: 'hamza.bouzidi@esprit.tn',
                 subject: "Build Backend",
                 body: "Build Backend failed."
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
            // post {
            //     success {
            //         echo 'Frontend build successful.'
            //     }
            //     failure {
            //         echo 'Frontend build failed.'
            //     }
            // }
        }

        
        

        stage('Deploy to Nexus') {
            steps {
                script {
                    def artifactFile = "DevOps_Backend/target/DevOps_Project-1.0.jar" // Replace with the actual artifact name pattern
                    nexusArtifactUploader(
                        nexusVersion: 'nexus3',
                        protocol: 'http',
                        nexusUrl: "${NEXUS_IP}:${NEXUS_PORT}",
                        groupId: 'QA',
                        version: "${env.BUILD_ID}-${new Date().format('yyyyMMddHHmmss')}", // Correct timestamp format
                        repository: "${RELEASE_REPO}",
                        credentialsId: "${NEXUS_LOGIN}",
                        artifacts: [
                            [artifactId: 'DevOps_Project',
                             classifier: '',
                             file: artifactFile,
                             type: 'jar']
                        ]
                    )
                }
            }
        }

    stage('SonarQube Analysis') {
    steps {
        script {
            // Checkout the source code from GitHub
            checkout scm
            
            def scannerHome = tool 'SonarQubeScanner'
            withSonarQubeEnv('SonarQube') {
                sh """
                    ${scannerHome}/bin/sonar-scanner \
                    -Dsonar.projectKey=devopsproject \
                    -Dsonar.java.binaries=DevOps_Backend/target/classes \
                    -Dsonar.coverage.jacoco.xmlReportPaths=DevOps_Backend/target/site/jacoco/jacoco.xml
                """
            }
        }
    }
}

stage('Build Docker Images') {
    steps {
        script {
            
            dir('DevOps_Backend') {
                docker.build("hamzabouzidi/devopsproject", "-f /var/lib/jenkins/workspace/projetDevOps/DevOps_Backend/Dockerfile .")
            }

            // dir('DevOps_Front') {
            //     docker.build("hamzabouzidi/devopsfrontend", "-f /var/lib/jenkins/workspace/projetDevOps/DevOps_Front/Dockerfile .")
            // }
        }
    }
}
       stage('Push image to Hub') {
    steps {
        script {
            withCredentials([string(credentialsId: 'docker-hub-credentials-id', variable: 'DOCKER_HUB_PASSWORD')]) {
                dir('DevOps_Backend') {
                    sh "docker login -u hamzabouzidi -p ${DOCKER_HUB_PASSWORD}"
                    sh "docker push hamzabouzidi/devopsproject"
                }
            }
        }
    }
}
      
         stage('Build and Deploy') {
            steps {
                script {
                    sh '/usr/bin/docker-compose -f /var/lib/jenkins/workspace/projetDevOps/docker-compose.yml up -d'
                }
            }
}
        stage('Prometheus / Grafana') {
            steps {
                script {
                    sh 'docker start prometheus'
                    sh 'docker start grafana'
                }
            }
        }
        
    }


    

    

 

    post {
        always {
            cleanWs()
        }
    }
}
