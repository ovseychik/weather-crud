[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=ovseychik_weather-crud&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=ovseychik_weather-crud)
[![Workflow Status](https://github.com/ovseychik/weather-crud/actions/workflows/gradle.yaml/badge.svg)](https://github.com/ovseychik/weather-crud/actions)

# Описание

Приложение в рамках тестового задания на вакансию Java разработчика

# Стек

* Java 17
* jOOQ

# Как запустить

* [ ] API key
* [ ] Docker compose

## Локально

### Из Intellij

### Из docker-compose

# Документация

После запуска сервиса Swagger UI доступен по ссылке:
http://localhost:8080/webjars/swagger-ui/index.html

# CI/CD

Файл с описанием пайплайна лежит по пути: `.github/workflows/gradle.yaml`

В рамках пайплайна происходит:

* сборка проекта в gradle
* тестирование
* упаковка в docker образ

Для репозитория настроено автоматическое сканирование в SonarCloud. Актуальный отчёт сонара:

[![SonarQube Cloud](https://sonarcloud.io/images/project_badges/sonarcloud-highlight.svg)](https://sonarcloud.io/summary/new_code?id=ovseychik_weather-crud)
