# Etapa de construcción
FROM maven:3.9-eclipse-temurin-17 AS builder

# Directorio de trabajo
WORKDIR /app

# Copia los archivos necesarios al contenedor
COPY pom.xml .
COPY src ./src

# Construye el proyecto
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:17-jre

# Directorio de trabajo
WORKDIR /app

# Copia el JAR de la etapa de construccion al directorio de trabajo de la etapa de ejecución
COPY --from=builder target/football-app.jar football-app.jar

# Expone el puerto de la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/football-app.jar"]