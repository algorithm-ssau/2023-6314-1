FROM openjdk:19-alpine
MAINTAINER Pavel Kazhukov <sanpavkaz@gmail.com>
EXPOSE 8888
COPY target/config-service-0.0.1-SNAPSHOT.jar /config-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "config-service-0.0.1-SNAPSHOT.jar"]