package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ChoosingPaymentMethod {
    private SelenideElement heading = $x("//h2[contains(text(), 'Путешествие дня')]");

    public ChoosingPaymentMethod() {
        heading.shouldBe(visible);
    }

    private SelenideElement cardButton = $(".button__text").shouldHave(text("Купить"));
    private SelenideElement creditButton = $(".button__text").shouldHave(text("Купить в кредит"));

    // Нажатие кнопки "Купить"
    public ChoosingPaymentMethod buyCard() {
        cardButton.click();
        return new ChoosingPaymentMethod();
    }

    // Нажатие кнопки "Купить в кредит"
    public ChoosingPaymentMethod buyCredit() {
        creditButton.click();
        return new ChoosingPaymentMethod();
    }

}
