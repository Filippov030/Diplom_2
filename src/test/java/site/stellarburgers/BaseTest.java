package site.stellarburgers;

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


    @Before
    public void setUp() {
        RestAssured.baseURI = Data.BURGERS_URL;
        userSteps = new UserSteps();
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


    @Step("Создать нового пользователя")
    private void testUser() {
        CreateNewUser user = new CreateNewUser(Data.EMAIL, Data.PASSWORD, Data.NAME);
        Response createResponse = userSteps.createNewUser(user);
        assertThat(createResponse.statusCode(), equalTo(SC_OK));

        UserLogin loginCreds = new UserLogin(Data.EMAIL, Data.PASSWORD);
        Response loginResponse = userSteps.loginUser(loginCreds);
        assertThat(loginResponse.statusCode(), equalTo(SC_OK));

        userToken = loginResponse.path("accessToken");
        assertThat(userToken, notNullValue());
    }
}
