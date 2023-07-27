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

    /* // Генераторы случайных последовательностей цифр
    public static String getRandom1Digits() {
        return faker.numerify("#");
    }

    public static String getRandom2Digits() {
        return faker.numerify("##");
    }

    public static String getRandom3Digits() {
        return faker.numerify("###");
    }

    public static String getRandom4Digits() {
        return faker.numerify("####");
    }

    public static String getRandom15Digits() {
        return faker.numerify("###############");
    }

    // для уверенности в том, что случайная последовательность будет валидной для номера карты
    public static String getRandom16Digits() {
        return faker.business().creditCardNumber();
    }

    public static String getRandom17Digits() {
        return faker.numerify("#################");
    }
*/

    // Карты
    // Получаем номер тестовой APPROVED карты
    public static String getApprovedCardNumb() {
        return "1111 2222 3333 4444";
    }

    // Получаем номер тестовой DECLINED карты
    public static String getDeclinedCardNumb() {
        return "5555 6666 7777 8888";
    }

//    // Получаем случайный валидный номер карты.
//    public static String getRandomValidCardNumb() {
//        return faker.business().creditCardNumber();
//    }

    // Месяц

    public static String getMonth(int shiftedMonth) {
        return LocalDate.now().plusMonths(shiftedMonth).format(DateTimeFormatter.ofPattern("MM"));
    }

    // Год
    public static String getYear(int shiftedYear) {
        return LocalDate.now().plusMonths(shiftedYear).format(DateTimeFormatter.ofPattern("YY"));
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
        return " " + faker.name().firstName() + " ";
    }

    // Получаем вледельца из одного слова
    public static String getValidNameOneWord() {
        return faker.name().firstName();
    }

    // CVC/CVV
    /* Для проверок поля CVC/CVV будем применять результат генераторв случайных чисел,
    для провверки ввода букв используем один из методов ввода имени владельца
     */



/*    public static String createJSON(Card info) {
        return "{\n" +
                "  \"number\": \"" + info.getCardNumber() + "\",\n" +
                "  \"year\": \"" + info.getMonth() + "\",\n" +
                "  \"month\": \"" + info.getYear() + "\",\n" +
                "  \"holder\": \"" + info.getCardOwner() + "\",\n" +
                "  \"cvc\": \"" + info.getCvc() + "\"\n" +
                "}";
    }*/


}