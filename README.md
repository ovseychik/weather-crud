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

## Из docker-compose

Репозиторий содержит [docker-compose.yml](docker-compose.yml), который поможет подготовить БД и запустить контейнер с
проектом.

При желании, приложение можно запустить как обычное Spring Boot приложение, нажав Run в IntelliJ.

# Документация

После запуска сервиса Swagger UI доступен по ссылке:
http://localhost:8080/webjars/swagger-ui/index.html

# CI/CD

Файл с описанием пайплайна лежит по пути: `.github/workflows/gradle.yaml`

В рамках пайплайна происходит:

* сборка проекта в gradle
* тестирование
* публикация в виде jar пакета в репозитории https://github.com/ovseychik/weather-crud/packages

Для репозитория настроено автоматическое сканирование в SonarCloud. Актуальный отчёт сонара:

[![SonarQube Cloud](https://sonarcloud.io/images/project_badges/sonarcloud-highlight.svg)](https://sonarcloud.io/summary/new_code?id=ovseychik_weather-crud)
