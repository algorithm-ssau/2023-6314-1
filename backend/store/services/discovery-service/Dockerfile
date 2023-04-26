FROM eclipse-temurin:17-jre-alpine as build
MAINTAINER Pavel Kazhukov <sanpavkaz@gmail.com>

FROM build
EXPOSE 8761
COPY target/discovery-service-0.0.1-SNAPSHOT.jar /discovery-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-Xmn30m", "-Xms60m", "-Xmx64m", "-Xss256k", "-jar", "discovery-service-0.0.1-SNAPSHOT.jar"]