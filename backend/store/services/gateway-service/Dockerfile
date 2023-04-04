FROM eclipse-temurin:17-jre-alpine as build
MAINTAINER Pavel Kazhukov <sanpavkaz@gmail.com>

FROM build
EXPOSE 8080
COPY target/gateway-service-0.0.1-SNAPSHOT.jar /gateway-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "gateway-service-0.0.1-SNAPSHOT.jar"]