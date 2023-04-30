package ru.netology.web.test;

import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.web.data.DataHelper.*;

class MoneyTransferNegativeTest {

    LoginPage loginPage;
    DashboardPage dashboardPage;

    @Test
        // негативны тест (не верный логин)
    void shouldInvalidAuthInfo() {
        loginPage = open("http://localhost:9999", LoginPage.class);
        var invalidAuthInfo = DataHelper.getInvalidAuthInfo();
        loginPage.invalidLogin(invalidAuthInfo);
        $("[data-test-id='error-notification']")
                .shouldHave(text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(visible);
    }

    @Test
        // негативный тест (не верный код)
    void shouldInvalidVerificationCode() {
        loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var invalidVerificationCode = DataHelper.getVerificationCodeForInvalid();
        verificationPage.invalidVerify(invalidVerificationCode);
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(text("Ошибка! Неверно указан код! Попробуйте ещё раз."))
                .shouldBe(visible);
    }

    @Test
        // негативный тест (не верный номер карты)
    void shouldInvalidCard() {
        loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCard = getFirstCardNumber();
        var secondCard = getSecondCardNumber();
        var invalidCard = getInvalidCardNumber();
        var firstCardBalance = dashboardPage.getCardBalance(firstCard);
        var secondCardBalance = dashboardPage.getCardBalance(secondCard);
        var amount = generateValidAmount(firstCardBalance);
        var transactionPage = dashboardPage.transferMoney(secondCard);
        transactionPage.invalidtransferOfMoney(String.valueOf(amount), invalidCard);

        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(text("Ошибка! Произошла ошибка"))
                .shouldBe(visible);
    }
}