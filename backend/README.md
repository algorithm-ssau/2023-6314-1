# Backend

## Запуск

- установить [Docker](https://docs.docker.com/engine/install/ubuntu)
- установить [JDK](https://jdk.java.net/archive/) хотя-бы 17 версии
- открыть cmd по директории: projectFolder/backend/store
- выполнить команду set JAVA_HOME="путь до jdk"/"jdk" (Либо установить переменную среду JAVA_HOME="путь до jdk"/"jdk")
- выполнить команду mvnw package -am -Dmaven.test.skip -T 1C (Если Linux: ./mvnw package -am -o -Dmaven.test.skip -T 1C)
- перейти в директорию: projectFolder/backend/store/services
- запустить Docker
- выполнить команду docker-compose up --build