FROM eclipse-temurin:17.0.19_10-jdk-jammy
WORKDIR /app
COPY target/wallet-app-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]