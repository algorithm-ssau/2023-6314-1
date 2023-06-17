# Mango
>Интернет-магазин посредник для продажи импортной одежды  
## Участники команды
```sh
6314 Никитин Евгений. Основная роль - Frontend developer
6314 Примосудов Сергей. Основная роль - Frontend developer
6313 Коннов Кирилл. Основная роль - Backend developer
6314 Кажуков Павел. Основная роль - Backend developer
```
# Backend

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

# Frontend

## Запуск

1. Скачать npm с сайта - https://nodejs.org/en/download
2. Открыть cmd по пути projectFolder/frontend
3. Выполнить команду npm i
4. Выполнить команду npm start
