FROM openjdk:17-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file from the host's ./target folder to the container's /app folder
COPY ./target/savvy-spring-1.0.jar /app/savvy-spring-1.0.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/savvy-spring-1.0.jar"]