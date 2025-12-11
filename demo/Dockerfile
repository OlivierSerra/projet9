FROM eclipse-temurin:21-jre

WORKDIR /app

# On copie le JAR déjà construit en local
COPY target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
