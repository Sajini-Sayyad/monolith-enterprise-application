FROM maven:3.9.9-eclipse-temurin-8 AS builder

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests -Dliquibase.skip=true

FROM eclipse-temurin:8-jre

WORKDIR /app

COPY --from=builder /app/target/Snowman.jar app.jar

EXPOSE 8090

CMD ["java","-jar","app.jar"]
