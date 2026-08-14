pipeline {

    agent any

    environment {
        ACR_NAME = 'snowmanacr'
        ACR_LOGIN_SERVER = 'snowmanacr.azurecr.io'

        IMAGE_NAME = 'monolith-enterprise-application'
        IMAGE_TAG = "${BUILD_NUMBER}"

        CONTAINER_NAME = 'snowman-container'

        HOST_PORT = '8090'
        CONTAINER_PORT = '8090'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'master',
                    url: 'https://github.com/Sajini-Sayyad/monolith-enterprise-application.git'
            }
        }

        stage('Maven Clean Package') {
            steps {
                sh '''
                    mvn clean package -DskipTests -Dliquibase.skip=true
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                    docker build \
                    -t ${IMAGE_NAME}:${IMAGE_TAG} \
                    .
                '''
            }
        }

        stage('Run Container') {
            steps {
                sh '''
                    docker rm -f ${CONTAINER_NAME} || true

                    docker run -d \
                    --name ${CONTAINER_NAME} \
                    -p ${HOST_PORT}:${CONTAINER_PORT} \
                    ${IMAGE_NAME}:${IMAGE_TAG}

                    sleep 10

                    docker ps
                '''
            }
        }

        stage('Login to ACR') {
            steps {
                sh '''
                    az acr login --name ${ACR_NAME}
                '''
            }
        }

        stage('Tag Image') {
            steps {
                sh '''
                    docker tag \
                    ${IMAGE_NAME}:${IMAGE_TAG} \
                    ${ACR_LOGIN_SERVER}/${IMAGE_NAME}:${IMAGE_TAG}
                '''
            }
        }

        stage('Push Image to ACR') {
            steps {
                sh '''
                    docker push \
                    ${ACR_LOGIN_SERVER}/${IMAGE_NAME}:${IMAGE_TAG}
                '''
            }
        }

        stage('Cleanup') {
            steps {
                sh '''
                    docker rm -f ${CONTAINER_NAME} || true

                    docker rmi ${IMAGE_NAME}:${IMAGE_TAG} || true

                    docker rmi \
                    ${ACR_LOGIN_SERVER}/${IMAGE_NAME}:${IMAGE_TAG} || true

                    docker image prune -f
                '''
            }
        }
    }

    post {
        success {
            echo "Pipeline completed successfully!"
            echo "Image pushed to ACR:"
            echo "${ACR_LOGIN_SERVER}/${IMAGE_NAME}:${IMAGE_TAG}"
        }

        failure {
            echo "Pipeline failed. Check the stage logs."
        }
    }
}
