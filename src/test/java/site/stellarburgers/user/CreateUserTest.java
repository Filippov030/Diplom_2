package site.stellarburgers.user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import site.stellarburgers.BaseTest;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@DisplayName("Тесты на создание пользователя")
public class CreateUserTest extends BaseTest {

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
    @DisplayName("Ошибка 403 при создании пользователя без пароля")
    @Description("Проверка получения ошибка 403 при попытке решистрации пользователя без указания пароля")
    public void NoPassUserTest() {
        CreateNewUser user = new CreateNewUser(userEmail, null, userName);
        Response createResponse = userSteps.createNewUser(user);
        assertThat(createResponse.statusCode(), equalTo(SC_FORBIDDEN));
        assertThat(createResponse.path("message"), equalTo("Email, password and name are required fields"));

    }

    @Test
    @DisplayName("Ошибка 403 при создании пользователя без емэйла")
    @Description("Проверка получения ошибка 403 при попытке регистрации пользователя без указания емэйла")
    public void NoEmailUserTest() {
        CreateNewUser user = new CreateNewUser(null, userPassword, userName);
        Response createResponse = userSteps.createNewUser(user);
        assertThat(createResponse.statusCode(), equalTo(SC_FORBIDDEN));
        assertThat(createResponse.path("message"), equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Ошибка 403 при создании пользователя без Имени")
    @Description("Проверка получения ошибка 403 при попытке регистрации пользователя без указания Имени")
    public void NoNameUserTest() {
        CreateNewUser user = new CreateNewUser(userEmail, userPassword, null);
        Response createResponse = userSteps.createNewUser(user);
        assertThat(createResponse.statusCode(), equalTo(SC_FORBIDDEN));
        assertThat(createResponse.path("message"), equalTo("Email, password and name are required fields"));
    }
}
