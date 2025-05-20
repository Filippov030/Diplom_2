package site.stellarburgers.user;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import site.stellarburgers.BaseTest;
import site.stellarburgers.data.Data;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;


public class UserAuthTest extends BaseTest {


    @Test
    @DisplayName("Успешная авторизация пользователя")
    @Description("Проверка получения кода 200 и accessToken при успешной авторизации клиента")
    public void successfulLoginTest() {
        UserLogin validCredentials = new UserLogin(userEmail, userPassword);
        Response response = userSteps.loginUser(validCredentials);

        assertThat(response.statusCode(), equalTo(SC_OK));
        assertThat(response.path("accessToken"), notNullValue());
    }


    @Test
    @DisplayName("Ошибка 403 при создании дубликата пользователя")
    @Description("Проверка получения ошибка 403 при попытке регистрации пользователя с уже существующими кредами")
    public void createDuplicateUserTest() {
        CreateNewUser user = new CreateNewUser(userEmail, userPassword, userName);
        Response createResponse = userSteps.createNewUser(user);
        assertThat(createResponse.statusCode(), equalTo(SC_FORBIDDEN));
        assertThat(createResponse.path("message"), equalTo("User already exists"));
    }

    @Test
    @DisplayName("Логин пользователя изменено успешно")
    @Description("Проверка получения кода 200 и Успешного изменения Логина авторизованного пользователя")
    public void updateUserMailDataTest() {
        UserUpdate updateData = new UserUpdate();
        updateData.setEmail("kocha@ya.ru");

        Response createResponse = userSteps.updateUser(userToken, updateData);
        assertThat(createResponse.statusCode(), equalTo(SC_OK));
        assertThat(createResponse.path("user.email"), equalTo("kocha@ya.ru"));
    }

    @Test
    @DisplayName("Имя пользователя изменено успешно")
    @Description("Проверка получения кода 200 и Успешного изменения Имени авторизованного пользователя")
    public void updateUserNameDataTest() {
        UserUpdate updateData = new UserUpdate();
        updateData.setEmail("kocha@ya.ru");
        updateData.setName("kovkv");

        Response createResponse = userSteps.updateUser(userToken, updateData);
        assertThat(createResponse.statusCode(), equalTo(SC_OK));
        assertThat(createResponse.path("user.name"), equalTo("kovkv"));
    }

    @Test
    @DisplayName("Пароль пользователя изменен успешно")
    @Description("Проверка получения кода 200 и Успешного изменения паролья авторизованного пользователя")
    public void updateUserPassDataTest() {
        UserUpdate updateData = new UserUpdate();
        updateData.setEmail("kocha@ya.ru");
        updateData.setPassword("234323");
        Response createResponse = userSteps.updateUser(userToken, updateData);
        assertThat(createResponse.statusCode(), equalTo(SC_OK));
    }
}
