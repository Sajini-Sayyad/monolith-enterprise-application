# ===========================
# Stage 1 - Build Application
# ===========================
FROM maven:3.9.9-eclipse-temurin-8 AS builder

WORKDIR /app

# Copy all project files
COPY . .

# Build the JAR
RUN mvn clean package -DskipTests

# ===========================
# Stage 2 - Run Application
# ===========================
FROM eclipse-temurin:8-jre

WORKDIR /app

# Copy the JAR from the builder stage
COPY --from=builder /app/target/Snowman.jar app.jar

# Expose application port
EXPOSE 8090

# Start the application
CMD ["java", "-jar", "app.jar"]
