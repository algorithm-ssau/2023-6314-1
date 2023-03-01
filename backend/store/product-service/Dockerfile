FROM openjdk:oraclelinux8
MAINTAINER Pavel Kazhukov <sanpavkaz@gmail.com>
EXPOSE 8001

COPY /.mvn /.mvn
COPY /mvnw /mvnw
COPY /mvnw.cmd /mvnw.cmd

COPY /pom.xml /pom.xml

COPY /src /src

COPY /build.sh /build.sh

CMD ./build.sh