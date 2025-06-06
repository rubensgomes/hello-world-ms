# image  : rubensgomes/helloworld-ms:<vERSION>
# purpose: image to spawn a very basic Spring Boot microservice.
# repo   : https://hub.docker.com/r/rubensgomes/helloworld-ms

# Use an official Amazon Linux 2023 JDK 21 runtime
FROM amazoncorretto:21-al2023

# the application released version being built
ARG VERSION

# Set the working directory in the container
WORKDIR /spring

# create a local group/user to mitigate security risks
RUN yum -y install shadow-utils \
    && groupadd -g 1001 spring \
    && useradd -g 1001 -u 1001 spring \
    && chown -R spring:spring /spring \
    && yum -y remove shadow-utils \
    && yum clean all \
    && rm -rf /var/cache/yum

# Copy the JAR file into the container
COPY build/libs/app-$VERSION.jar app.jar

# run as below user
USER spring

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
