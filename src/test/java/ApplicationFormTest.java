import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.cssSelector;

@Data
public class ApplicationFormTest {
    private SelenideElement form;
    String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    @BeforeEach
    void setup(){
        open("http://localhost:9999");
        form = $("[action]");
    }

    @Nested
    public class FullyValid {

        @Test
        void shouldSubmitRequest() {
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Новосибирск");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("Олег Юдыцкий");
            form.$(cssSelector("[name=phone]")).sendKeys("+79137049918");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(cssSelector(".notification__content")).waitUntil(Condition.visible,15000).shouldHave(text(date));
        }

        @Test
        void shouldSubmitRequestIfCityWithHyphen() {
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Йошкар-Ола");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("Олег Юдыцкий");
            form.$(cssSelector("[name=phone]")).sendKeys("+79137049918");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(cssSelector(".notification__content")).waitUntil(Condition.visible,15000).shouldHave(text(date));
        }

        @Test
        void shouldSubmitRequestIfDateIsValid() {
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Новосибирск");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("Олег Юдыцкий");
            form.$(cssSelector("[name=phone]")).sendKeys("+79137049918");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(cssSelector(".notification__content")).waitUntil(Condition.visible,15000).shouldHave(text(date));
        }

        @Test
        void shouldSubmitRequestIfNameWithHyphen() {
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Новосибирск");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("Олег Петров-Водкин");
            form.$(cssSelector("[name=phone]")).sendKeys("+79137049918");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(cssSelector(".notification__content")).waitUntil(Condition.visible,15000).shouldHave(text(date));
        }
    }

    @Nested
    public class InputCityTests {

        @Test
        void shouldNotSubmitRequestIfCityIsEmpty() {
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("Олег Юдыцкий");
            form.$(cssSelector("[name=phone]")).sendKeys("+79137049918");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Поле обязательно для заполнения")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfCityWithUppercase() {
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("НОВОСИБИРСК");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("Олег Юдыцкий");
            form.$(cssSelector("[name=phone]")).sendKeys("+79137049918");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Поле обязательно для заполнения")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfCityWithLowercase() {
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("новосибирск");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("Олег Юдыцкий");
            form.$(cssSelector("[name=phone]")).sendKeys("+79137049918");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Поле обязательно для заполнения")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfCityIncorrect() {
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Шамбала");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("Олег Юдыцкий");
            form.$(cssSelector("[name=phone]")).sendKeys("+79137049918");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Доставка в выбранный город недоступна")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfCityWithIrrelevantSymbols() {
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Novosibirsk");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("Олег Юдыцкий");
            form.$(cssSelector("[name=phone]")).sendKeys("+79137049918");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Доставка в выбранный город недоступна")).waitUntil(Condition.visible, 15000);
        }
    }

    @Nested
    public class InputDateTests {

        @BeforeEach
        void setup(){
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Новосибирск");
        }

        @Test
        void shouldNotSubmitRequestIfDateIsEmpty() {
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys("");
            form.$(cssSelector("[name=name]")).sendKeys("Олег Юдыцкий");
            form.$(cssSelector("[name=phone]")).sendKeys("+79137049918");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Неверно введена дата")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfDateIsWrong() {
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys("31.12.2019");
            form.$(cssSelector("[name=name]")).sendKeys("Олег Юдыцкий");
            form.$(cssSelector("[name=phone]")).sendKeys("+79137049918");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Заказ на выбранную дату невозможен")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfDateWithSymbols() {
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys("#$@@");
            form.$(cssSelector("[name=name]")).sendKeys("Олег Юдыцкий");
            form.$(cssSelector("[name=phone]")).sendKeys("+79137049918");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Неверно введена дата")).waitUntil(Condition.visible, 15000);
        }
    }

    @Nested
    public class InputNameTests {

        @BeforeEach
        void setup(){
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Новосибирск");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
        }

        @Test
        void shouldNotSubmitRequestIfEmpty() {
            form.$(cssSelector("[name=name]")).sendKeys("");
            form.$(cssSelector("[name=phone]")).sendKeys("+79137049918");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Поле обязательно для заполнения")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfInUppercase() {
            form.$(cssSelector("[name=name]")).sendKeys("ОЛЕГ ЮДЫЦКИЙ");
            form.$(cssSelector("[name=phone]")).sendKeys("+79137049918");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfInLowercase() {
            form.$(cssSelector("[name=name]")).sendKeys("олег юдыцкий");
            form.$(cssSelector("[name=phone]")).sendKeys("+79137049918");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfOnlyName() {
            form.$(cssSelector("[name=name]")).sendKeys("Олег");
            form.$(cssSelector("[name=phone]")).sendKeys("+79137049918");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Поле обязательно для заполнения")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfNameAndSurnameWith30Letters() {
            form.$(cssSelector("[name=name]")).sendKeys("Фффффффффффффффффффффффффффффф Аааааааааааааааааааааааааааааа");
            form.$(cssSelector("[name=phone]")).sendKeys("+79137049918");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfInLatinLetters() {
            form.$(cssSelector("[name=name]")).sendKeys("Oleg Yuditskiy");
            form.$(cssSelector("[name=phone]")).sendKeys("+79137049918");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfNameWithSymbols() {
            form.$(cssSelector("[name=name]")).sendKeys("#@%%$");
            form.$(cssSelector("[name=phone]")).sendKeys("+79137049918");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 15000);
        }
    }

    @Nested
    public class InputTelTests {

        @BeforeEach
        void setup(){
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Новосибирск");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("Олег Юдыцкий");
        }

        @Test
        void shouldNotSubmitRequestIfEmpty() {
            form.$(cssSelector("[name=phone]")).sendKeys("");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Поле обязательно для заполнения")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestWithoutPlus() {
            form.$(cssSelector("[name=phone]")).sendKeys("79137049918");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfPhoneIs1Number() {
            form.$(cssSelector("[name=phone]")).sendKeys("+7");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestWithTenNumbers() {
            form.$(cssSelector("[name=phone]")).sendKeys("+7913704991");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestWithTwelveNumbers() {
            form.$(cssSelector("[name=phone]")).sendKeys("+791370499181");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfPhoneIsLetters() {
            form.$(cssSelector("[name=phone]")).sendKeys("+апыавыаывп");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).waitUntil(Condition.visible, 15000);
        }

        @Test
        void shouldNotSubmitRequestIfPhoneContainsSymbols() {
            form.$(cssSelector("[name=phone]")).sendKeys("№%@##$");
            form.$(cssSelector("[data-test-id=agreement]")).click();
            form.$(byText("Забронировать")).click();
            $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).waitUntil(Condition.visible, 15000);
        }
    }

    @Nested
    public class AgreementTests {

        @Test
        void shouldNotSubmitRequestWithoutAgreement() {
            form.$(cssSelector("[data-test-id=city] input")).sendKeys("Новосибирск");
            form.$(cssSelector("[data-test-id=date] input")).doubleClick().sendKeys(Keys.DELETE);
            form.$(cssSelector("[data-test-id=date] input")).sendKeys(date);
            form.$(cssSelector("[name=name]")).sendKeys("Олег Юдыцкий");
            form.$(cssSelector("[name=phone]")).sendKeys("+79137049918");
            form.$(byText("Забронировать")).click();
            $("[data-test-id=agreement].input_invalid .checkbox__text").shouldHave(text("Я соглашаюсь с условиями обработки"));
        }
    }
}
