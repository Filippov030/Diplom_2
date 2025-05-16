# Diplom_2

Проект автоматизированного тестирования API для сервиса заказа межгалактических бургеров Stellarburgers.

## Описание проекта

Проект содержит автоматизированные тесты для проверки API сервиса заказа бургеров Stellarburgers. Тесты покрывают основные функции:
- Создание пользователя:
- Логин пользователя:
- Изменение данных пользователя:
- Создание заказа:
- Получение заказов конкретного пользователя:

## Технологический стек

- Java 11
- JUnit 4
- RestAssured
- Allure Framework
- Maven

## Требования

Для запуска проекта необходимо:

1. Java JDK 11 или выше
2. Apache Maven 3.6.0 или выше
3. Allure Commandline (для генерации отчетов)

## Установка и настройка

1. Запуск всех тестов
```zsh
mvn clean test
```
2. Запуск конкретного тестового класса
```zsh
mvn clean test -Dtest=OrderAuthTest
```
```zsh
mvn clean test -Dtest=OrderNoAuthTest
```
```zsh
mvn clean test -Dtest=UserAuthTest
```
```zsh
mvn clean test -Dtest=UserNoAuthTest

```
3. Генерация отчетов Allure
```sh
mvn allure:serve
```

