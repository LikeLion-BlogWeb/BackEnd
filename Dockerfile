FROM openjdk:11-jre
ENV SPRING_PROFILES_ACTIVE=prod
COPY changuii-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
