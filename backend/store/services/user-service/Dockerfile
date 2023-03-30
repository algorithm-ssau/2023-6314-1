FROM openjdk:19-alpine
MAINTAINER Pavel Kazhukov <sanpavkaz@gmail.com>
EXPOSE 8001
COPY target/user-service-0.0.1-SNAPSHOT.jar /user-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "user-service-0.0.1-SNAPSHOT.jar"]