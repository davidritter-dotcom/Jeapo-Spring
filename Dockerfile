FROM openjdk:17-alpine
ARG JAR_FILE=target/todo2-0.0.1-SNAPSHOT.jar
COPY ./target/todo2-0.0.1-SNAPSHOT.jar todo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/todo.jar"]
