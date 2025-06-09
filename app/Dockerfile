# image  : rubensgomes/helloworld-ms:<vERSION>
# purpose: image to spawn a very basic Spring Boot microservice.
# repo   : https://hub.docker.com/r/rubensgomes/helloworld-ms

# Use an official Amazon Linux 2023 JDK 21 runtime
FROM amazoncorretto:21-al2023

# the date/time this image was built
ARG BUILD_DATE
# the application released version built
ARG VERSION

# switch to root user to perform admin tasks only
USER root
# create a local group/user to mitigate security risks
RUN yum update -y \
    && yum -y install shadow-utils \
    && groupadd -g 1001 spring \
    && useradd -m -u 1001 -g spring spring \
    && yum -y remove shadow-utils \
    && yum clean all \
    && rm -rf /var/cache/yum

# switch to local user to run remaining image build commands and later the container
USER spring

# Set the working directory in the container to the spring user home
WORKDIR /home/spring

# 1. Copy the Spring boot lib folder
COPY build/layer/lib lib
# 2. Copy the application jar
COPY build/layer/app-$VERSION.jar app.jar

LABEL version="$VERSION" \
      Maintainer="rubens.s.gomes@gmail.com" \
      org.opencontainers.image.created="$BUILD_DATE" \
      org.opencontainers.image.authors="rubens.s.gomes@gmail.com" \
      org.opencontainers.image.url="https://github.com/rubensgomes/helloworld-ms" \
      org.opencontainers.image.licenses="Apache License 2.0" \
      org.opencontainers.image.title="Spring Boot HelloWorld Microservice."

LABEL org.opencontainers.image.description="An very basic Spring Boot RESTful webapp to \
illustrate some best practices and patterns of implementing a microservice."

# deffault eecutable for the container, also the PID 1 process
# SIGTERM is sent to the JAva vm which is handled by the running Spring Boot
# ApplicationListener event handler code.
ENTRYPOINT ["java", "-jar", "app.jar"]
