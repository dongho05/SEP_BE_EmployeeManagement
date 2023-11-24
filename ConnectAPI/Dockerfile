# syntax=docker/dockerfile:1
# Which official docker image?
FROM openjdk:11-jre-slim-buster
LABEL maintainer="connectapi"
# Add dependencies
RUN apt-get update && apt-get install -y \
    curl \
    libsnappy-dev \
    zlib1g-dev
ADD deployment/lib/application-api-admin.jar application-api-admin.jar
# Bundle application dependencies
COPY --from=maven:latest /usr/share/maven/ref/ /usr/share/maven/ref/
EXPOSE 9999
ENTRYPOINT ["java","-jar","application-api-admin.jar"]