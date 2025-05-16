package site.stellarburgers.user;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import site.stellarburgers.data.Data;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


@DisplayName("Тесты без авторизации пользователя")
public class UserNoAuthTest {
    private UserSteps userSteps;

    @Before
    public void setUp() {
        RestAssured.baseURI = Data.BURGERS_URL;
        userSteps = new UserSteps();
    }

    @Test
    @DisplayName("Для изменения Логина требуется авторизация ошибка 401")
    public void updateUserMailDataErrorTest() {
        noAuthUpdateMailData();
    }

    @Test
    @DisplayName("Для изменения Имени требуется авторизация ошибка 401")
    public void updateUserNameDataErrorTest() {
        noAuthUpdateNameData();
    }

    @Test
    @DisplayName("Для изменения пароля требуется авторизация ошибка 401")
    public void updateUserPassDataErrorTest() {
        noAuthUpdatePassData();
    }


    @Test
    @DisplayName("Ошибка 401 при авторизации с неверным паролем")
    public void loginWithWrongPasswordTest() {
        UserLogin wrongPassword = new UserLogin(Data.EMAIL, "54321");
        Response response = userSteps.loginUser(wrongPassword);

        assertThat(response.statusCode(), equalTo(SC_UNAUTHORIZED));
        assertThat(response.path("message"), equalTo("email or password are incorrect"));
    }


    @Test
    @DisplayName("Ошибка 401 при авторизации с неверным логином")
    public void loginWithWrongLoginTest() {
        UserLogin wrongLogin = new UserLogin("Masha", Data.PASSWORD);
        Response response = userSteps.loginUser(wrongLogin);

        assertThat(response.statusCode(), equalTo(SC_UNAUTHORIZED));
        assertThat(response.path("message"), equalTo("email or password are incorrect"));
    }


    @Test
    @DisplayName("Ошибка 403 при создании пользователя без пароля")
    public void NoPassUserTest() {
        createNoPassUser();
    }

    @Test
    @DisplayName("Ошибка 403 при создании пользователя без емэйла")
    public void NoEmailUserTest() {
        createNoEmailUser();
    }

    @Test
    @DisplayName("Ошибка 403 при создании пользователя без Имени")
    public void NoNameUserTest() {
        createNoNameUser();
    }


    @Step("Изменить емэйл не авторизованного пользователя")
    private void noAuthUpdateMailData() {
        UserUpdate updateData = new UserUpdate();
        updateData.setEmail("kocha@ya.ru");

        Response createResponse = userSteps.updateUser("", updateData);
        assertThat(createResponse.statusCode(), equalTo(SC_UNAUTHORIZED));
        assertThat(createResponse.path("message"), equalTo("You should be authorised"));
    }


    @Step("Изменить Имя не авторизованного пользователя")
    private void noAuthUpdateNameData() {
        UserUpdate updateData = new UserUpdate();
        updateData.setEmail("kocha@ya.ru");
        updateData.setName("kovkv");

        Response createResponse = userSteps.updateUser("", updateData);
        assertThat(createResponse.statusCode(), equalTo(SC_UNAUTHORIZED));
        assertThat(createResponse.path("message"), equalTo("You should be authorised"));
    }


    @Step("Изменить пароль не авторизованного пользователя")
    private void noAuthUpdatePassData() {
        UserUpdate updateData = new UserUpdate();
        updateData.setEmail("kocha@ya.ru");
        updateData.setPassword("234323");
        Response createResponse = userSteps.updateUser("", updateData);
        assertThat(createResponse.statusCode(), equalTo(SC_UNAUTHORIZED));
        assertThat(createResponse.path("message"), equalTo("You should be authorised"));
    }


    @Step("Создать нового пользователя без пароля")
    private void createNoPassUser() {
        CreateNewUser user = new CreateNewUser(Data.EMAIL, null, Data.NAME);
        Response createResponse = userSteps.createNewUser(user);
        assertThat(createResponse.statusCode(), equalTo(SC_FORBIDDEN));
        assertThat(createResponse.path("message"), equalTo("Email, password and name are required fields"));

    }

    @Step("Создать нового пользователя без емеэла")
    private void createNoEmailUser() {
        CreateNewUser user = new CreateNewUser(null, Data.PASSWORD, Data.NAME);
        Response createResponse = userSteps.createNewUser(user);
        assertThat(createResponse.statusCode(), equalTo(SC_FORBIDDEN));
        assertThat(createResponse.path("message"), equalTo("Email, password and name are required fields"));
    }


    @Step("Создать нового пользователя без Имени")
    private void createNoNameUser() {
        CreateNewUser user = new CreateNewUser(Data.EMAIL, Data.PASSWORD, null);
        Response createResponse = userSteps.createNewUser(user);
        assertThat(createResponse.statusCode(), equalTo(SC_FORBIDDEN));
        assertThat(createResponse.path("message"), equalTo("Email, password and name are required fields"));
    }
}
