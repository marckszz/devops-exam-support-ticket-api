# Support Ticket API - Examen DevOps Jenkins + SonarQube + Docker

Proyecto simple en Spring Boot para practicar un examen de CI/CD.

## Caso real

Una mesa de ayuda necesita publicar una API de tickets de soporte. El equipo DevOps debe automatizar:

1. Compilacion del proyecto Java.
2. Validacion de estilo con Checkstyle.
3. Ejecucion de pruebas unitarias.
4. Validacion de cobertura con JaCoCo.
5. Analisis de calidad en SonarQube.
6. Construccion y publicacion de imagen Docker en Docker Hub.

## Endpoints

- `GET /api/tickets`
- `POST /api/tickets`
- `PATCH /api/tickets/{id}/status/{status}`
- `GET /api/tickets/summary`
- `GET /actuator/health`

Ejemplo POST:

```json
{
  "customerName": "Cliente Examen",
  "subject": "No se genera el reporte de notas",
  "priority": "HIGH"
}
```

## Network recomendada para esta practica

Usa esta red para no mezclarte con la del profesor:

```bash
docker network create devops-ticket-net
```

## Contenedores recomendados

### Jenkins

```bash
docker run -d --name jenkins-exam --platform linux/amd64 --user root --network devops-ticket-net -p 9091:9091 -p 50001:50000 -v /var/run/docker.sock:/var/run/docker.sock -v jenkins_exam_data:/var/jenkins_home -e JENKINS_OPTS="--httpPort=9091" jenkins-ci-cd:2026.final
```

Ver clave inicial:

```bash
docker logs jenkins-exam
```

URL navegador:

```text
http://localhost:9091/
```

### SonarQube

```bash
docker run -d --name sonarqube-exam --network devops-ticket-net -p 9001:9000 -v sonarqube_exam_data:/opt/sonarqube/data -v sonarqube_exam_extensions:/opt/sonarqube/extensions -v sonarqube_exam_logs:/opt/sonarqube/logs sonarqube:lts-community
```

URL navegador:

```text
http://localhost:9001/
```

URL interna desde Jenkins:

```text
http://sonarqube-exam:9000
```

Webhook interno desde SonarQube hacia Jenkins:

```text
http://jenkins-exam:9091/sonarqube-webhook/
```

## Comandos locales

```bash
mvn clean compile
mvn checkstyle:check
mvn test
mvn clean verify jacoco:report
mvn jacoco:check
mvn clean package
```

Ejecutar local:

```bash
java -jar target/support-ticket-api-0.0.1-SNAPSHOT.jar
```

Construir imagen local:

```bash
docker build --platform linux/amd64 -t support-ticket-api:local .
```

Ejecutar imagen local:

```bash
docker run --rm --name support-ticket-api --network devops-ticket-net -p 8091:8091 support-ticket-api:local
```

## Partes que normalmente te cambian en examen

Si cambian la version de JDK, cambia estos lugares:

1. `pom.xml`
   - `<java.version>24</java.version>`
   - `<maven.compiler.release>24</maven.compiler.release>`

2. `Dockerfile`
   - `maven:3.9.11-eclipse-temurin-24-noble`
   - `eclipse-temurin:24-jre-noble`

3. `Jenkinsfile`
   - `jdk 'JDK_24'`

Si cambian el usuario de Docker Hub, cambia:

```groovy
REGISTRY_USER = "TU_USUARIO_DOCKERHUB"
```

Si cambian el nombre de imagen, cambia:

```groovy
IMAGE_NAME = "support-ticket-api"
```

Si cambian SonarQube, cambia:

```groovy
withSonarQubeEnv('MiSonarServer')
sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=support-ticket-api'
```
