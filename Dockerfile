FROM openjdk:17-alpine
MAINTAINER Milan Somyk
RUN apk add --no-cache bash
RUN mkdir /app
WORKDIR /app
ENV PORT 8080
EXPOSE 8080
COPY target/*.jar /opt/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar