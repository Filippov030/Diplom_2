package site.stellarburgers.order;

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

@DisplayName("Тесты на получение заказа")
public class GetOrdersTest extends BaseTest {
    OrderSteps orderSteps = new OrderSteps();


    @Test
    @DisplayName("Получен список заказов пользователя")
    @Description("Проверка получения кода 200 и списка заказов авторизованным пользователем")
    public void getUserOrdersTest() {
        Response createResponse = orderSteps.getUserOrder(userToken);
        assertThat(createResponse.statusCode(), equalTo(SC_OK));
        assertThat(createResponse.path("success"), equalTo(true));
        assertThat(createResponse.path("orders"), notNullValue());
    }


    @Test
    @DisplayName("список заказов не получен, Ошибка 401 Пользователь не авторизован")
    @Description("Проверка получения ошибки 401 при попытке получения списка заказов неавторизованным пользователем")
    public void getNoAuthUserOrdersTest() {
        Response createResponse = orderSteps.getUserOrder("");
        assertThat(createResponse.statusCode(), equalTo(SC_UNAUTHORIZED));
        assertThat(createResponse.path("success"), equalTo(false));
        assertThat(createResponse.path("message"), equalTo("You should be authorised"));
    }
}
