FROM eclipse-temurin:17-jre-alpine as build
MAINTAINER Pavel Kazhukov <sanpavkaz@gmail.com>
RUN apk add --no-cache curl

FROM build
EXPOSE 8005
COPY target/image-service-0.0.1-SNAPSHOT.jar /