# Use a base image with Maven to build the application
FROM maven:3.8.4-openjdk-11 AS build

# Set the working directory
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .

# Download the project dependencies
RUN mvn dependency:go-offline -B

# Copy the application source code
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Use a lightweight base image for the final image
FROM adoptopenjdk:11-jre-hotspot

# Set the working directory
WORKDIR /opt/app

# Copy the JAR file from the build stage
COPY --from=build /app/target/airbnb-clone-backend-0.0.1-SNAPSHOT.jar .

# Consume the build arguments
ARG GITHUB_CLIENT_ID
ARG GITHUB_CLIENT_SECRET
ARG MONGODB_PASSWORD
ENV CLIENT_ID=$GITHUB_CLIENT_ID
ENV CLIENT_SECRET=$GITHUB_CLIENT_SECRET
ENV DB_PASSWORD=$MONGODB_PASSWORD

# Set the command to run the Spring Boot application
CMD ["java", "-jar", "airbnb-clone-backend-0.0.1-SNAPSHOT.jar"]


