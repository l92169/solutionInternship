## Отбор на стажировку #FRESHQIWI 2023 Backend

Реализовать консольную утилиту, которая выводит курсы валют ЦБ РФ за определенную дату. Для получения курсов необходимо использовать официальный API ЦБ РФ https://www.cbr.ru/development/sxml/.


Интерфейс:
    
    currency_rates --code=USD --date=2022-10-08

Описание параметров:

    code - код валюты в формате ISO 4217
    date - дата в формате YYYY-MM-DD

Вывод:

    USD (Доллар США): 61,2475

Запуск:
    
    git clone https://github.com/l92169/FRESHQIWI.git
    cd src/main/java/org/example
    java CurrencyRates.java --code=USD --date=2020-02-02

Реализованы тесты