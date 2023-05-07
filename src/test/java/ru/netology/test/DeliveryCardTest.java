package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {

    private String setCurrentDate(int days) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate newDate = LocalDate.now().plusDays(days);
        return newDate.format(formatter);
    }

    @Test
    void shoudReservationDeliveryCard() {
        String meetingDate = setCurrentDate(9);

        Configuration.holdBrowserOpen = true;
        Configuration.timeout = (15000);
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Рязань");
        SelenideElement dateInput = $("[data-test-id='date'] input");
        dateInput.sendKeys(Keys.LEFT_SHIFT, Keys.HOME, Keys.BACK_SPACE);
        dateInput.setValue(meetingDate);
        $("[name='name']").setValue("Гусев Иван");
        $("[name='phone']").setValue("+79164535391");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__content']/ancestor-or-self::button[@type='button']").click();

        $x("//div[@data-test-id='notification']//div[@class='notification__content']")
                .shouldHave(visible, text("Встреча успешно забронирована на " + meetingDate));


    }


    @Test
    public void testCityFieldEmpty() {
        String meetingDate = setCurrentDate(1);

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("");
        SelenideElement dateInput = $("span[data-test-id='date'] input");
        dateInput.sendKeys(Keys.LEFT_SHIFT, Keys.HOME, Keys.BACK_SPACE);
        dateInput.setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Петров-Водкин Ян");
        $("[data-test-id='phone'] input").setValue("+9995348756");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__content']/ancestor-or-self::button[@type='button']").click();

        $x("//span[@data-test-id='city']//span[@class='input__sub']")
                .shouldHave(exactText("Поле обязательно для заполнения"), visible);
    }

    @Test
    public void tesFieldNameLatinFont() {
        String meetingDate = setCurrentDate(3);

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        SelenideElement dateInput = $("span[data-test-id='date'] input");
        dateInput.sendKeys(Keys.LEFT_SHIFT, Keys.HOME, Keys.BACK_SPACE);
        dateInput.setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Gusev Ivan");
        $("[data-test-id='phone'] input").setValue("+9995348756");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__content']/ancestor-or-self::button[@type='button']").click();

        $x("//span[@data-test-id='name']//span[@class='input__sub']")
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."), visible);
    }


}
