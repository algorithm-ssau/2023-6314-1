FROM eclipse-temurin:17-jre-alpine as build
MAINTAINER Pavel Kazhukov <sanpavkaz@gmail.com>

FROM build
EXPOSE 8888
COPY target/config-service-0.0.1-SNAPSHOT.jar /config-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-Xmn30m", "-Xms60m", "-Xmx64m", "-Xss256k", "-jar", "config-service-0.0.1-SNAPSHOT.jar"]