FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/spring-job-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} spring-job.jar
ENTRYPOINT ["java","-jar","/spring-job.jar"]