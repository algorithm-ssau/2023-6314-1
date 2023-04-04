FROM eclipse-temurin:17-jre-alpine as build
MAINTAINER Pavel Kazhukov <sanpavkaz@gmail.com>

FROM build
EXPOSE 8001
COPY target/user-service-0.0.1-SNAPSHOT.jar /user-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "user-service-0.0.1-SNAPSHOT.jar"]

