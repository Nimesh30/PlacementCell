#FROM ubuntu:latest
#LABEL authors="HIFI LAPTOP"
#
#ENTRYPOINT ["top", "-b"]

# Build stage - Now using Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage - Now using Java 21
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Expose port 8085 as you requested earlier
EXPOSE 8085

ENTRYPOINT ["java", "-jar", "app.jar"]