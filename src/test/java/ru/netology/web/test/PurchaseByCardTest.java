package ru.netology.web.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.SQLHelper;
import ru.netology.web.page.CardPage;
import ru.netology.web.page.ChoosingPaymentMethod;
import ru.netology.web.page.CreditPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PurchaseByCardTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @AfterAll
    static void databaseCleanup() {
        SQLHelper.clearDatabase();
    }

    @BeforeEach
    void setup() {
        open("http://localhost:8080/");
        var choosingMethod = new ChoosingPaymentMethod();
        choosingMethod.buyCard();
    }


    // Тестовые сценарии при покупке по карте
    @Test
    @DisplayName("Успешная покупка тура по карте")
    void shouldSuccessBuyTourByCard() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        page.inputInCardNumberField(DataHelper.getApprovedCardNumb());
        page.inputInMonthField(DataHelper.getMonth(1));
        page.inputInYearField(DataHelper.getYear(1));
        page.inputInOwnerField(DataHelper.getValidCardOwner());
        page.inputInCvcField(DataHelper.randomNumberGenerator(3));
        page.continueButtonClick();
        page.waitingSuccessMessage();
        assertEquals("APPROVED", SQLHelper.getPaymentTransactionStatus());

    }

    @Test
    @DisplayName("Отклонённая покупка тура по карте")
    void shouldBeDenied() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        page.inputInCardNumberField(DataHelper.getDeclinedCardNumb());
        page.inputInMonthField(DataHelper.getMonth(1));
        page.inputInYearField(DataHelper.getYear(1));
        page.inputInOwnerField(DataHelper.getValidCardOwner());
        page.inputInCvcField(DataHelper.randomNumberGenerator(3));
        page.continueButtonClick();
        page.waitingErrorMessage();
        assertEquals("DECLINED", SQLHelper.getPaymentTransactionStatus());

    }

    @Test
    @DisplayName("Ошибка при оплате случайной валидной картой")
    void shouldThrowAnErrorOnARandomCard() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        page.inputInCardNumberField(DataHelper.randomNumberGenerator(16));
        page.inputInMonthField(DataHelper.getMonth(1));
        page.inputInYearField(DataHelper.getYear(1));
        page.inputInOwnerField(DataHelper.getValidCardOwner());
        page.inputInCvcField(DataHelper.randomNumberGenerator(3));
        page.continueButtonClick();
        page.waitingErrorMessage();
    }

    @Test
    @DisplayName("Отображение подсказок под полями при отправке незаполненной формы")
    void shouldShowErrorsUnderAllFields() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();
        page.continueButtonClick();
        page.outputErrorNotificationCardNumber();
        page.outputErrorNotificationMonth();
        page.outputErrorNotificationYear();
        page.outputErrorNotificationOwner();
        page.outputErrorNotificationCVC();

    }

    // Блок валидации полей

    // Поле НОМЕР КАРТЫ
    @Test
    @DisplayName("ввести в поле валидный номер карты")
    void validationFieldCardNumber1() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.randomNumberGenerator(16);
        page.inputInCardNumberField(value);
        page.continueButtonClick();
        assertEquals(value, page.getValueCardField());

    }

    @Test
    @DisplayName("ввести в поле 17 цифр")
    void validationFieldCardNumber2() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.randomNumberGenerator(17);
        page.inputInCardNumberField(value);
        page.continueButtonClick();
        // сабстрингом обрезаем лишнее количество символов (>16)
        assertEquals(value.substring(0, 16), page.getValueCardField());

    }

    @Test
    @DisplayName("ввести в поле 15 цифр")
    void validationFieldCardNumber3() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.randomNumberGenerator(15);
        page.inputInCardNumberField(value);
        page.continueButtonClick();
        assertEquals(value, page.getValueCardField());
        page.outputErrorNotificationCardNumber();

    }

    @Test
    @DisplayName("ввести в поле значение на латинице")
    void validationFieldCardNumber4() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.getValidCardOwner();
        page.inputInCardNumberField(value);
        page.continueButtonClick();
        assertEquals("", page.getValueCardField());
        page.outputErrorNotificationCardNumber();

    }

    @Test
    @DisplayName("ввести в поле значение со спец. символами")
    void validationFieldCardNumber5() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.getSpecialSymbols();
        page.inputInCardNumberField(value);
        page.continueButtonClick();
        assertEquals("", page.getValueCardField());
        page.outputErrorNotificationCardNumber();

    }

    @Test
    @DisplayName("ввести в поле несколько пробелов")
    void validationFieldCardNumber6() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.getSpaces();
        page.inputInCardNumberField(value);
        page.continueButtonClick();
        assertEquals("", page.getValueCardField());
        page.outputErrorNotificationCardNumber();

    }

    // Поле МЕСЯЦ
    @Test
    @DisplayName("ввести в поле валидный номер месяца")
    void validationFieldMonth1() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.getMonth(1);
        page.inputInMonthField(value);
        page.continueButtonClick();
        assertEquals(value, page.getValueMonthField());

    }

    @Test
    @DisplayName("ввести в поле предыдущий номер месяца")
    void validationFieldMonth2() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.getMonth(-1);
        page.inputInMonthField(value);

        page.inputInCardNumberField(DataHelper.getApprovedCardNumb());
        page.inputInYearField(DataHelper.getYear(0));
        page.inputInOwnerField(DataHelper.getValidCardOwner());
        page.inputInCvcField(DataHelper.randomNumberGenerator(3));

        page.continueButtonClick();

        assertEquals(value, page.getValueMonthField());
        page.outputErrorNotificationCardMonthExpired();

    }

    @Test
    @DisplayName("ввести в поле 1 цифру")
    void validationFieldMonth3() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.randomNumberGenerator(1);
        page.inputInMonthField(value);
        page.continueButtonClick();
        assertEquals(value, page.getValueMonthField());
        page.outputErrorNotificationMonth();

    }

    @Test
    @DisplayName("ввести в поле 3 цифры")
    void validationFieldMonth4() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.randomNumberGenerator(3);
        page.inputInMonthField(value);
        page.continueButtonClick();
        assertEquals(value.substring(0, 2), page.getValueMonthField());

    }

    @Test
    @DisplayName("ввести в поле несуществующий номер месяца (меньше минимально допустимого, нижнее граничное значение)")
    void validationFieldMonth5() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = "00";
        page.inputInMonthField(value);
        page.continueButtonClick();
        assertEquals(value, page.getValueMonthField());
        page.outputErrorNotificationMonth();

    }

    @Test
    @DisplayName("ввести в поле несуществующий номер месяца (больше максимально допустимого, верхнее граничное значение)")
    void validationFieldMonth6() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = "13";
        page.inputInMonthField(value);
        page.continueButtonClick();
        assertEquals(value, page.getValueMonthField());
        page.outputErrorNotificationMonth();

    }

    @Test
    @DisplayName("ввести в поле значение на латинице")
    void validationFieldMonth7() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.getValidNameOneWord();
        page.inputInMonthField(value);
        page.continueButtonClick();
        assertEquals("", page.getValueMonthField());

    }

    @Test
    @DisplayName("ввести в поле значение со спец. символами")
    void validationFieldMonth8() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.getSpecialSymbols();
        page.inputInMonthField(value);
        page.continueButtonClick();
        assertEquals("", page.getValueMonthField());

    }

    @Test
    @DisplayName("ввести в поле несколько пробелов)")
    void validationFieldMonth9() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.getSpaces();
        page.inputInMonthField(value);
        page.continueButtonClick();
        assertEquals("", page.getValueMonthField());

    }

    // ГОД

    @Test
    @DisplayName("ввести в поле валидный номер года")
    void validationFieldYear1() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.getYear(1);
        page.inputInYearField(value);
        page.continueButtonClick();
        assertEquals(value, page.getValueYearField());

    }

    @Test
    @DisplayName("ввести в поле более 2-х цифр")
    void validationFieldYear2() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.randomNumberGenerator(3);
        page.inputInYearField(value);
        page.continueButtonClick();
        assertEquals(value.substring(0, 2), page.getValueYearField());

    }

    @Test
    @DisplayName("ввести в поле менее 2-х цифр")
    void validationFieldYear3() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.randomNumberGenerator(1);
        page.inputInYearField(value);
        page.continueButtonClick();
        page.outputErrorNotificationYear();
    }

    @Test
    @DisplayName("ввести в поле не валидный (прошедший) номер года (меньше минимально допустимого, нижнее граничное значение)")
    void validationFieldYear4() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.getYear(-1);
        page.inputInYearField(value);

        page.inputInCardNumberField(DataHelper.getApprovedCardNumb());
        page.inputInMonthField(DataHelper.getMonth(1));
        page.inputInOwnerField(DataHelper.getValidCardOwner());
        page.inputInCvcField(DataHelper.randomNumberGenerator(3));

        page.continueButtonClick();
        page.outputErrorNotificationCardYearExpired();

    }

    @Test
    @DisplayName("ввести в поле не валидный номер года (больше максимально допустимого, верхнее граничное значение)")
    void validationFieldYear5() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.getYear(8);
        page.inputInYearField(value);

        page.inputInCardNumberField(DataHelper.getApprovedCardNumb());
        page.inputInMonthField(DataHelper.getMonth(1));
        page.inputInOwnerField(DataHelper.getValidCardOwner());
        page.inputInCvcField(DataHelper.randomNumberGenerator(3));

        page.continueButtonClick();
        page.outputErrorInvalidCardExpirationDate();

    }

    @Test
    @DisplayName("ввести в поле значение на латинице")
    void validationFieldYear6() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.getValidNameOneWord();
        page.inputInYearField(value);
        page.continueButtonClick();
        assertEquals("", page.getValueYearField());

    }

    @Test
    @DisplayName("ввести в поле значение со спец. символами")
    void validationFieldYear7() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.getSpecialSymbols();
        page.inputInYearField(value);
        page.continueButtonClick();
        assertEquals("", page.getValueYearField());

    }

    @Test
    @DisplayName("ввести в поле несколько пробелов")
    void validationFieldYear8() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.getSpaces();
        page.inputInYearField(value);
        page.continueButtonClick();
        assertEquals("", page.getValueYearField());
        page.outputErrorNotificationYear();

    }

    // ВЛАДЕЛЕЦ

    @Test
    @DisplayName("ввести в поле имя и фамилию на латинице")
    void validationFieldOwner1() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.getValidCardOwner();
        page.inputInOwnerField(value);
        page.continueButtonClick();
        assertEquals(value, page.getValueOwnerField());

    }

    @Test
    @DisplayName("ввести в поле значение, написанное через дефис")
    void validationFieldOwner2() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.getValidNameHyphen();
        page.inputInOwnerField(value);
        page.continueButtonClick();
        assertEquals(value, page.getValueOwnerField());

    }

    @Test
    @DisplayName("ввести в поле значение с несколькими пробелами")
    void validationFieldOwner3() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.getValidNameSpace();
        page.inputInOwnerField(value);
        page.continueButtonClick();
        assertEquals(value, page.getValueOwnerField());

    }

    @Test
    @DisplayName("ввести в поле имя всеми строчными латинскими буквами")
    void validationFieldOwner4() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.getValidNameLower();
        page.inputInOwnerField(value);
        page.continueButtonClick();
        assertEquals(value, page.getValueOwnerField().toUpperCase());

    }

    @Test
    @DisplayName("ввести в поле значение, начинающееся и оканчивающееся пробелом")
    void validationFieldOwner5() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.getValidNameTrimmingSpaces();
        page.inputInOwnerField(value);
        page.continueButtonClick();
        assertEquals(value, page.getValueOwnerField().trim());

    }

    @Test
    @DisplayName("ввести в поле цифровое значение")
    void validationFieldOwner6() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.randomNumberGenerator(4);
        page.inputInOwnerField(value);
        page.continueButtonClick();
        assertEquals("", page.getValueOwnerField());

    }

    @Test
    @DisplayName("ввести в поле значениями со спец символами")
    void validationFieldOwner7() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.getSpecialSymbols();
        page.inputInOwnerField(value);
        page.continueButtonClick();
        assertEquals("", page.getValueOwnerField());

    }

    @Test
    @DisplayName(" ввести в поле несколько пробелов")
    void validationFieldOwner8() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.getSpaces();
        page.inputInOwnerField(value);
        page.continueButtonClick();
        // assertEquals("", page.getValueOwnerField());

        page.outputErrorNotificationOwner();

    }

    // CVC/CVV

    @Test
    @DisplayName("ввести в поле валидное значение")
    void validationFieldCVC1() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.randomNumberGenerator(3);
        page.inputInCvcField(value);
        page.continueButtonClick();
        assertEquals(value, page.getValueCVCField());

    }

    @Test
    @DisplayName("ввести в поле 4 цифры (верхнее граничное значение")
    void validationFieldCVC2() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.randomNumberGenerator(4);
        page.inputInCvcField(value);
        page.continueButtonClick();
        assertEquals(value.substring(0, 3), page.getValueCVCField());

    }

    @Test
    @DisplayName("ввести в поле 2 цифры (нижнее граничное значение")
    void validationFieldCVC3() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.randomNumberGenerator(2);
        page.inputInCvcField(value);
        page.continueButtonClick();
        assertEquals(value, page.getValueCVCField());

        page.outputErrorNotificationCVC();

    }

    @Test
    @DisplayName("ввести в поле значение на латинице")
    void validationFieldCVC4() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.getValidCardOwner();
        page.inputInCvcField(value);
        page.continueButtonClick();
        assertEquals("", page.getValueCVCField());

    }

    @Test
    @DisplayName("ввести в поле значение со спец. символами")
    void validationFieldCVC5() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.getSpecialSymbols();
        page.inputInCvcField(value);
        page.continueButtonClick();
        assertEquals("", page.getValueCVCField());

    }

    @Test
    @DisplayName("ввести в поле несколько пробелов")
    void validationFieldCVC6() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        String value = DataHelper.getSpaces();
        page.inputInCvcField(value);
        page.continueButtonClick();
        assertEquals("", page.getValueCVCField());

        page.outputErrorNotificationCVC();

    }

    // UI

    @Test
    @DisplayName("Отображение формы \"Оплата по карте\"")
    void displayingTheFormPaymentByCard() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();
        page.displayingFormFields();

    }

    @Test
    @DisplayName("Отображение формы \"Купить в кредит\"")
    void displayingTheFormBuyOnCredit() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CreditPage();
        page.clickButtonCreditButton();
        page.displayingFormFields();

    }

    @Test
    @DisplayName("Проверка суммы оплаты")
    void shouldShowThePrice() {
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "900x900";

        var page = new CardPage();

        page.inputInCardNumberField(DataHelper.getApprovedCardNumb());
        page.inputInMonthField(DataHelper.getMonth(1));
        page.inputInYearField(DataHelper.getYear(1));
        page.inputInOwnerField(DataHelper.getValidCardOwner());
        page.inputInCvcField(DataHelper.randomNumberGenerator(3));
        page.continueButtonClick();
        page.waitingSuccessMessage();
        assertEquals(45_000, SQLHelper.getPurchaseAmount());

    }

}

























