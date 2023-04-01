# Backend

## Запуск

- установить [Docker](https://docs.docker.com/engine/install/ubuntu)
- запустить Docker
- открыть cmd по директории: projectFolder/backend/store
- выполнить команду mvnw package -am -o -Dmaven.test.skip -T 1C (Если Linux: ./mvnw package -am -o -Dmaven.test.skip -T 1C)
- перейти в директорию: projectFolder/backend/store/services
- выполнить команду docker-compose up --build
