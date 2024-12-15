# Build stage
FROM gradle:8.6.0-jdk17 AS builder

# Set working directory
WORKDIR /app

# Copy project files
COPY build.gradle settings.gradle ./
COPY src ./src
COPY gradle ./gradle

# Set database host for build time
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/weather_db
ENV DB_HOST=host.docker.internal

# Build the application
# --no-daemon reduces memory usage
# -x test skips tests during build as they will be run in CI pipeline
RUN gradle build --no-daemon -x test

# Runtime stage
FROM --platform=linux/amd64 eclipse-temurin:17-jre-alpine

WORKDIR /app

RUN apk add --no-cache postgresql-client ca-certificates

# Copy the built artifact from builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Runtime environment variables
#ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/weather_db
ENV DB_HOST=postgres

# Expose application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
