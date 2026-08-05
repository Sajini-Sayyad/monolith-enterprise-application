FROM eclipse-temurin:8-jre

WORKDIR /app

COPY target/Snowman.jar app.jar

EXPOSE 8090

CMD ["java", "-jar", "app.jar"]
