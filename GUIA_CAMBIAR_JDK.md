# Guia rapida: cambiar version de JDK

En tu examen, el profesor puede decir: "ahora usen JDK 21" o "ahora usen JDK 25".

No basta con cambiar un solo archivo. Cambia estos 3 lugares.

## 1. pom.xml

Busca:

```xml
<java.version>24</java.version>
<maven.compiler.release>24</maven.compiler.release>
```

Cambia 24 por la version solicitada.

Ejemplo JDK 21:

```xml
<java.version>21</java.version>
<maven.compiler.release>21</maven.compiler.release>
```

## 2. Dockerfile

Busca:

```dockerfile
FROM maven:3.9.11-eclipse-temurin-24-noble AS build
FROM eclipse-temurin:24-jre-noble
```

Cambia 24 por la version solicitada.

Ejemplo JDK 21:

```dockerfile
FROM maven:3.9.11-eclipse-temurin-21-noble AS build
FROM eclipse-temurin:21-jre-noble
```

## 3. Jenkinsfile

Busca:

```groovy
jdk 'JDK_24'
```

Cambia por el nombre exacto configurado en Jenkins.

Ejemplo:

```groovy
jdk 'JDK_21'
```

Importante: el nombre depende de como tu profesor haya registrado el JDK en Jenkins. Si en Jenkins se llama `jdk24` o `Temurin24`, debes usar exactamente ese nombre.

## Regla mental para examen

Version de Java en codigo:

```text
pom.xml
```

Version de Java para construir imagen:

```text
Dockerfile build stage
```

Version de Java para ejecutar imagen:

```text
Dockerfile runtime stage
```

Version de Java para Jenkins:

```text
Jenkinsfile tools
```
