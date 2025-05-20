package site.stellarburgers.order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import site.stellarburgers.BaseTest;
import site.stellarburgers.data.Data;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Тесты на создание заказа")
public class CreateOrderTest extends BaseTest {
    OrderSteps orderSteps = new OrderSteps();


    @Test
    @DisplayName("Создание заказа авторизованным пользователем")
    @Description("Проверка получения кода 200 и номера заказа при создании заказа авторизованным пользователем")
    public void createNewOrderTest() {
        String[] ingridients = {Data.INGRIDIENT_BUN, Data.INGRIDIENT_HOT_SAUCE, Data.INGRIDIENT_INVINCIBEL_MEAT, Data.INGRIDIENT_CHEES, Data.INGRIDIENT_BUN};
        Response createResponse = orderSteps.createNewOrder(ingridients, userToken);
        assertThat(createResponse.statusCode(), equalTo(SC_OK));
        assertThat(createResponse.path("order.number"), notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без авторизации пользователем")
    @Description("Проверка получения кода 200 и номера заказа при создании заказа не авторизованным пользователем")
    public void createNewOrderWhithoutAuthTest() {
        String[] ingridients = {Data.INGRIDIENT_BUN, Data.INGRIDIENT_HOT_SAUCE, Data.INGRIDIENT_INVINCIBEL_MEAT, Data.INGRIDIENT_CHEES, Data.INGRIDIENT_BUN};
        Response createResponse = orderSteps.createNewOrder(ingridients, "");
        assertThat(createResponse.statusCode(), equalTo(SC_OK));
        assertThat(createResponse.path("order.number"), notNullValue());
    }


    @Test
    @DisplayName("Создание заказа без ингридиентов")
    @Description("Проверка получения ошибки 400 при попытке создания заказа без ингридиентов")
    public void createNewOrderWhithoutIngridientsTest() {
        String[] ingridients = {};
        Response createResponse = orderSteps.createNewOrder(ingridients, "");
        assertThat(createResponse.statusCode(), equalTo(SC_BAD_REQUEST));
        assertThat(createResponse.path("message"), equalTo("Ingredient ids must be provided"));
    }


    @Test
    @DisplayName("Создание заказа с невалидным хешем ингредиента")
    @Description("Проверка получения ошибки 500 при попытке создания заказа с невалидном хешем ингредиента")
    public void createNewOrderWhithIngridientHashTest() {
        String[] ingridients = {Data.NOT_VALID_INGRIDIENT};
        Response createResponse = orderSteps.createNewOrder(ingridients, "");
        assertThat(createResponse.statusCode(), equalTo(SC_INTERNAL_SERVER_ERROR));
    }
}
