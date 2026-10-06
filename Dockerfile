# syntax=docker/dockerfile:1.27
FROM maven:3.9-eclipse-temurin-26 AS java-build
WORKDIR /src/app
COPY pom.xml lombok.config ./
COPY src/ src/
COPY frontend/ frontend/
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -ntp -DskipTests package

FROM eclipse-temurin:26-jre-noble AS java
WORKDIR /app
COPY --from=java-build /src/app/target/hello-world-client-java-0.1.0.jar /app/server.jar
ENV HTTP_PORT=8080
EXPOSE 8080
USER 65532:65532
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "/app/server.jar"]
