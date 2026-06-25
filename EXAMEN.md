# Examen de practica: Jenkins + SonarQube + Docker

## Contexto

La empresa ficticia EduHelp necesita desplegar una API llamada `support-ticket-api` para registrar tickets de soporte academico. El equipo de desarrollo ya entrego el codigo fuente en Java con Spring Boot, pero falta automatizar el proceso de integracion continua.

Tu tarea es preparar el proyecto para que Jenkins compile, pruebe, analice en SonarQube y publique la imagen Docker en Docker Hub.

## Datos del examen

- Network Docker: `devops-ticket-net`
- Jenkins container: `jenkins-exam`
- Jenkins URL navegador: `http://localhost:9091`
- SonarQube container: `sonarqube-exam`
- SonarQube URL navegador: `http://localhost:9001`
- SonarQube URL interna para Jenkins: `http://sonarqube-exam:9000`
- Webhook de SonarQube a Jenkins: `http://jenkins-exam:9091/sonarqube-webhook/`
- SonarQube server name en Jenkins: `MiSonarServer`
- Credencial SonarQube en Jenkins: `sonarqube-token-id`
- Credencial Docker Hub en Jenkins: `DOCKER_HUB_CREDENTIALS`
- Imagen Docker: `<tu-usuario-dockerhub>/support-ticket-api:<build-number>`

## Requerimiento 1: Fork y evidencia GitHub

1. Crea o usa tu repositorio GitHub.
2. Sube este proyecto.
3. Evidencia requerida: captura de la raiz del repositorio mostrando `Dockerfile`, `Jenkinsfile`, `pom.xml` y carpeta `src`.

## Requerimiento 2: Dockerfile

El profesor te entrega el Dockerfile base con JDK 24.

Modifica el Dockerfile solo si en el examen te piden otra version de JDK.

Ejemplo: si te piden JDK 21, debes cambiar:

```dockerfile
FROM maven:3.9.11-eclipse-temurin-21-noble AS build
FROM eclipse-temurin:21-jre-noble
```

Evidencia requerida:

- Captura del Dockerfile.
- Captura de `docker images` donde se vea tu imagen.

## Requerimiento 3: Jenkinsfile

Completa o modifica el `Jenkinsfile` para que tenga estos stages:

1. Compile Project
2. Validate Checkstyle
3. Validate Unit Tests
4. Validate Test Coverage
5. SonarQube Analysis
6. Construir y Publicar Imagen Docker

Debes cambiar:

- Usuario de Docker Hub.
- Nombre de imagen si el profesor lo pide.
- Nombre del servidor SonarQube si el profesor cambia `MiSonarServer`.
- Version de JDK si el profesor cambia `JDK_24`.

Evidencia requerida:

- Captura del Jenkinsfile.
- Captura del pipeline ejecutado en verde.

## Requerimiento 4: SonarQube

1. Genera un token en SonarQube.
2. Registra el token en Jenkins como `sonarqube-token-id`.
3. Configura el servidor SonarQube en Jenkins con el nombre `MiSonarServer`.
4. Configura el webhook en SonarQube hacia Jenkins.

Evidencia requerida:

- Captura del proyecto en SonarQube.
- Captura del Quality Gate.
- Captura del Webhook Delivery Log exitoso.

## Requerimiento 5: Docker Hub

1. Genera token de Docker Hub.
2. Registra la credencial en Jenkins con ID `DOCKER_HUB_CREDENTIALS`.
3. Ejecuta el pipeline.
4. Verifica que la imagen se publique en Docker Hub.

Evidencia requerida:

- Captura de Docker Hub con la imagen publicada.
- Captura del stage de Docker en Jenkins.

## Variante sorpresa del profesor

El profesor puede cambiar una de estas variables:

| Cambio sorpresa | Donde modificar |
|---|---|
| JDK 21, 24, 25 o 26 | `pom.xml`, `Dockerfile`, `Jenkinsfile` |
| Puerto 8091 a 8085 | `Dockerfile`, `application.properties`, comando `docker run` |
| Sonar server name | `withSonarQubeEnv('...')` |
| ProjectKey de Sonar | `-Dsonar.projectKey=...` |
| Usuario Docker Hub | `REGISTRY_USER` |
| Nombre de imagen | `IMAGE_NAME` |
| Network Docker | comandos `docker network` y `docker run --network` |
