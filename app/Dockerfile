# image  : rubensgomes/hello-world-ms:latest
# purpose: image to spawn a very basic Spring Boot microservice.
# repo   : https://hub.docker.com/r/rubensgomes/hello-world-ms


# Use an official JDK 21 runtime as a parent image
FROM amazoncorretto:21

# the application released version being passed in
ARG RELEASED_VERSION

# create a local group/user to mitigate security risks
RUN addgroup -S spring && adduser -S spring -G spring

# run as below user
USER spring:spring

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY build/libs/app-$RELEASED_VERSION.jar app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]