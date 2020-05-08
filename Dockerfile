FROM openjdk:8-jdk-alpine
COPY ./build/libs/*.jar ./sample-api.jar
ENTRYPOINT ["java","-jar","./sample-api.jar"]