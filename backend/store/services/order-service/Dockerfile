FROM openjdk:19-alpine
MAINTAINER Pavel Kazhukov <sanpavkaz@gmail.com>
EXPOSE 8003
COPY target/order-service-0.0.1-SNAPSHOT.jar /order-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "order-service-0.0.1-SNAPSHOT.jar"]