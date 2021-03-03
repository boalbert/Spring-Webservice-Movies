FROM openjdk:15-alpine
LABEL maintainer="hej@boalbert.se"
VOLUME /main-app
ADD target/moviesapi-0.0.1-SNAPSHOT.jar movies-api.jar
EXPOSE 5050
ENTRYPOINT ["java", "-jar","/movies-api.jar"]