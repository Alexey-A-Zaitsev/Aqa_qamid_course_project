package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class CardPage {

    private SelenideElement heading = $x("//h3[contains(text(), 'Оплата по карте')]");
    private SelenideElement cardNumber = $x("//*[contains(text(), 'Номер карты')]");
    private SelenideElement month = $x("//*[contains(text(), 'Месяц')]");
    private SelenideElement year = $x("//*[contains(text(), 'Год')]");
    private SelenideElement owner = $x("//*[contains(text(), 'Владелец')]");
    private SelenideElement cvc = $x("//*[contains(text(), 'CVC/CVV')]");
    private SelenideElement continueButton = $(".button__text").shouldHave(text("Продолжить"));
    private SelenideElement creditButton = $(".button__text").shouldHave(text("Купить в кредит"));
    private SelenideElement successMessage = $x("//*[contains(text(), 'Успешно')]");
    private SelenideElement bankRefusedMessage = $x("//*[contains(text(), 'Банк отказал')]");
    private SelenideElement errorNotificationCardNumber = $("(//span[text()='Неверный формат'])[1]");
    private SelenideElement errorNotificationMonth = $("(//span[text()='Неверный формат'])[2]");
    private SelenideElement errorNotificationYear = $("(//span[text()='Неверный формат'])[3]");
    private SelenideElement errorNotificationCVC = $("(//span[text()='Неверный формат'])[4]");
    private SelenideElement errorNotificationOwner = $("//span[text()='Поле обязательно для заполнения']");
    private SelenideElement errorNotificationCardYearExpired = $("//span[text()='Истёк срок действия карты']");

    /* Т.к. в форме покупки невозможно вызвать одновременно ошибку с невалидным значением в полях Месяц и Год (первой обрабатывается ошибка поля год),
    а селекторы их ошибок одинаковы, будем использовать полный путь.
     */
    private SelenideElement errorNotificationInvalidYear = $("div#root>div>form>fieldset>div:nth-of-type(2)>span>span:nth-of-type(2)>span>span>span:nth-of-type(3)");
    private SelenideElement errorNotificationCardMonthExpired = $("div#root>div>form>fieldset>div:nth-of-type(2)>span>span>span>span>span:nth-of-type(3)");

    public CardPage() {
        heading.shouldBe(visible);
    }

    public void waitingSuccessMessage() {
        successMessage.shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.exactText("Успешно"));
    }

    public void waitingErrorMessage() {
        bankRefusedMessage.shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.exactText("Банк отказал"));
    }

    // Заполение поля cardNumber
    public void inputCardNumFieldApprovedInfo() {
        cardNumber.setValue(DataHelper.getApprovedCardNumb());
    }

    public void inputCardNumFieldDeclinedInfo() {
        cardNumber.setValue(DataHelper.getDeclinedCardNumb());
    }

    public void inputCardNumFieldRandomInfo() {
        cardNumber.setValue(DataHelper.getRandomValidCardNumb());
    }

    public void inputCardNumFieldInvalid15() {
        cardNumber.setValue(DataHelper.getRandom15Digits());
    }

    public void inputCardNumFieldInvalid17() {
        cardNumber.setValue(DataHelper.getRandom17Digits());
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

// Заполение поля cardNumber

    public void inputMonthFieldValidInfo() {
        month.setValue(DataHelper.getCurrentMonth());
    }

    public void inputMonthFieldLastInfo() {
        month.setValue(DataHelper.getLastMonth());
    }

    public void inputMonthFieldInvalid1() {
        month.setValue(DataHelper.getRandom1Digits());
    }

    public void inputMonthFieldInvalid3() {
        month.setValue(DataHelper.getRandom3Digits());
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
        year.setValue(DataHelper.getCurrentYear());
    }

    public void inputYearFieldInvalidLastInfo() {
        year.setValue(DataHelper.getLastYear());
    }

    public void inputYearFieldInvalidFutureInfo() {
        year.setValue(DataHelper.getFutureYear());
    }

    public void inputYearFieldInvalid1() {
        year.setValue(DataHelper.getRandom1Digits());
    }

    public void inputYearFieldInvalid3() {
        year.setValue(DataHelper.getRandom3Digits());
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
        owner.setValue(DataHelper.getRandom17Digits());
    }

    public void inputOwnerFieldSpec() {
        owner.setValue(DataHelper.getSpecialSymbols());
    }

    public void inputOwnerFieldSpaces() {
        owner.setValue(DataHelper.getSpaces());
    }

    // Заполение поля cvc
    public void inputCvcFieldValidInfo() {
        cvc.setValue(DataHelper.getRandom3Digits());
    }

    public void inputCvcFieldInvalid1() {
        cvc.setValue(DataHelper.getRandom1Digits());
    }

    public void inputCvcFieldInvalid4() {
        cvc.setValue(DataHelper.getRandom4Digits());
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

    // Нажатие на кнопки
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

}
