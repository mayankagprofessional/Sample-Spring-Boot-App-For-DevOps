# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory to /app
WORKDIR /app

# Copy the packaged JAR file into the container at /app
COPY target/SpringScale-1.0.jar /app/SpringScale.jar

# Expose 8080 port
EXPOSE 8080


# Run the JAR file when the container launches
CMD ["java", "-jar", "SpringScale.jar"]
