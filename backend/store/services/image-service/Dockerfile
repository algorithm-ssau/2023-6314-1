FROM openjdk:19-alpine
MAINTAINER Pavel Kazhukov <sanpavkaz@gmail.com>
EXPOSE 8005
COPY target/image-service-0.0.1-SNAPSHOT.jar /image-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "image-service-0.0.1-SNAPSHOT.jar"]