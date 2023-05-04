package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {

    private @NotNull String setCurrentDate(int days) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        LocalDate newDate = LocalDate.now().plusDays(days);
        return newDate.format(formatter);
    }

    @Test
    void shoudReservationDeliveryCard() {
        String meetingDate = setCurrentDate(4);

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Рязань");
        SelenideElement dateInput = $("[data-test-id='date'] input");
        dateInput.sendKeys(Keys.LEFT_SHIFT, Keys.HOME,Keys.BACK_SPACE);
        dateInput.setValue(meetingDate);
        $("[name=name]").setValue("Гусев Иван");
        $("[name=phone]").setValue("+79164535391");
        $("[data-test-id=agreement]").click();
        $x("//span[@class='button__text']/../../../button").click();
    }




    @Test
    public void testCityFieldEmpty() {
        String meetingDate = setCurrentDate (3);

        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("");
        SelenideElement dateInput = $("span[data-test-id='date'] input");
        dateInput.sendKeys(Keys.LEFT_SHIFT, Keys.HOME, Keys.BACK_SPACE);
        dateInput.setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Петров-Водкин Вова");
        $("[data-test-id='phone'] input").setValue("+9995348756");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']/../../../button").click();

        $x("//span[@data-test-id='city']//span[contains(text(), 'Поле обязательно для заполнения')]").shouldBe(
                appear, Duration.ofSeconds(10));

    }
}
