FROM eclipse-temurin:17-jre-alpine as build
MAINTAINER Pavel Kazhukov <sanpavkaz@gmail.com>

FROM build
EXPOSE 8001
COPY target/product-service-0.0.1-SNAPSHOT.jar /product-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "product-service-0.0.1-SNAPSHOT.jar"]