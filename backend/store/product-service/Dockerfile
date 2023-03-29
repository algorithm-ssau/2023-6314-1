FROM openjdk:19-alpine
MAINTAINER Pavel Kazhukov <sanpavkaz@gmail.com>
EXPOSE 8001
COPY ./target/product-service-0.0.1-SNAPSHOT.jar /product-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "product-service-0.0.1-SNAPSHOT.jar"]