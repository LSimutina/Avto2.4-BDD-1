package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransactionPage {
    private SelenideElement sumAmount = $("[data-test-id='amount'] input");
    private SelenideElement fromСard = $("[data-test-id='from'] input");
    private SelenideElement replenish = $("[data-test-id='action-transfer']");
    private SelenideElement transferHead = $(byText("Пополнение карты"));
    private SelenideElement errorMessage = $("[data-test-id='error-message']");
    private SelenideElement errorCard = $("[data-test-id='error-notification'] .notification__content");

    public TransactionPage(){
        transferHead.shouldBe(visible);
    }

    public DashboardPage transferOfMoneyValid(String amount, DataHelper.CardsInfo cardsInfo) {
        transferOfMoney(amount, cardsInfo);
        return new DashboardPage();
    }

    public void transferOfMoney(String amount, DataHelper.CardsInfo cardsInfo) {
        sumAmount.setValue(amount);
        fromСard.setValue(cardsInfo.getCardNumber());
        replenish.click();
    }

        public void invalidTransferOfMoney(String amount, DataHelper.CardsInfo cardsInfo) {
        transferOfMoney(amount, cardsInfo);
        errorCard.shouldHave(text("Ошибка! Произошла ошибка"))
                .shouldBe(visible);
    }

    public void findErrorMessage(String expectedText){
        errorMessage.shouldBe(exactText(expectedText), Duration.ofSeconds(15)).shouldBe(visible);
    }

    public void invalidLimin() {
        $(".notification__title").should(Condition.text("Ошибка"));
    }
}