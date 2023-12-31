# План автоматизации тестирования комплексного сервиса, взаимодействующего с СУБД и API банка ([Курсовой проект](courseProject.md))

## I. Перечень автоматизируемых сценариев
Поля формы покупки тура имеют следующие требования к содержимому:
- поле `Номер карты` принимает числовые значения, состоящие из 16 цифр;
- поле `Месяц` принимает числовые значения, состоящие из 2 цифр, в диапазоне от 01 до 12 (границы включительно) и обозначающие номер месяца;
- поле `Год` принимает числовые значения, состоящие из 2 цифр, обозначающие последние 2 цифры года, но не менее значения текущего года и не более 5 лет от текущего года;
- поле `Владелец` принимает только прописные и строчные латинские буквы, дефисы и пробелы;
- поле `CVC/CVV` принимает числовые значения, состоящие из 3 цифр [001, 999].

### Тестовые данные
Сервис обрабатывает только специальные номера карт, которые даны для тестирования:
* APPROVED карта — `1111 2222 3333 4444`;
* DECLINED карта — `5555 6666 7777 8888`. 

## Тестовые сценарии при покупке по карте

*Предусловия: в браузере открыта страница по адресу http://localhost:8080/ ; выбрана **оплата по карте** нажатием кнопки `Купить`*

1.  Успешная покупка тура
- ввести в поле "Номер карты" тестовый **APPROVED** номер карты `1111 2222 3333 4444`
- ввести в поле "Месяц" существующий номер месяца `06`
- ввести в поле "Год" валидное значение `25`
- ввести в поле "Владелец" валидное значение `Ivan Petrov`
- ввести в поле "CVC/CVV" валидное значение `123`
- нажать кнопку `Продолжить`    
*Ожидаемый результат:* запрос отправлен, на странице отображается сообщение — `Успешно. Операция одобрена Банком.`

2. Отклонение операции банком
- ввести в поле "Номер карты" тестовый номер **DECLINED** карты `5555 6666 7777 8888`
- ввести в поле "Месяц" существующий номер месяца `06`
- ввести в поле "Год" валидное значение `25`
- ввести в поле "Владелец" валидное значение `Ivan Petrov`
- ввести в поле "CVC/CVV" валидное значение `123`
- нажать кнопку `Продолжить`    
*Ожидаемый результат:* запрос отправлен, на странице отображается сообщение — `Ошибка! Банк отказал в проведении операции.`

3. Отображение сообщения об ошибке при оплате случайной валидной картой
- ввести в поле "Номер карты" случайный валидный номер  карты `3680 1234 4321 8888`
- ввести в поле "Месяц" существующий номер месяца `06`
- ввести в поле "Год" валидное значение `25`
- ввести в поле "Владелец" валидное значение `Ivan Petrov`
- ввести в поле "CVC/CVV" валидное значение `123`
- нажать кнопку `Продолжить`    
*Ожидаемый результат:* запрос не отправлен, на странице отображается сообщение — `Ошибка! Банк отказал в проведении операции.`

4. Отображение подсказок под полями при отправке незаполненной формы
- нажать кнопку `Продолжить`
*Ожидаемый результат:* форма не отправлена. Поля формы подсвечены красным. Под каждым полем отображены подсказки `Необходимо заполнить поле`.

### Блок валидации полей
*Предусловия: в браузере открыта страница по адресу http://localhost:8080/ ; выбрана **оплата по карте** нажатием кнопки `Купить`*  

5. Валидация поля "Номер карты"
- ввести в поле валидный номер карты — `1111 2222 3333 4444`  
*Ожидаемый результат:* введенное значение отображается в поле. Подсветка поля красным отсутствует. Сообщения об ошибках отсутствуют.
- ввести в поле 17 цифр — `1111 2222 3333 4444 5`  
*Ожидаемый результат:* ввод более 16-ти символов невозможен. В поле отображены первые 16 введенных символов.
- ввести в поле 15 цифр — `1111 2222 3333 444`  
*Ожидаемый результат:* В поле отображены введенные символы. Поле выделено красным. Под полем отображается сообщение об ошибке "Неверный формат".
- ввести в поле значение на латинице — `one two three`  
*Ожидаемый результат:* ввод букв невозможен.
- ввести в поле значение со спец. символами — `$$~6`  
*Ожидаемый результат:* ввод спец.символов невозможен.  
- ввести в поле несколько пробелов  
*Ожидаемый результат:* поле подсвечено красным. Под полем отображается сообщение об ошибке "Поле обязательно для заполнения". 

6. Валидация поля "Месяц"
- ввести в поле валидный номер месяца — `07`  
*Ожидаемый результат:* введенное значение отображается в поле. Подсветка поля красным отсутствует. Сообщения об ошибках отсутствуют.
- ввести в поле предыдущий (от текущего) номер месяца — `01`
*Ожидаемый результат:* введенное значение отображается в поле. поле подсвечено красным. Под полем отображается сообщение об ошибке "Неверно указан срок действия карты"
- ввести в поле 1 цифру — `4`
*Ожидаемый результат:* введенное значение отображается в поле. Поле подсвечено красным. Под полем отображается сообщение об ошибке "Введите дату как на карте".
- ввести в поле 3 цифры — `123`  
*Ожидаемый результат:* ввод более 2-х символов невозможен. В поле отображены первые 2 введенные символа.
- ввести в поле несуществующий номер месяца (меньше минимально допустимого, нижнее граничное значение) — `00`  
*Ожидаемый результат:* введенное значение отображается в поле. Поле подсвечено красным. Под полем отображается сообщение об ошибке "Введите дату как на карте".
- ввести в поле несуществующий номер месяца (больше максимально допустимого, верхнее граничное значение) — `13` 
*Ожидаемый результат:* введенное значение отображается в поле. Поле подсвечено красным. Под полем отображается сообщение об ошибке "Введите дату как на карте".
- ввести в поле значение на латинице — `May`  
*Ожидаемый результат:* ввод букв невозможен.
- ввести в поле значение со спец. символами — `$$~6`  
*Ожидаемый результат:* ввод спец. символов невозможен.  
- ввести в поле несколько пробелов  
*Ожидаемый результат:* поле подсвечено красным. Под полем отображается сообщение об ошибке "Поле обязательно для заполнения". 

7. Валидация поля "Год"
- ввести в поле валидный номер года — `24`  
*Ожидаемый результат:* введенное значение отображается в поле. Подсветка поля красным отсутствует. Сообщения об ошибках отсутствуют.
- ввести в поле 3 цифры — `123`  
*Ожидаемый результат:* ввод более 2-х символов невозможен. В поле отображены первые 2 введенные символа.
- ввести в поле 1 цифру — `1`  
*Ожидаемый результат:* введенное значение отображается в поле.Поле подсвечено красным. Под полем отображается сообщение об ошибке "Неверно указан срок действия карты".
- ввести в поле не валидный номер года (меньше минимально допустимого, нижнее граничное значение) — `22`  
*Ожидаемый результат:* введенное значение отображается в поле. Поле подсвечено красным. Под полем отображается сообщение об ошибке "Истёк срок действия карты".
- ввести в поле не валидный номер года (больше максимально допустимого, верхнее граничное значение) — `29` 
*Ожидаемый результат:* введенное значение отображается в поле. Поле подсвечено красным. Под полем отображается сообщение об ошибке "Неверно указан срок действия карты".
- ввести в поле значение на латинице — `Year`  
*Ожидаемый результат:* ввод букв невозможен.
- ввести в поле значение со спец. символами — `$$~6`  
*Ожидаемый результат:* ввод спец. символов невозможен.  
- ввести в поле несколько пробелов  
*Ожидаемый результат:* поле подсвечено красным. Под полем отображается сообщение об ошибке "Поле обязательно для заполнения". 

8. Валидация поля "Владелец"
- ввести в поле имя и фамилию на латинице — `Oleg Petrov`  
*Ожидаемый результат:* введенное значение отображается в поле. Подсветка поля красным отсутствует. Сообщения об ошибках отсутствуют.
- ввести в поле значение, написанное через дефис — `Anna-Maria Ivanova`  
*Ожидаемый результат:* введенное значение отображается в поле. Подсветка поля красным отсутствует. Сообщения об ошибках отсутствуют.
- ввести в поле значение с несколькими пробелами — `Amy Lynn Lee`  
*Ожидаемый результат:* введенное значение отображается в поле. Подсветка поля красным отсутствует. Сообщения об ошибках отсутствуют.
- ввести в поле имя всеми строчными латинскими буквами — `aleksei`  
*Ожидаемый результат:* введенное значение отображается в поле. Все буквы исправлены на заглавные. Подсветка поля красным отсутствует. Сообщения об ошибках отсутствуют.
- ввести в поле значение, начинающееся и оканчивающееся пробелом — `_Ivan_`  
*Ожидаемый результат:* введенное значение отображается в поле. Пробелы до и после имени обрезаются. Подсветка поля красным отсутствует. Сообщения об ошибках отсутствуют.
- ввести в поле цифровое значение — `1234567890`  
*Ожидаемый результат:* ввод цифр невозможен.
- ввести в поле значение со спец символами — `$$Ivan%`  
*Ожидаемый результат:* ввод спец. символов невозможен.
- ввести в поле несколько пробелов  
*Ожидаемый результат:* поле подсвечено красным. Под полем отображается сообщение об ошибке "Поле обязательно для заполнения".  

9. Валидация поля "CVC/CVV"
- ввести в поле валидное значение — `323`  
*Ожидаемый результат:* введенное значение отображается в поле. Подсветка поля красным отсутствует. Сообщения об ошибках отсутствуют.
- ввести в поле 4 цифры (верхнее граничное значение) — `1234`  
*Ожидаемый результат:* ввод более 3-х символов невозможен. В поле отображены первые 3 введенные символа.
- ввести в поле 2 цифры (нижнее граничное значение) — `01`  
*Ожидаемый результат:* введенное значение отображается в поле. Поле подсвечено красным. Под полем отображается сообщение об ошибке "Неверный формат".
- ввести в поле значение на латинице — `Year`  
*Ожидаемый результат:* ввод букв невозможен.
- ввести в поле значение со спец. символами — `$$~6`  
*Ожидаемый результат:* ввод спец. символов невозможен.  
- ввести в поле несколько пробелов  
*Ожидаемый результат:* ввод проьбелов невозможен. Поле подсвечено красным. Под полем отображается сообщение об ошибке "Неверный формат".  

### UI-тесты  
*Предусловия: в браузере открыта страница по адресу http://localhost:8080/*  

10. Отображение формы "Оплата по карте"
- нажать кнопку `Купить`
*Ожидаемый результат:* на странице отображается форма для оплаты по карте, содержащая поля:   
"Номер карты"  
"Месяц"  
"Год"  
"Владелец"  
"CVC/CVV"
11. Отображение формы "Кредит по данным карты"  
- нажать кнопку `Купить в кредит`
*Ожидаемый результат:* на странице отображается форма для заявки на кредит по карте, содержащая поля:   
"Номер карты"  
"Месяц"  
"Год"  
"Владелец"  
"CVC/CVV"

### Тесты с БД
12. Статус при оплате одобренной картой
- ввести в поле "Номер карты" тестовый **APPROVED** номер карты `1111 2222 3333 4444`
- ввести в поле "Месяц" существующий номер месяца `06`
- ввести в поле "Год" валидное значение `25`
- ввести в поле "Владелец" валидное значение `Ivan Petrov`
- ввести в поле "CVC/CVV" валидное значение `123`
- нажать кнопку `Продолжить`
*Ожидаемый результат:* `status` = "APPROVED" в таблице `payment_entity`.
13. Статус при оплате отклоненной картой
- ввести в поле "Номер карты" тестовый **APPROVED** номер карты `5555 6666 7777 8888`
- ввести в поле "Месяц" существующий номер месяца `06`
- ввести в поле "Год" валидное значение `25`
- ввести в поле "Владелец" валидное значение `Ivan Petrov`
- ввести в поле "CVC/CVV" валидное значение `123`
- нажать кнопку `Продолжить`
*Ожидаемый результат:* `status` = "DECLINED" в таблице `payment_entity`.
14. Стоимость при покупке тура
- ввести в поле "Номер карты" тестовый **APPROVED** номер карты `1111 2222 3333 4444`
- ввести в поле "Месяц" существующий номер месяца `06`
- ввести в поле "Год" валидное значение `25`
- ввести в поле "Владелец" валидное значение `Ivan Petrov`
- ввести в поле "CVC/CVV" валидное значение `123`
- нажать кнопку `Продолжить`
*Ожидаемый результат:* `amount` = 45000 в таблице `payment_entity`

### Так как формы оплаты по карте и оформления кредита по карте идентичны, то сценарии оформления в кредит по данным карты выполняются аналогично сценариям оплаты по карте.

## II. Перечень используемых инструментов  

Автоматизация тестирования будет реализована в JDK 11. Применяемая IDE — Intellij IDEA с системой автоматической сборки Gradle. Для выполнения автоматизации будут применяться следующие инструменты:  
* **lombok** - позволит сократить шаблонный код, путем генерации геттеров, сеттеров и конструкторов
* **JUnit 5** - фреймворк, предназначенный для автоматического тестирования программ
* **REST Assured** - позволит взаимодействовать с базой данных: посылать запросы в БД и проверять ответы
* **Google Chrome** - наиболее распространенный браузер
* **Selenide** - позволит взаимодействовать с браузером. В отличие от Selenium, в Selenide не придется отслеживать версию браузера
* **Allure** - позволит создавать понятные и наглядные репорты автотестов
* **AppVeyor** - позволит реализовать CI
* **Docker** - контейнеризатор приложений
* **DBeaver** - удобен для работы с базами данных
* **GitHub** - контроль версий, обмен данными
* [pairwise.teremokgames.com](https://pairwise.teremokgames.com/) - онлайн инструмент для попарного тестирования - позволит создать компактный набор значений параметров для реализации тест-кейсов, достаточных для обеспечения необходимого уровня качества.

## III. Перечень и описание возможных рисков при автоматизации  

* Отсутствие документации увеличит время на уточнение деталей и уменьшит время на проведение тестирования. Возможен срыв сроков тестирования.
* Проблема коммуникации между отделами, заказчиком так же может привести к срыву сроков тестирования.
* При взаимодействии с рабочей БД существуют риски повредить БД.
* По окончании тестирования удалить тестовые данные, не затронув реальные данные.
* Исправление кода автотестов при возможных изменениях структуры сайта: неактуальность css-селекторов.
* Нагрузка на сайт при запуске тестов, что может привести к увеличению времени загрузки страниц у пользователей.
* Недостаточный опыт тестировщика может привести к некачественному уровню проверки функциональности сервиса. Могут быть упущены некоторые дефекты.
* Срыв сроков выполнения работ может привести к финансовым и репутационным потерям.
* Стоимость и время, затраченное на автоматизацию, могут быть нерентабельны.
* Для избежания эффекта пестицида необходимо затрачивать дополнительные ресурсы на изменение тестовых данных (поддержка автотестов).
* Некачественное покрытие тестами всех возможных сценариев может привести к упущению некоторых дефектов.

## IV. Интервальная оценка с учётом рисков в часах

* Уточнение требований - не менее 5 часов
* Проведение тест-дизайна, написание тестовых сценариев - не менее 4 часов
* Развертывание окружения - 3 часа
* Написание автотестов и вспомогательных утилит, прогон автотестов - не менее 15 часов 
* Написание баг-репортов (при наличии; зависит от количества) - 5 часов
* Подготовка отчета о проведении тестирования - 5 часов

**Итого:** на автоматизацию тестирования потребуется не менее **37 часов**.

## V. План сдачи работ

Автотесты и результаты проведения тестов планируется сдать 11.07.2023.  
Отчёт по автоматизации тестирования планируется сдать 13.07.2023.