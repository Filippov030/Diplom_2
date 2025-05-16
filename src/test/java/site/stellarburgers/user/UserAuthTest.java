package site.stellarburgers.user;

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
    public void successfulLoginTest() {
        successfulLogin();
    }


    @Test
    @DisplayName("Ошибка 403 при создании дубликата пользователя")
    public void createDuplicateUserTest() {
        createUserDouble();
    }

    @Test
    @DisplayName("Логин пользователя изменено успешно")
    public void updateUserMailDataTest() {
        updateMailData();
    }

    @Test
    @DisplayName("Имя пользователя изменено успешно")
    public void updateUserNameDataTest() {
        updateNameData();
    }

    @Test
    @DisplayName("Пароль пользователя изменен успешно")
    public void updateUserPassDataTest() {
        updatePassData();
    }


    @Step("Успешная авторизация статус 200, accessToken присутствует")
    private void successfulLogin() {
        UserLogin validCredentials = new UserLogin(Data.EMAIL, Data.PASSWORD);
        Response response = userSteps.loginUser(validCredentials);

        assertThat(response.statusCode(), equalTo(SC_OK));
        assertThat(response.path("accessToken"), notNullValue());
    }


    @Step("Создать пользователя c существующим Логином и паролем")
    private void createUserDouble() {
        CreateNewUser user = new CreateNewUser(Data.EMAIL, Data.PASSWORD, Data.NAME);
        Response createResponse = userSteps.createNewUser(user);
        assertThat(createResponse.statusCode(), equalTo(SC_FORBIDDEN));
        assertThat(createResponse.path("message"), equalTo("User already exists"));
    }


    @Step("Изменить емэйл авторизованного пользователя")
    private void updateMailData() {
        UserUpdate updateData = new UserUpdate();
        updateData.setEmail("kocha@ya.ru");

        Response createResponse = userSteps.updateUser(userToken, updateData);
        assertThat(createResponse.statusCode(), equalTo(SC_OK));
        assertThat(createResponse.path("user.email"), equalTo("kocha@ya.ru"));
    }


    @Step("Изменить Имя авторизованного пользователя")
    private void updateNameData() {
        UserUpdate updateData = new UserUpdate();
        updateData.setEmail("kocha@ya.ru");
        updateData.setName("kovkv");

        Response createResponse = userSteps.updateUser(userToken, updateData);
        assertThat(createResponse.statusCode(), equalTo(SC_OK));
        assertThat(createResponse.path("user.name"), equalTo("kovkv"));
    }

    @Step("Изменить пароль авторизованного пользователя")
    private void updatePassData() {
        UserUpdate updateData = new UserUpdate();
        updateData.setEmail("kocha@ya.ru");
        updateData.setPassword("234323");
        Response createResponse = userSteps.updateUser(userToken, updateData);
        assertThat(createResponse.statusCode(), equalTo(SC_OK));
    }


}
