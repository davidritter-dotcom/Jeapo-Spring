FROM maven:3.8.5-openjdk-17 AS build

# image layer
WORKDIR /app
COPY pom.xml /app
# Download project dependencies for the production profile
RUN mvn dependency:go-offline -P production
COPY src ./src
# Build the project with the production profile, skipping tests
RUN mvn clean install -DskipTests -P production
FROM openjdk:17-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/savvy-spring-0.0.1-SNAPSHOT.jar /app/savvy-spring-0.0.1-SNAPSHOT.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/savvy-spring-0.0.1-SNAPSHOT.jar"]