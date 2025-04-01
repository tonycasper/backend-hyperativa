# Etapa 1 - Build da aplicação
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2 - Execução da aplicação
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiar o JAR gerado na etapa 1
COPY --from=build /app/target/app.jar app.jar

# Comando para rodar o JAR
ENTRYPOINT ["java", "-jar", "/app.jar"]