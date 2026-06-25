pipeline {
  agent any

  tools {
    maven 'MAVEN_3_9_11'
    jdk 'JDK_24'
  }

  environment {
    // CAMBIAR EN EL EXAMEN: coloca tu usuario real de Docker Hub.
    REGISTRY_USER = "marckszzz"

    // Nombre de la imagen de la aplicacion.
    IMAGE_NAME = "support-ticket-api"

    // Version de la imagen: usa el numero de build de Jenkins.
    TAG = "${env.BUILD_NUMBER}"
  }

  stages {
    stage('1. Compile Project') {
      steps {
        withMaven(maven: 'MAVEN_3_9_11') {
          sh 'mvn clean compile'
        }
      }
    }

    stage('2. Validate Checkstyle') {
      steps {
        withMaven(maven: 'MAVEN_3_9_11') {
          sh 'mvn checkstyle:check'
        }
      }
    }

    stage('3. Validate Unit Tests') {
      steps {
        withMaven(maven: 'MAVEN_3_9_11') {
          sh 'mvn test'
        }
      }
    }

    stage('4. Validate Test Coverage') {
      steps {
        withMaven(maven: 'MAVEN_3_9_11') {
          sh 'mvn clean verify jacoco:report'
          sh 'mvn jacoco:check'
        }
      }
    }

    stage('5. SonarQube Analysis') {
      steps {
        withSonarQubeEnv('MiSonarServer') {
          sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=support-ticket-api'
        }

        script {
          timeout(time: 10, unit: 'MINUTES') {
            def qg = waitForQualityGate()
            if (qg.status != 'OK') {
              error "El pipeline se detuvo porque el Quality Gate fallo. Estado: ${qg.status}"
            }
          }
        }
      }
    }

    stage('6. Construir y Publicar Imagen Docker') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'DOCKER_HUB_CREDENTIALS', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
          script {
            echo 'Iniciando sesion en Docker Hub...'
            sh "echo '${DOCKER_PASS}' | docker login -u '${DOCKER_USER}' --password-stdin"

            echo 'Construyendo y publicando imagen AMD64...'
            sh "docker buildx build --platform linux/amd64 -t ${REGISTRY_USER}/${IMAGE_NAME}:${TAG} -t ${REGISTRY_USER}/${IMAGE_NAME}:latest --push ."
          }
        }
      }
    }
  }
}
