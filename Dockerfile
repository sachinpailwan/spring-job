FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/spring-job-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} flare-batch.jar
ENTRYPOINT ["java","-jar","/app.jar"]