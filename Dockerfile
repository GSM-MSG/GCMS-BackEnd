FROM openjdk:11-jdk-slim
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} gcms.jar
CMD ["java", "-jar", "/gcms.jar"]
