FROM eclipse-temurin:17-jre-alpine as build
MAINTAINER Pavel Kazhukov <sanpavkaz@gmail.com>

FROM build
EXPOSE 8003
COPY target/order-service-0.0.1-SNAPSHOT.jar /order-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "order-service-0.0.1-SNAPSHOT.jar"]