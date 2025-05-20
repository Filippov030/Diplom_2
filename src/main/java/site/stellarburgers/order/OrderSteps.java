package site.stellarburgers.order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.stellarburgers.data.Data;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    @Step("Создание нового заказа")
    public Response createNewOrder(String[] ingredients, String userToken) {
        CreateNewOrder order = new CreateNewOrder(ingredients);
        return given()
                .header(Data.CONTENT_TYPE, Data.APPLICATION_JSON)
                .header(Data.AUTHORIZATION, userToken)
                .body(order)
                .when()
                .post(Data.CREATE_ORDER_URL)
                .then()
                .extract().response();
    }

    @Step("Получение заказа пользователя")
    public Response getUserOrder(String userToken) {
        return given()
                .header(Data.CONTENT_TYPE, Data.APPLICATION_JSON)
                .header(Data.AUTHORIZATION, userToken)
                .when()
                .get(Data.GET_USER_ORDER_URL)
                .then()
                .extract().response();
    }
}
