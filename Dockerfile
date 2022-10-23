FROM openjdk:8
ADD /car-rental-platform-0.0.1-SNAPSHOT.jar //
ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=prod","car-rental-platform-0.0.1-SNAPSHOT.jar"]