# Stage 1: Build JAR
FROM maven:3.9.9-eclipse-temurin-21 AS builder
LABEL authors="kn260"

WORKDIR /app

# Copy pom.xml và download dependency trước (để cache)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source và build
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run JAR
FROM eclipse-temurin:21-jre-alpine
LABEL authors="kn260"

WORKDIR /app
COPY --from=builder /app/target/hrm-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
