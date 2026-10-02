# Build stage: compile, test and package the WAR
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -B -q dependency:go-offline
COPY src ./src
RUN mvn -B package

# Runtime stage: Tomcat 10.1 serving the app at /booknook
FROM tomcat:10.1-jre11
COPY --from=build /app/target/booknook.war /usr/local/tomcat/webapps/booknook.war
ENV BOOKNOOK_DB_PATH=/data/booknook.db
VOLUME /data
EXPOSE 8080
