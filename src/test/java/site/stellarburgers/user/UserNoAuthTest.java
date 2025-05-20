package site.stellarburgers.user;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import site.stellarburgers.BaseTest;
import site.stellarburgers.data.Data;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


@DisplayName("Тесты без авторизации пользователя")
public class UserNoAuthTest extends BaseTest {
    private UserSteps userSteps;

    @Before
    public void setUp() {
        RestAssured.baseURI = Data.BURGERS_URL;
        userSteps = new UserSteps();
    }

    @Test
    @DisplayName("Для изменения Логина требуется авторизация ошибка 401")
    @Description("Проверка получения ошибка 401 при попытке изменить Логина пользователя без авторизации")
    public void updateUserMailDataErrorTest() {
        UserUpdate updateData = new UserUpdate();
        updateData.setEmail("kocha@ya.ru");

        Response createResponse = userSteps.updateUser("", updateData);
        assertThat(createResponse.statusCode(), equalTo(SC_UNAUTHORIZED));
        assertThat(createResponse.path("message"), equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Для изменения Имени требуется авторизация ошибка 401")
    @Description("Проверка получения ошибка 401 при попытке изменить Именя пользователя без авторизации")
    public void updateUserNameDataErrorTest() {
        UserUpdate updateData = new UserUpdate();
        updateData.setEmail("kocha@ya.ru");
        updateData.setName("kovkv");

        Response createResponse = userSteps.updateUser("", updateData);
        assertThat(createResponse.statusCode(), equalTo(SC_UNAUTHORIZED));
        assertThat(createResponse.path("message"), equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Для изменения пароля требуется авторизация ошибка 401")
    @Description("Проверка получения ошибка 401 при попытке изменить пароля пользователя без авторизации")
    public void updateUserPassDataErrorTest() {
        UserUpdate updateData = new UserUpdate();
        updateData.setEmail("kocha@ya.ru");
        updateData.setPassword("234323");
        Response createResponse = userSteps.updateUser("", updateData);
        assertThat(createResponse.statusCode(), equalTo(SC_UNAUTHORIZED));
        assertThat(createResponse.path("message"), equalTo("You should be authorised"));
    }


    @Test
    @DisplayName("Ошибка 401 при авторизации с неверным паролем")
    @Description("Проверка получения ошибка 401 при попытке авторизации пользователем с неверным паролем")
    public void loginWithWrongPasswordTest() {
        UserLogin wrongPassword = new UserLogin(userEmail, "54321");
        Response response = userSteps.loginUser(wrongPassword);

        assertThat(response.statusCode(), equalTo(SC_UNAUTHORIZED));
        assertThat(response.path("message"), equalTo("email or password are incorrect"));
    }


    @Test
    @DisplayName("Ошибка 401 при авторизации с неверным логином")
    @Description("Проверка получения ошибка 401 при попытке авторизации пользователем с неверным логином")
    public void loginWithWrongLoginTest() {
        UserLogin wrongLogin = new UserLogin("Masha", userPassword);
        Response response = userSteps.loginUser(wrongLogin);

        assertThat(response.statusCode(), equalTo(SC_UNAUTHORIZED));
        assertThat(response.path("message"), equalTo("email or password are incorrect"));
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
    @Description("Проверка получения ошибка 403 при попытке решистрации пользователя без указания емэйла")
    public void NoEmailUserTest() {
        CreateNewUser user = new CreateNewUser(null, userPassword, userName);
        Response createResponse = userSteps.createNewUser(user);
        assertThat(createResponse.statusCode(), equalTo(SC_FORBIDDEN));
        assertThat(createResponse.path("message"), equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Ошибка 403 при создании пользователя без Имени")
    @Description("Проверка получения ошибка 403 при попытке решистрации пользователя без указания Имени")
    public void NoNameUserTest() {
        CreateNewUser user = new CreateNewUser(userEmail, userPassword, null);
        Response createResponse = userSteps.createNewUser(user);
        assertThat(createResponse.statusCode(), equalTo(SC_FORBIDDEN));
        assertThat(createResponse.path("message"), equalTo("Email, password and name are required fields"));
    }
}
