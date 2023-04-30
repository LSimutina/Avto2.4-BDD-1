package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static java.lang.String.valueOf;

public class TransactionPage {
    private SelenideElement sumAmount = $("[data-test-id='amount'] input");
    private SelenideElement fromСard = $("[data-test-id='from'] input");
    private SelenideElement replenish = $("[data-test-id='action-transfer']");
    private SelenideElement transferHead = $(byText("Пополнение карты"));
    private SelenideElement errorMessage = $("[data-test-id='error-message']");

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

    // для невалидного теста
    public void invalidtransferOfMoney(String amount, DataHelper.InvalidCardsInfo invalidCardsInfo) {
        sumAmount.setValue(amount);
        fromСard.setValue(invalidCardsInfo.getCardNumberInvalid());
        replenish.click();
    }

    public void findErrorMessage(String expectedText){
        errorMessage.shouldBe(exactText(expectedText), Duration.ofSeconds(15)).shouldBe(visible);
    }

    public void invalidCard() {
        $(".notification__content").should(Condition.text("Ошибка! Произошла ошибка"));
    }

    public void invalidLimin() {
        $(".notification__title").should(Condition.text("Ошибка"));
    }
}