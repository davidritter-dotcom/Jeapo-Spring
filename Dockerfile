FROM openjdk:17-alpine
ARG JAR_FILE=target/savvy-0.0.1-SNAPSHOT.jar
COPY ./target/savvy-0.0.1-SNAPSHOT.jar savvy.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/savvy.jar"]
