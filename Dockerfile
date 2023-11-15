# syntax=docker/dockerfile:1
# Which official docker image?
FROM openjdk:11-jre-slim-buster
LABEL maintainer="capstones"
# Add dependencies
RUN apt-get update && apt-get install -y \
    curl \
    libsnappy-dev \
    zlib1g-dev
ADD target/sep-java-springboot-docker.jar sep-java-springboot-docker.jar
# Bundle application dependencies
COPY --from=maven:latest /usr/share/maven/ref/ /usr/share/maven/ref/
EXPOSE 8080
ENTRYPOINT ["java","-jar","sep-java-springboot-docker.jar"]