FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY ./ecosystem-api.jar /app/ecosystem-api.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/ecosystem-api.jar"]