package test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {
    @BeforeEach
    void loginToAccount() {
        // Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    @DisplayName("Should transfer money from the first to the second card")
    void shouldTransferFrom1to2card() {
        Configuration.holdBrowserOpen = true;
        var transferAmount = 2000;
        var dashboardPage = new DashboardPage();
        var initialBalanceFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCard().getId());
        var initialBalanceSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCard().getId());
        dashboardPage.replenishmentSecondCard();
        var cardReplenishmentPage = new CardReplenishmentPage();
        cardReplenishmentPage.transferFromCardToCard(String.valueOf(transferAmount), DataHelper.getFirstCard());
        var firstCardBalance = dashboardPage.getCardBalance(DataHelper.getFirstCard().getId());
        var secondCardBalance = dashboardPage.getCardBalance(DataHelper.getSecondCard().getId());
        assertEquals(initialBalanceFirstCard - transferAmount, firstCardBalance);
        assertEquals(initialBalanceSecondCard + transferAmount, secondCardBalance);
    }

    @Test
    @DisplayName("Should transfer money from the second to the first card")
    void shouldTransferFrom2to1card() {
       // Configuration.holdBrowserOpen = true;
        var transferAmount = 1500;
        var dashboardPage = new DashboardPage();
        var initialBalanceFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCard().getId());
        var initialBalanceSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCard().getId());
        dashboardPage.replenishmentFirstCard();
        var cardReplenishmentPage = new CardReplenishmentPage();
        cardReplenishmentPage.transferFromCardToCard(String.valueOf(transferAmount), DataHelper.getSecondCard());
        var firstCardBalance = dashboardPage.getCardBalance(DataHelper.getFirstCard().getId());
        var secondCardBalance = dashboardPage.getCardBalance(DataHelper.getSecondCard().getId());
        assertEquals(initialBalanceFirstCard + transferAmount, firstCardBalance);
        assertEquals(initialBalanceSecondCard - transferAmount, secondCardBalance);
    }

    @Test
    @DisplayName("Should not transfer from first card an amount greater than the balance of the first card")
    void shouldNotTransferAmountGreaterFirsCardBalance() {
       // Configuration.holdBrowserOpen = true;
        var transferAmount = 55_000;
        var dashboardPage = new DashboardPage();
        var initialBalanceFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCard().getId());
        var initialBalanceSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCard().getId());
        dashboardPage.replenishmentSecondCard();
        var cardReplenishmentPage = new CardReplenishmentPage();
        cardReplenishmentPage.transferFromCardToCard(String.valueOf(transferAmount), DataHelper.getFirstCard());
        cardReplenishmentPage.waitingError();
        assertEquals(initialBalanceFirstCard, dashboardPage.getCardBalance(DataHelper.getFirstCard().getId()));
        assertEquals(initialBalanceSecondCard, dashboardPage.getCardBalance(DataHelper.getSecondCard().getId()));
    }

    @Test
    @DisplayName("Should not transfer from first card an amount greater than the balance of the second card")
    void shouldNotTransferAmountGreaterSecondCardBalance() {
      //  Configuration.holdBrowserOpen = true;
        var transferAmount = 155_000;
        var dashboardPage = new DashboardPage();
        var initialBalanceFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCard().getId());
        var initialBalanceSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCard().getId());
        dashboardPage.replenishmentFirstCard();
        var cardReplenishmentPage = new CardReplenishmentPage();
        cardReplenishmentPage.transferFromCardToCard(String.valueOf(transferAmount), DataHelper.getSecondCard());
        cardReplenishmentPage.waitingError();
        assertEquals(initialBalanceFirstCard, dashboardPage.getCardBalance(DataHelper.getFirstCard().getId()));
        assertEquals(initialBalanceSecondCard, dashboardPage.getCardBalance(DataHelper.getSecondCard().getId()));
    }

    @Test
    @DisplayName("Should not transfer from the first card if the amount field is empty")
    void shouldNotTransferFromFirstCardIfAmountEmpty() {
       // Configuration.holdBrowserOpen = true;
        var dashboardPage = new DashboardPage();
        var initialBalanceFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCard().getId());
        var initialBalanceSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCard().getId());
        dashboardPage.replenishmentFirstCard();
        var cardReplenishmentPage = new CardReplenishmentPage();
        cardReplenishmentPage.transferWithoutAmount(DataHelper.getSecondCard());
        cardReplenishmentPage.waitingError();
        assertEquals(initialBalanceFirstCard, dashboardPage.getCardBalance(DataHelper.getFirstCard().getId()));
        assertEquals(initialBalanceSecondCard, dashboardPage.getCardBalance(DataHelper.getSecondCard().getId()));
    }

}
