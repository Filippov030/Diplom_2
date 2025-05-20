package site.stellarburgers.user;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.stellarburgers.data.Data;

import static io.restassured.RestAssured.given;


public class UserSteps {

    @Step("Создание нового пользователя")
    public Response createNewUser(CreateNewUser user) {
        return given()
                .header(Data.CONTENT_TYPE, Data.APPLICATION_JSON)
                .body(user)
                .when()
                .post(Data.CREATE_USER_URL)
                .then()
                .extract().response();
    }

    @Step("Авторизация пользователя")
    public Response loginUser(UserLogin userLogin) {
        return given()
                .header(Data.CONTENT_TYPE, Data.APPLICATION_JSON)
                .body(userLogin)
                .when()
                .post(Data.LOGIN_USER_URL)
                .then()
                .extract().response();
    }

    @Step("Удаление пользователя")
    public Response deleteUser(String userToken) {
        return given()
                .header(Data.CONTENT_TYPE, Data.APPLICATION_JSON)
                .header(Data.AUTHORIZATION, userToken)
                .when()
                .delete(Data.DELETE_USER_URL)
                .then()
                .extract().response();
    }

    @Step("Редактирование пользователя")
    public Response updateUser(String userToken, UserUpdate updateData) {
        return given()
                .header(Data.CONTENT_TYPE, Data.APPLICATION_JSON)
                .header(Data.AUTHORIZATION, userToken)
                .body(updateData)
                .when()
                .patch(Data.UPDATE_USER_URL)
                .then()
                .extract().response();
    }
}
