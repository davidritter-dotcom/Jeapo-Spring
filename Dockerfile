FROM maven:3.8.5-openjdk-17 AS build

# image layer
WORKDIR /app
COPY pom.xml /app
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean install -DskipTests
FROM openjdk:17-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/todo-0.0.1-SNAPSHOT.jar /app/todo-0.0.1-SNAPSHOT.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/todo-0.0.1-SNAPSHOT.jar"]