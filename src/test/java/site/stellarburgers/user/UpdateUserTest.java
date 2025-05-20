package site.stellarburgers.user;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import site.stellarburgers.BaseTest;
import site.stellarburgers.data.Data;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@DisplayName("Тесты на обновление данных пользователя")
public class UpdateUserTest extends BaseTest {


    @Before
    public void generateNewTestData() {
        generateUpdateData();
    }


    @Test
    @DisplayName("Логин пользователя изменено успешно")
    @Description("Проверка получения кода 200 и Успешного изменения Логина авторизованного пользователя")
    public void updateUserMailDataTest() {
        UserUpdate updateData = new UserUpdate();
        updateData.setEmail(newMail);

        Response createResponse = userSteps.updateUser(userToken, updateData);
        assertThat(createResponse.statusCode(), equalTo(SC_OK));
        assertThat(createResponse.path("user.email"), equalTo(newMail));
    }

    @Test
    @DisplayName("Имя пользователя изменено успешно")
    @Description("Проверка получения кода 200 и Успешного изменения Имени авторизованного пользователя")
    public void updateUserNameDataTest() {
        UserUpdate updateData = new UserUpdate();
        updateData.setEmail(newMail);
        updateData.setName(newName);

        Response createResponse = userSteps.updateUser(userToken, updateData);
        assertThat(createResponse.statusCode(), equalTo(SC_OK));
        assertThat(createResponse.path("user.name"), equalTo(newName));
    }

    @Test
    @DisplayName("Пароль пользователя изменен успешно")
    @Description("Проверка получения кода 200 и Успешного изменения паролья авторизованного пользователя")
    public void updateUserPassDataTest() {
        UserUpdate updateData = new UserUpdate();
        updateData.setEmail(newMail);
        updateData.setPassword(newPass);
        Response createResponse = userSteps.updateUser(userToken, updateData);
        assertThat(createResponse.statusCode(), equalTo(SC_OK));
    }

    @Test
    @DisplayName("Для изменения Логина требуется авторизация ошибка 401")
    @Description("Проверка получения ошибка 401 при попытке изменить Логина пользователя без авторизации")
    public void updateUserMailDataErrorTest() {
        UserUpdate updateData = new UserUpdate();
        updateData.setEmail(newMail);

        Response createResponse = userSteps.updateUser("", updateData);
        assertThat(createResponse.statusCode(), equalTo(SC_UNAUTHORIZED));
        assertThat(createResponse.path("message"), equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Для изменения Имени требуется авторизация ошибка 401")
    @Description("Проверка получения ошибка 401 при попытке изменить Именя пользователя без авторизации")
    public void updateUserNameDataErrorTest() {
        UserUpdate updateData = new UserUpdate();
        updateData.setEmail(newMail);
        updateData.setName(newName);

        Response createResponse = userSteps.updateUser("", updateData);
        assertThat(createResponse.statusCode(), equalTo(SC_UNAUTHORIZED));
        assertThat(createResponse.path("message"), equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Для изменения пароля требуется авторизация ошибка 401")
    @Description("Проверка получения ошибка 401 при попытке изменить пароля пользователя без авторизации")
    public void updateUserPassDataErrorTest() {
        UserUpdate updateData = new UserUpdate();
        updateData.setEmail(newMail);
        updateData.setPassword(newPass);
        Response createResponse = userSteps.updateUser("", updateData);
        assertThat(createResponse.statusCode(), equalTo(SC_UNAUTHORIZED));
        assertThat(createResponse.path("message"), equalTo("You should be authorised"));
    }
}
