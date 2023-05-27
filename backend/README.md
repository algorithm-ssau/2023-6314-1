# Backend Setup

## Подготовка
- установить [JDK](https://jdk.java.net/archive/) хотя-бы 17 версии
- установить [Docker](https://docs.docker.com/engine/install/)
- запустить Docker
- открыть терминал linux
- выполнить команду set JAVA_HOME="путь до jdk"/"jdk" (Либо вручную установить переменную среду JAVA_HOME="путь до jdk"/"jdk")

## Запуск через bash скрипт
- выполнить скрипт по пути: projectFolder/backend/store/scripts/rebuild.sh

## Мануальный запуск
- открыть cmd по директории: projectFolder/backend/store
- выполнить команду ./mvnw package -am -Dmaven.test.skip=true
- перейти в директорию: projectFolder/backend/store/services
- выполнить команду docker-compose up --build
