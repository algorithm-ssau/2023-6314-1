FROM openjdk:19-alpine
MAINTAINER Pavel Kazhukov <sanpavkaz@gmail.com>
EXPOSE 8080
COPY ./target/gateway-service-0.0.1-SNAPSHOT.jar /gateway-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "gateway-service-0.0.1-SNAPSHOT.jar"]