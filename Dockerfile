FROM openjdk:17-alpine
ARG JAR_FILE=build/libs/app-0.0.1.jar
COPY ./build/libs/app-0.0.1.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
