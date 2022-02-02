package com.garbuziuk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class ParameterizedWebTest {

    @BeforeEach
    void beforeEach(){
        System.out.println("Before each test");
    }

    static Stream<Arguments> commonSearchTestDataProvider() {
        return Stream.of(
                Arguments.of("Selenide", false, List.of("1","2")),
                Arguments.of("Selenide", true, List.of("3","4"))
        );
    }

    @Test
    void gTest() {
        open("https://www.google.com");
        $("[name='q']").setValue("Selenide").pressEnter();
        $$(".g").first().shouldHave(text("Selenide"));
    }

    @Test
    void gTest2() {
        open("https://www.google.com");
        $("[name='q']").setValue("Allure").pressEnter();
        $$(".g").first().shouldHave(text("Allure"));
    }

    @ValueSource(strings = {"Allure", "Selenide", "JUnit"})
    @ParameterizedTest(name = "Test google with text data = {0}")
    void gTest3(String testData) {
        open("https://www.google.com");
        $("[name='q']").setValue(testData).pressEnter();
        $$(".g").first().shouldHave(text(testData));
    }

    //@ValueSource(strings = {"Allure", "Selenide", "JUnit"})
    @CsvSource(value = {
            "Selenide",
            "Junit"
    })
    @ParameterizedTest(name = "Test google with text data = {0}")
    void gTest4(String testData) {
        open("https://www.google.com");
        $("[name='q']").setValue(testData).pressEnter();
        $$(".g").first().shouldHave(text(testData));
    }

    @MethodSource("commonSearchTestDataProvider")
    @ParameterizedTest(name = "Test google with text data = {0}")
    void gTest5(String testData, boolean flag, List list) {
        open("https://www.google.com");
        $("[name='q']").setValue(testData).pressEnter();
        $$(".g").first().shouldHave(text(testData));

        System.out.println("Flag: " + flag);
        System.out.println("List: " + list.toString());
    }
}
