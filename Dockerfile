# Use an official Java runtime as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged jar file to the container
COPY target/*.jar app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
