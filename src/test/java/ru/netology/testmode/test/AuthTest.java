package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        // Configuration.holdBrowserOpen = true;
        var registeredUser = getRegisteredUser("active");
        // TODO: добавить логику теста, в рамках которого будет выполнена попытка входа в личный кабинет с учётными
        //  данными зарегистрированного активного пользователя, для заполнения полей формы используйте
        //  пользователя registeredUser
        $("[data-test-id= login] .input__control").setValue(registeredUser.getLogin());
        $("[data-test-id= password] .input__control").setValue(registeredUser.getPassword());
        $("[data-test-id = action-login] .button__text").click();
        $("[id = root] h2").shouldHave(text("  Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        // Configuration.holdBrowserOpen = true;
        var notRegisteredUser = getUser("active");
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет
        //  незарегистрированного пользователя, для заполнения полей формы используйте пользователя notRegisteredUser
        $("[data-test-id= login] .input__control").setValue(notRegisteredUser.getLogin());
        $("[data-test-id= password] .input__control").setValue(notRegisteredUser.getPassword());
        $("[data-test-id = action-login] .button__text").click();
        $("[data-test-id = error-notification] .notification__content")
                .shouldHave(text("Ошибка! " + "Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);

    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        // Configuration.holdBrowserOpen = true;
        var blockedUser = getRegisteredUser("blocked");
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет,
        //  заблокированного пользователя, для заполнения полей формы используйте пользователя blockedUser
        $("[data-test-id= login] .input__control").setValue(blockedUser.getLogin());
        $("[data-test-id= password] .input__control").setValue(blockedUser.getPassword());
        $("[data-test-id = action-login] .button__text").click();
        $("[data-test-id = error-notification] .notification__content")
                .shouldHave(text("Ошибка! " + "Пользователь заблокирован"))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        // Configuration.holdBrowserOpen = true;
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  логином, для заполнения поля формы "Логин" используйте переменную wrongLogin,
        //  "Пароль" - пользователя registeredUser
        $("[data-test-id= login] .input__control").setValue(wrongLogin);
        $("[data-test-id= password] .input__control").setValue(registeredUser.getPassword());
        $("[data-test-id = action-login] .button__text").click();
        $("[data-test-id = error-notification] .notification__content")
                .shouldHave(text("Ошибка! " + "Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);

    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        // Configuration.holdBrowserOpen = true;
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  паролем, для заполнения поля формы "Логин" используйте пользователя registeredUser,
        //  "Пароль" - переменную wrongPassword
        $("[data-test-id= login] .input__control").setValue(registeredUser.getLogin());
        $("[data-test-id= password] .input__control").setValue(wrongPassword);
        $("[data-test-id = action-login] .button__text").click();
        $("[data-test-id = error-notification] .notification__content")
                .shouldHave(text("Ошибка! " + "Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message appear if the login is empty")
    void shouldGetErrorMessageLoginEmpty() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id= password] .input__control").setValue(registeredUser.getPassword());
        $("[data-test-id = action-login] .button__text").click();
        $("[data-test-id = login] .input__sub").shouldHave(text("Поле обязательно для заполнения"))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message appear if the password is empty")
    void shouldGetErrorMessagePasswordEmpty() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id= login] .input__control").setValue(registeredUser.getLogin());
        $("[data-test-id = action-login] .button__text").click();
        $("[data-test-id = password] .input__sub").shouldHave(text("Поле обязательно для заполнения"))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message appear if the form is empty")
    void shouldGetErrorMessageFormEmpty() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id = action-login] .button__text").click();
        $("[data-test-id = login] .input__sub").shouldHave(text("Поле обязательно для заполнения"))
                .shouldBe(Condition.visible);
        $("[data-test-id = password] .input__sub").shouldHave(text("Поле обязательно для заполнения"))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message should appear if the login and password are reversed")
    void shouldGetErrorMessageIfLoginAndPasswordReversed() {
        var registeredUser = getUser("active");
        $("[data-test-id= login] .input__control").setValue(registeredUser.getPassword());
        $("[data-test-id= password] .input__control").setValue(registeredUser.getLogin());
        $("[data-test-id = action-login] .button__text").click();
        $("[data-test-id = error-notification] .notification__content")
                .shouldHave(text("Ошибка! " + "Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }

}