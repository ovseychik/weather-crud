# Build stage
FROM gradle:8.6.0-jdk17 AS builder

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY src ./src
COPY gradle ./gradle

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/weather_db
ENV DB_HOST=localhost

RUN gradle build --no-daemon -x test

# Runtime stage
FROM --platform=linux/amd64 eclipse-temurin:17-jre-alpine

WORKDIR /app

RUN apk add --no-cache postgresql-client ca-certificates

COPY --from=builder /app/build/libs/*.jar app.jar

ENV DB_HOST=postgres

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
