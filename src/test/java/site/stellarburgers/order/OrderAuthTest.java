package site.stellarburgers.order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import site.stellarburgers.BaseTest;
import site.stellarburgers.data.Data;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class OrderAuthTest extends BaseTest {
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
    @DisplayName("Получен список заказов пользователя")
    @Description("Проверка получения кода 200 и списка заказов авторизованным пользователем")
    public void getUserOrdersTest() {
        Response createResponse = orderSteps.getUserOrder(userToken);
        assertThat(createResponse.statusCode(), equalTo(SC_OK));
        assertThat(createResponse.path("success"), equalTo(true));
        assertThat(createResponse.path("orders"), notNullValue());
    }
}
