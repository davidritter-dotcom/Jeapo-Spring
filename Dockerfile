FROM openjdk:17-alpine
ARG JAR_FILE=target/todo-0.0.1-SNAPSHOT.jar
COPY ./target/todo-0.0.1-SNAPSHOT.jar todo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/todo.jar"]
