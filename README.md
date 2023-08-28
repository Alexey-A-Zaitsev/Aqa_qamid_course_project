# Курсовой проект профессии Тестировщик


***[Планирование автоматизации](documents/Plan.md)***

***[Отчёт по итогам тестирования](documents/Report.md)***

***[Отчёт по итогам автоматизации](documents/Summary.md)***

## Окружение

* JDK версия 11
* GIT
* Google Chrome
* IntelliJ IDEA Ultimate
* Docker desktop (совместно с Docker-compose)
* Свободные порты 8080, 9999, 5432, 3306

## Процедура запуска автотестов

1. **Клонировать проект**
* открыть терминал `GIT` для целевой папки проекта
* командой `git clone` клонировать [проект](https://github.com/Alexey-A-Zaitsev/Aqa_qamid_course_project) в свой репозиторий

2. **Запустить `Docker Desktop`**

3. **Открыть проект в `IntelliJ IDEA`**
* в одном окне терминала запустить контейнер БД командой `docker-compose up`
* во втором окне терминала запустить SUT командой `java -jar artifacts/aqa-shop.jar`
* проверить успешный запуск приложения: в Google Chrome перейти по адресу: `http://localhost/8080/`

4. **Запуск тестов и отчёт**
* в третьем окне терминала запустить тесты командой *./gradlew clean test*
* после прохождения тестов создать отчет командой *./gradlew allureserve*