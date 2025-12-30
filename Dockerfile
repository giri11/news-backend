# Build stage
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy Maven settings
COPY settings.xml /root/.m2/settings.xml

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:resolve -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests -T 1C

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]