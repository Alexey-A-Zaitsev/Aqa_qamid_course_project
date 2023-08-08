package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private DataHelper() {
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Card {
        String cvc;
        String holder;
        String month;
        String number;
        String year;
    }

    private static final Faker faker = new Faker(new Locale("en"));

    // Ввод спец. символов
    public static String getSpecialSymbols() {
        return "$%^*@";
    }

    public static String getSpaces() {
        return " " + " " + " ";
    }

    // генератор рандомных чисел. В numberLength передаём длину желаемого числа

    public static String randomNumberGenerator(int numberLength) {
        return faker.number().digits(numberLength);
    }

    // Карты
    // Получаем номер тестовой APPROVED карты
    public static String getApprovedCardNumb() {
        return "1111 2222 3333 4444";
    }

    // Получаем номер тестовой DECLINED карты
    public static String getDeclinedCardNumb() {
        return "5555 6666 7777 8888";
    }

    // Месяц

    public static String getMonth(int shiftedMonth) {
        return LocalDate.now().plusMonths(shiftedMonth).format(DateTimeFormatter.ofPattern("MM"));
    }

    // Год
    public static String getYear(int shiftedYear) {
        return LocalDate.now().plusYears(shiftedYear).format(DateTimeFormatter.ofPattern("YY"));
    }


    // Владелец
    // Получаем валидное имя держателя карты
    public static String getValidCardOwner() {
        return faker.name().fullName();
    }

    // Получаем валидное имя, написанное через дефис
    public static String getValidNameHyphen() {
        return faker.name().firstName() + "-" + faker.name().firstName() + " " + faker.name().lastName();
    }

    // Получаем валидное имя, написанное с несколькими пробелами
    public static String getValidNameSpace() {
        return faker.name().firstName() + " " + faker.name().firstName() + " " + faker.name().lastName();
    }

    // Получаем валидное имя, написанное в нижнем регистре
    public static String getValidNameLower() {
        return faker.name().firstName().toLowerCase() + " " + faker.name().lastName().toLowerCase();
    }

    // Получаем валидное имя с пробелами перед и после значения. Используем для проверки обрезки пробелов
    public static String getValidNameTrimmingSpaces() {
        return " " + faker.name().fullName() + " ";
    }

    // Получаем вледельца из одного слова
    public static String getValidNameOneWord() {
        return faker.name().firstName();
    }


    public static Card status(String cardNum) {
        return new Card(
                randomNumberGenerator(3),
                getValidCardOwner(),
                getMonth(1),
                cardNum,
                getYear(1)

        );
    }


}