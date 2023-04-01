FROM maven:3.8.7-openjdk-18-slim AS build
COPY src /src
COPY pom.xml /pom.xml
RUN mvn -f ./pom.xml clean package

FROM openjdk:latest
MAINTAINER Pavel Kazhukov <sanpavkaz@gmail.com>
COPY --from=build /target/product-service-0.0.1-SNAPSHOT.jar product-service-0.0.1-SNAPSHOT.jar
COPY --from=build /target/classes/images /images
EXPOSE 8001
ENTRYPOINT ["java","-jar","product-service-0.0.1-SNAPSHOT.jar"]