package site.stellarburgers;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import site.stellarburgers.data.Data;
import site.stellarburgers.user.CreateNewUser;
import site.stellarburgers.user.UserLogin;
import site.stellarburgers.user.UserSteps;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Тесты с авторизацией пользователя")
public abstract class BaseTest {
    protected UserSteps userSteps;
    protected String userToken;
    protected String userEmail;
    protected String userPassword;
    protected String userName;
    protected Faker faker;
    protected String newMail;
    protected String newPass;
    protected String newName;


    @Before
    public void setUp() {
        RestAssured.baseURI = Data.BURGERS_URL;
        faker = new Faker();
        userSteps = new UserSteps();
        generateTestUserData();
        testUser();
    }


    @After
    public void tearDown() {
        deleteTestUser();
    }

    @Step("Удалить пользователя")
    private void deleteTestUser() {
        if (userToken != null) {
            userSteps.deleteUser(userToken);
        }
    }

    @Step("Генерация данных тестового пользователя")
    private void generateTestUserData() {
        userEmail = faker.internet().emailAddress();
        userPassword = faker.internet().password(8, 12, true, true, true);
        userName = faker.name().fullName();
    }

    @Step("Генерация данных для обновления пользователя")
    protected void generateUpdateData() {
        newMail = faker.internet().emailAddress();
        newPass = faker.internet().password(8, 16, true, true, true);
        newName = faker.name().fullName();
    }

    @Step("Создать нового пользователя")
    private void testUser() {

        CreateNewUser user = new CreateNewUser(userEmail, userPassword, userName);
        Response createResponse = userSteps.createNewUser(user);
        assertThat(createResponse.statusCode(), equalTo(SC_OK));

        UserLogin loginCreds = new UserLogin(userEmail, userPassword);
        Response loginResponse = userSteps.loginUser(loginCreds);
        assertThat(loginResponse.statusCode(), equalTo(SC_OK));

        userToken = loginResponse.path("accessToken");
        assertThat(userToken, notNullValue());
    }
}
