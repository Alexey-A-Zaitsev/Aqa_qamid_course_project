package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class CardPage {

    private SelenideElement heading =  $(byText("Оплата по карте"));
    private SelenideElement cardNumber = $("[placeholder=\"0000 0000 0000 0000\"]");
    private SelenideElement month = $("[placeholder=\"08\"]");
    private SelenideElement year = $("[placeholder=\"22\"]");
    private SelenideElement owner = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cvc = $("[placeholder=\"999\"]");
    private SelenideElement buyButton =  $(byText("Купить"));
    private SelenideElement creditButton = $(byText("Купить в кредит"));
    private SelenideElement continueButton = $(byText("Продолжить"));
    private SelenideElement successMessage = $(".notification_status_ok .notification__title");
    private SelenideElement bankRefusedMessage = $(".notification_status_error .notification__content");
    private SelenideElement errorNotificationCardNumber = $(byText("Номер карты")).parent().$(byText("Неверный формат"));;
    private SelenideElement errorNotificationMonth = $(byText("Месяц")).parent().$(byText("Неверный формат"));
    private SelenideElement errorNotificationYear = $(byText("Год")).parent().$(byText("Неверный формат"));
    private SelenideElement errorNotificationCVC = $(byText("CVC/CVV")).parent().$(byText("Неверный формат"));
    private SelenideElement errorNotificationOwner = $x("//span[text()='Поле обязательно для заполнения']");
    private SelenideElement errorNotificationCardYearExpired = $(byText("Истёк срок действия карты")).parent().$(".input__sub");


    /* Т.к. в форме покупки невозможно вызвать одновременно ошибку с невалидным значением в полях Месяц и Год (первой обрабатывается ошибка поля год),
    а селекторы их ошибок одинаковы, будем использовать полный путь.
     */
    private SelenideElement errorNotificationInvalidYear = $("div#root>div>form>fieldset>div:nth-of-type(2)>span>span:nth-of-type(2)>span>span>span:nth-of-type(3)");
    private SelenideElement errorNotificationCardMonthExpired = $("div#root>div>form>fieldset>div:nth-of-type(2)>span>span>span>span>span:nth-of-type(3)");

    public CardPage() {
        heading.shouldBe(visible);
    }

    public void waitingSuccessMessage() {
        successMessage.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public void waitingErrorMessage() {
        bankRefusedMessage.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    // Отображение полей формы

    public void displayingFormFields() {
        heading.shouldBe(visible);
        cardNumber.shouldBe(visible);
        month.shouldBe(visible);
        year.shouldBe(visible);
        owner.shouldBe(visible);
        cvc.shouldBe(visible);
        continueButton.shouldBe(visible);
        creditButton.shouldBe(visible);

    }


    // Заполение полей

    public void inputInCardNumberField(String value) {
        cardNumber.setValue(value);
    }

    public void inputInMonthField(String value) {
        month.setValue(value);
    }

    public void inputInYearField(String value) {
        year.setValue(value);
    }

    public void inputInOwnerField(String value) {
        owner.setValue(value);
    }

    public void inputInCvcField(String value) {
        cvc.setValue(value);
    }
   /*

    public void inputCardNumFieldApprovedInfo() {
        cardNumber.setValue(DataHelper.getApprovedCardNumb());
    }

    public void inputCardNumFieldDeclinedInfo() {
        cardNumber.setValue(DataHelper.getDeclinedCardNumb());
    }

    public void inputCardNumFieldRandomInfo() {
        cardNumber.setValue(DataHelper.randomNumberGenerator(16));
    }

    public void inputCardNumFieldInvalid15() {
        cardNumber.setValue(DataHelper.randomNumberGenerator(15));
    }

    public void inputCardNumFieldInvalid17() {
        cardNumber.setValue(DataHelper.randomNumberGenerator(17));
    }

    public void inputCardNumFieldLetters() {
        cardNumber.setValue(DataHelper.getValidCardOwner());
    }

    public void inputCardNumFieldSpec() {
        cardNumber.setValue(DataHelper.getSpecialSymbols());
    }

    public void inputCardNumFieldSpaces() {
        cardNumber.setValue(DataHelper.getSpaces());
    }

// Заполение поля month

    public void inputMonthFieldValidInfo() {
        month.setValue(DataHelper.getMonth(1));
    }

    public void inputMonthFieldLastInfo() {
        month.setValue(DataHelper.getMonth(-1));
    }

    public void inputMonthFieldInvalid1() {
        month.setValue(DataHelper.randomNumberGenerator(1));
    }

    public void inputMonthFieldInvalid3() {
        month.setValue(DataHelper.randomNumberGenerator(3));
    }

    public void inputMonthFieldZeroValue() {
        month.setValue("00");
    }

    public void inputMonthFieldNonExistentValue() {
        month.setValue("13");
    }

    public void inputMonthFieldLetters() {
        month.setValue(DataHelper.getValidCardOwner());
    }

    public void inputMonthFieldSpec() {
        month.setValue(DataHelper.getSpecialSymbols());
    }

    public void inputMonthFieldSpaces() {
        month.setValue(DataHelper.getSpaces());
    }

// Заполение поля Year

    public void inputYearFieldValidInfo() {
        year.setValue(DataHelper.getYear(1));
    }

    public void inputYearFieldInvalidLastInfo() {
        year.setValue(DataHelper.getYear(-1));
    }

    public void inputYearFieldInvalidFutureInfo() {
        year.setValue(DataHelper.getYear(9));
    }

    public void inputYearFieldInvalid1() {
        year.setValue(DataHelper.randomNumberGenerator(1));
    }

    public void inputYearFieldInvalid3() {
        year.setValue(DataHelper.randomNumberGenerator(3));
    }

    public void inputYearFieldLetters() {
        year.setValue(DataHelper.getValidCardOwner());
    }

    public void inputYearFieldSpec() {
        year.setValue(DataHelper.getSpecialSymbols());
    }

    public void inputYearFieldSpaces() {
        year.setValue(DataHelper.getSpaces());
    }

    // Заполение поля owner
    public void inputOwnerFieldValidInfo() {
        owner.setValue(DataHelper.getValidCardOwner());
    }

    public void inputOwnerFieldValidNameSpace() {
        owner.setValue(DataHelper.getValidNameSpace());
    }

    public void inputOwnerFieldValidNameLower() {
        owner.setValue(DataHelper.getValidNameLower());
    }

    public void inputOwnerFieldValidNameTrimmingSpaces() {
        owner.setValue(DataHelper.getValidNameTrimmingSpaces());
    }

    public void inputOwnerFieldValidNameOneWord() {
        owner.setValue(DataHelper.getValidNameOneWord());
    }

    public void inputOwnerFieldValidNameHyphen() {
        owner.setValue(DataHelper.getValidNameHyphen());
    }

    public void inputOwnerFieldDigitsValue() {
        owner.setValue(DataHelper.randomNumberGenerator(3));
    }

    public void inputOwnerFieldSpec() {
        owner.setValue(DataHelper.getSpecialSymbols());
    }

    public void inputOwnerFieldSpaces() {
        owner.setValue(DataHelper.getSpaces());
    }

    // Заполение поля cvc
    public void inputCvcFieldValidInfo() {
        cvc.setValue(DataHelper.randomNumberGenerator(3));
    }

    public void inputCvcFieldInvalid1() {
        cvc.setValue(DataHelper.randomNumberGenerator(1));
    }

    public void inputCvcFieldInvalid4() {
        cvc.setValue(DataHelper.randomNumberGenerator(4));
    }

    public void inputCvcFieldLetters() {
        cvc.setValue(DataHelper.getValidCardOwner());
    }

    public void inputCvcFieldSpec() {
        cvc.setValue(DataHelper.getSpecialSymbols());
    }

    public void inputCvcFieldSpaces() {
        cvc.setValue(DataHelper.getSpaces());
    }
*/

// Вывод сообщений об ошибках

    public void outputErrorNotificationCardNumber() {
        errorNotificationCardNumber.shouldBe(visible);
    }

    public void outputErrorNotificationMonth() {
        errorNotificationMonth.shouldBe(visible);
    }

    public void outputErrorNotificationYear() {
        errorNotificationYear.shouldBe(visible);
    }

    public void outputErrorNotificationCVC() {
        errorNotificationCVC.shouldBe(visible);
    }

    public void outputErrorNotificationOwner() {
        errorNotificationOwner.shouldBe(visible);
    }

    public void outputErrorNotificationCardYearExpired() {
        errorNotificationCardYearExpired.shouldBe(visible);
    }

    public void outputErrorInvalidCardExpirationDate() {
        errorNotificationInvalidYear.shouldBe(visible);
    }

    public void outputErrorNotificationCardMonthExpired() {
        errorNotificationCardMonthExpired.shouldBe(visible);
    }

    // Нажатие на кнопки
    public void buyButtonClick() {
        buyButton.click();
    }
    public void continueButtonClick() {
        continueButton.click();
    }

    public void creditButtonClick() {
        creditButton.click();
    }

    // очистка полей
    public void cleanCardNumField() {
        cardNumber.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
    }

    public void cleanMonthField() {
        month.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
    }

    public void cleanYearField() {
        year.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
    }

    public void cleanOwnerField() {
        owner.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
    }

    public void cleanCvcField() {
        cvc.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
    }


    // Методы извлечения данных из полей

    // Номер карты
    public String getValueCardField() {
        String getValue = cardNumber.getValue().replaceAll("\\s+", "");
        return getValue;
    }

    // Месяц
    public String getValueMonthField() {
        String getValue = month.getValue().replaceAll("\\s+", "");
        return getValue;
    }

    // Год
    public String getValueYearField() {
        String getValue = year.getValue().replaceAll("\\s+", "");
        return getValue;
    }

    // Владелец
    public String getValueOwnerField() {
        String getValue = owner.getValue();
        return getValue;
    }

    // CVC/CVV
    public String getValueCVCField() {
        String getValue = cvc.getValue().replaceAll("\\s+", "");
        return getValue;
    }





}
