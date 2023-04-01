FROM maven:3.8.7-openjdk-18-slim AS build
COPY src /src
COPY pom.xml /pom.xml
RUN mvn -f ./pom.xml package -am -Dmaven.test.skip -T 1C

FROM openjdk:latest
MAINTAINER Pavel Kazhukov <sanpavkaz@gmail.com>
COPY --from=build /target/identity-provider-0.0.1-SNAPSHOT.jar identity-provider-0.0.1-SNAPSHOT.jar
EXPOSE 8075
ENTRYPOINT ["java","-jar","identity-provider-0.0.1-SNAPSHOT.jar"]