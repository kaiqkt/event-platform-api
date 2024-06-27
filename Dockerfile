FROM openjdk:17

MAINTAINER https://github.com/kaiqkt

COPY ./build/libs/*.jar event-platform-api.jar

ENTRYPOINT ["java","-jar","event-platform-api.jar"]
