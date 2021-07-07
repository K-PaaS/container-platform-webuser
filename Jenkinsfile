pipeline {
	environment {
		IMAGE_NAME = "container-platform-webuser"
		REGISTRY_HARBOR_CREDENTIAL = 'harbor-credential'       
		KUBERNETES_CREDENTIAL = 'kubernetes-credential'
        REGISTRY_HARBOR_URL = "${HARBOR_URL}"
        PROJECT_NAME = "container-platform"
	}
	agent any
	stages {
		stage('Cloning Github') {
			steps {
				git branch: 'dev', url: 'https://github.com/PaaS-TA/paas-ta-container-platform-webuser.git'
			}
		}
		stage('Environment') {
            parallel {
                stage('wrapper') {
                    steps {
                        sh 'gradle wrapper'
                    }
                }
                stage('display') {
                    steps {
                        sh 'ls -la'
                    }
                }
            }
        }
		stage('copy config') {
			steps {
				sh 'cp /var/lib/jenkins/workspace/config/webuser/application.yml src/main/resources/application.yml'
			}
		}
		stage('Clean Build') {
            steps {
                sh './gradlew clean'
            }
        }
		stage('Build Jar') {
            steps {
                sh './gradlew build'
            }
        }
		stage('Building image') {
			steps{
				script {										
					harborImage = docker.build REGISTRY_HARBOR_URL+"/"+PROJECT_NAME+"/"+IMAGE_NAME+":latest"
                    harborVersionedImage = docker.build REGISTRY_HARBOR_URL+"/"+PROJECT_NAME+"/"+IMAGE_NAME+":$BUILD_NUMBER"
				}
			}
		}
		stage('Deploy Image') {
			steps{
				script {					
					docker.withRegistry("http://"+REGISTRY_HARBOR_URL, REGISTRY_HARBOR_CREDENTIAL)
                    {
                        harborImage.push()
                        harborVersionedImage.push()
                    }
				}
			}
		}
		stage('Kubernetes deploy') {
			steps {
				kubernetesDeploy (
					configs: "yaml/Deployment.yaml",					
					kubeconfigId: 'kubernetes-credential', 
					enableConfigSubstitution: true										
				)				
			}
		}		
		stage('Remove Unused docker image') {
			steps{				
                echo "REGISTRY_HARBOR_URL: $REGISTRY_HARBOR_URL"
                echo "PROJECT_NAME: $PROJECT_NAME"
                echo "IMAGE_NAME: $IMAGE_NAME"
                echo "BUILD_NUMBER: $BUILD_NUMBER"               
                sh "docker rmi $REGISTRY_HARBOR_URL/$PROJECT_NAME/$IMAGE_NAME:latest"
                sh "docker rmi $REGISTRY_HARBOR_URL/$PROJECT_NAME/$IMAGE_NAME:$BUILD_NUMBER"
			}
		}
	}
}