FROM eclipse-temurin:17.0.8.1_1-jdk-focal
WORKDIR /app
COPY target/microContenu-0.0.1-SNAPSHOT.jar microContenu.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "microContenu.jar"]
