package ru.netology.web.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.APIHelper;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.SQLHelper;

public class APITest {

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


    @Test
    public void shouldBeApprovedAfterPaymentWithCard() {
        APIHelper.payWithCard(200, "APPROVED", DataHelper.status(DataHelper.getApprovedCardNumb()));
        SQLHelper.getPaymentTransactionStatus();

    }

    @Test
    public void shouldBeDeclinedAfterPaymentWithCard() {
        APIHelper.payWithCard(200, "DECLINED", DataHelper.status(DataHelper.getDeclinedCardNumb()));
        SQLHelper.getPaymentTransactionStatus();

    }


}
