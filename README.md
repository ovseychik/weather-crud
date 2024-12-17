[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=ovseychik_weather-crud&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=ovseychik_weather-crud)
[![Workflow Status](https://github.com/ovseychik/weather-crud/actions/workflows/gradle.yaml/badge.svg)](https://github.com/ovseychik/weather-crud/actions)

# Описание

Приложение в рамках тестового задания на вакансию Java разработчика

# Стек

* Java 17
* Spring Boot

Persistence:

* jOOQ
* PostgreSQL
* Liquibase

# Как запустить

Ключ API сервиса погоды был передан вместе со ссылкой на репозиторий.

## Локально

Необходимо указать ключ API в переменных окружения. Это можно легко сделать через IntelliJ, изменив конфиграцию запуска,
как показано на скриншоте.

![Screenshot 2024-12-15 at 18.39.12.png](_docs/Screenshot%202024-12-15%20at%2018.39.12.png)

## Из docker-compose

Репозиторий содержит [docker-compose.yml](docker-compose.yml), который поможет подготовить БД и запустить контейнер с
проектом.

При желании, приложение можно запустить как обычное Spring Boot приложение, нажав Run в IntelliJ.

# Документация

После запуска сервиса Swagger UI доступен по ссылке:
http://localhost:8080/webjars/swagger-ui/index.html

# CI/CD

Файл с описанием пайплайна лежит в репозитории: [gradle.yaml](.github/workflows/gradle.yaml)

В рамках пайплайна происходит:

* сборка проекта в gradle
* тестирование
* публикация в виде jar пакета в репозитории https://github.com/ovseychik/weather-crud/packages

Для репозитория настроено автоматическое сканирование в SonarCloud. Актуальный отчёт сонара:

[![SonarQube Cloud](https://sonarcloud.io/images/project_badges/sonarcloud-highlight.svg)](https://sonarcloud.io/summary/new_code?id=ovseychik_weather-crud)

# Бизнес-логика

Есть два типа ендпонитов: для работы с городами и для работы с погодой

Сначала необходимо добавить город, указав его географические координаты - широту и долготу. После этого можно работать с
погодой, запрашивая текущую и прогноз до 7 дней включительно.

Коллекции Postman:

* cities: [Cities.postman_collection.json](_docs/Cities.postman_collection.json)
* weather: [Weather.postman_collection.json](_docs/Weather.postman_collection.json)
