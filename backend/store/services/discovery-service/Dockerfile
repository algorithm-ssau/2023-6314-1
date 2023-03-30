FROM openjdk:19-alpine
MAINTAINER Pavel Kazhukov <sanpavkaz@gmail.com>
EXPOSE 8761
COPY target/discovery-service-0.0.1-SNAPSHOT.jar /discovery-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "discovery-service-0.0.1-SNAPSHOT.jar"]