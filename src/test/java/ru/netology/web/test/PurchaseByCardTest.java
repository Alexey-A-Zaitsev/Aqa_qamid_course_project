package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.web.data.SQLHelper;
import ru.netology.web.page.CardPage;
import ru.netology.web.page.ChoosingPaymentMethod;

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
        open("http://localhost:8080");
        var choosingMethod = new ChoosingPaymentMethod();
        choosingMethod.buyCard();
    }

    @Test
    @DisplayName("Успешная покупка тура по карте")
    void shouldSuccessBuyTourByCard() {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "900x1300";

        var page = new CardPage();
        page.clickCardNumber();
        page.inputCardNumFieldApprovedInfo();
        page.clickMonth();
        page.inputMonthFieldValidInfo();
        page.clickYear();
        page.inputYearFieldValidInfo();
        page.clickOwner();
        page.inputOwnerFieldValidInfo();
        page.clickCvc();
        page.inputCvcFieldValidInfo();
        page.continueButtonClick();
        page.waitingSuccessMessage();
        assertEquals("APPROVED", SQLHelper.getPaymentTransactionStatus());


    }


}
