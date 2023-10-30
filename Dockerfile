# syntax=docker/dockerfile:1
# Which official docker image?
FROM openjdk:11
LABEL maintainer="javaguides"
ADD target/sep-java-springboot-docker.jar sep-java-springboot-docker.jar
ENTRYPOINT ["java","-jar","sep-java-springboot-docker.jar"]