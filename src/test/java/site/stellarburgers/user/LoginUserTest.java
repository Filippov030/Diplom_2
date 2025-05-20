package site.stellarburgers.user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import site.stellarburgers.BaseTest;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Тесты на авторизацию пользователя")
public class LoginUserTest extends BaseTest {


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


}
