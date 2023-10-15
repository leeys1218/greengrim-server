FROM openjdk:17-alpine

COPY ./build/libs/*.jar green-grim.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/green-grim.jar"]