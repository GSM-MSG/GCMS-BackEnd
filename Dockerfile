FROM openjdk:11-jre-slim-buster

COPY build/libs/testApplication.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]