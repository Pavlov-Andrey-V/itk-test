# Wallet App

REST-сервис для управления балансом кошельков.
Spring Boot 3 + PostgreSQL + Liquibase + Docker.

## Требования

- Java 17+
- Docker & Docker Compose
- Maven (или `./mvnw`)

## Сборка и запуск

```bash
# 1. Сборка JAR
./mvnw clean package -DskipTests

# 2. Сборка Docker-образа и запуск
docker-compose up --build
```

Приложение доступно на `http://localhost:8080`.


## Настройка

Параметры задаются через переменные окружения (без пересборки контейнеров):

| Переменная | Описание | Пример |
|---|---|---|
| `POSTGRES_URL` | JDBC URL | `jdbc:postgresql://postgres:5432/postgres` |
| `POSTGRES_USERNAME` | Пользователь БД | `postgres` |
| `POSTGRES_PASSWORD` | Пароль | `password` |

