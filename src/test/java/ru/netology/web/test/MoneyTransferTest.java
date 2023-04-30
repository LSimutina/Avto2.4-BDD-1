package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;

class MoneyTransferTest {

    LoginPage loginPage;
    DashboardPage dashboardPage;

    @BeforeEach
    void setup() {
        loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test // позитивный тест
    void shouldTransferMoneyFirstToSecond() {
        var firstCard = getFirstCardNumber();
        var secondCard = getSecondCardNumber();
        var firstCardBalance = dashboardPage.getCardBalance(firstCard);
        var secondCardBalance = dashboardPage.getCardBalance(secondCard);
        var amount = generateValidAmount(firstCardBalance);
        var expectedBalanceFirstCard = firstCardBalance - amount;
        var expectedBalanceSecondCard = secondCardBalance + amount;
        var transactionPage = dashboardPage.transferMoney(secondCard);
        dashboardPage = transactionPage.transferOfMoneyValid(String.valueOf(amount), firstCard);
        var actualBalanceFirst = dashboardPage.getCardBalance(firstCard);
        var actualBalanceSecond = dashboardPage.getCardBalance(secondCard);

        assertEquals(expectedBalanceFirstCard, actualBalanceFirst);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecond);
    }

    @Test // позитивный тест
    void shouldTransferMoneySecondToFirst() {
        var firstCard = getFirstCardNumber();
        var secondCard = getSecondCardNumber();
        var firstCardBalance = dashboardPage.getCardBalance(firstCard);
        var secondCardBalance = dashboardPage.getCardBalance(secondCard);
        var amount = generateValidAmount(secondCardBalance);
        var expectedBalanceFirstCard = firstCardBalance + amount;
        var expectedBalanceSecondCard = secondCardBalance - amount;
        var transactionPage = dashboardPage.transferMoney(firstCard);
        dashboardPage = transactionPage.transferOfMoneyValid(String.valueOf(amount), secondCard);
        var actualBalanceFirst = dashboardPage.getCardBalance(firstCard);
        var actualBalanceSecond = dashboardPage.getCardBalance(secondCard);

        assertEquals(expectedBalanceFirstCard, actualBalanceFirst);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecond);
    }
}