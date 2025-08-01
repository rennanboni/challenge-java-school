# Build stage
FROM gradle:7.4-jdk17 AS build
WORKDIR /app
COPY build.gradle .
COPY src ./src
RUN gradle build --no-daemon

# Package stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
