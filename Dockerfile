FROM gradle:7.6.1-jdk17-alpine AS builder
WORKDIR /
COPY . .
RUN gradle build -x test

FROM amazoncorretto:17.0.6-alpine3.17
ENV TZ="America/Bogota"
COPY --from=builder build/libs/chatgpt-api-0.0.1-SNAPSHOT.jar app.jar
CMD [ "java", "-jar", "app.jar" ]