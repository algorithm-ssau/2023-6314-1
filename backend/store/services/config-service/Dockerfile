FROM maven:3.8.7-openjdk-18-slim AS build
COPY src /src
COPY pom.xml /pom.xml
RUN mvn -f ./pom.xml clean package

FROM openjdk:latest
MAINTAINER Pavel Kazhukov <sanpavkaz@gmail.com>
COPY --from=build /target/config-service-0.0.1-SNAPSHOT.jar config-service-0.0.1-SNAPSHOT.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","config-service-0.0.1-SNAPSHOT.jar"]