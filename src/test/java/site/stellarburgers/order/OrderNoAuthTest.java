package site.stellarburgers.order;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import site.stellarburgers.data.Data;
import site.stellarburgers.user.UserSteps;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Тесты заказов без авторизации пользователя")
public class OrderNoAuthTest {
    private UserSteps userSteps;
    OrderSteps orderSteps = new OrderSteps();

    @Before
    public void setUp() {
        RestAssured.baseURI = Data.BURGERS_URL;
        userSteps = new UserSteps();
    }

    @Test
    @DisplayName("Создание заказа без авторизации пользователем")
    public void createNewOrderWhithoutAuthTest() {
        createNewOrderWhithoutAuth();
    }

    @Test
    @DisplayName("Создан заказа без ингридиентов")
    public void createNewOrderWhithoutIngridientsTest() {
        createNewOrderWhithoutIngridients();
    }

    @Test
    @DisplayName("Получение ошибки 500 при невалидном хеше ингредиента")
    public void createNewOrderWhithIngridientHashTest() {
        createNewOrderWhithIngridientHash();
    }

    @Test
    @DisplayName("список заказов не получен, Ошибка 401 Пользователь не авторизован")
    public void getNoAuthUserOrdersTest() {
        getNoAuthUserOrders();
    }


    @Step("Создать новый заказа")
    private void createNewOrderWhithoutAuth() {
        String[] ingridients = {Data.INGRIDIENT_BUN, Data.INGRIDIENT_HOT_SAUCE, Data.INGRIDIENT_INVINCIBEL_MEAT, Data.INGRIDIENT_CHEES, Data.INGRIDIENT_BUN};
        Response createResponse = orderSteps.createNewOrder(ingridients, "");
        assertThat(createResponse.statusCode(), equalTo(SC_OK));
        assertThat(createResponse.path("order.number"), notNullValue());
    }

    @Step("Создать заказа без ингридиентов")
    private void createNewOrderWhithoutIngridients() {
        String[] ingridients = {};
        Response createResponse = orderSteps.createNewOrder(ingridients, "");
        assertThat(createResponse.statusCode(), equalTo(SC_BAD_REQUEST));
        assertThat(createResponse.path("message"), equalTo("Ingredient ids must be provided"));
    }


    @Step("Создать заказа c невалидным хешем ингредиента")
    private void createNewOrderWhithIngridientHash() {
        String[] ingridients = {Data.NOT_VALID_INGRIDIENT};
        Response createResponse = orderSteps.createNewOrder(ingridients, "");
        assertThat(createResponse.statusCode(), equalTo(SC_INTERNAL_SERVER_ERROR));
    }

    @Step("Получить заказы пользователя")
    private void getNoAuthUserOrders() {
        Response createResponse = orderSteps.getUserOrder("");
        assertThat(createResponse.statusCode(), equalTo(SC_UNAUTHORIZED));
        assertThat(createResponse.path("success"), equalTo(false));
        assertThat(createResponse.path("message"), equalTo("You should be authorised"));

    }

}
