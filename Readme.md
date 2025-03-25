# Calories Tracker

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.1-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)

Микросервис для учета калорий и питания с возможностью:
- 📊 Учета дневной нормы калорий
- 🍎 Добавления блюд и их состава (БЖУ)
- 📅 Формирования отчетов за период
- 👤 Персональных рекомендаций по питанию

## Быстрый старт

### Требования

![Docker](https://img.shields.io/badge/Docker-20.10%2B-2496ED?logo=docker&logoColor=white)

![Docker Compose](https://img.shields.io/badge/Docker_Compose-1.29%2B-2496ED?logo=docker&logoColor=white)

#### 1. Клонировать репозиторий
```bash 
  git clone https://github.com/belkamydog/Calories-Tracker-API
```

Соберите и запустите проект:
```bash
docker-compose up -d
```
### Доступ к сервисам
  - Приложение: http://localhost:8080
  - Swagger UI: http://localhost:8080/swagger-ui.html

### Управление сервисами:
Запуск сервисов
- ```bash
  docker-compose up -d 
  ``` 
Остановка сервисов
- ```bash
  docker-compose down
  ```
Просмотр логов
- ```bash
  docker-compose logs -f
  ```
Перезапуск приложения
- ```bash
  docker-compose restart calories-service
     ```

### Устранение проблем
   - Если приложение не подключается к БД:
```bash
docker-compose restart calories-db
   ```
     - Для полной пересборки:
```bash
docker-compose down -v && docker-compose up -d --build
```
При первом запуске может потребоваться 1-2 минуты для инициализации базы данных