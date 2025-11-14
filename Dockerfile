# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copier les fichiers de configuration Maven
COPY pom.xml .
RUN mvn dependency:go-offline

# Copier le code source
COPY src ./src

# Compiler l'application (sans tests)
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copier le JAR depuis le stage de build
COPY --from=build /app/target/minesweeper-backend-1.0.0.jar app.jar

# Exposer le port
EXPOSE 80
EXPOSE 8011

# Variables d'environnement
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Lancer l'application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]