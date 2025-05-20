package site.stellarburgers.order;

import io.qameta.allure.Description;
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
    @Description("Проверка получения кода 200 и номера заказа при создании заказа не авторизованным пользователем")
    public void createNewOrderWhithoutAuthTest() {
        String[] ingridients = {Data.INGRIDIENT_BUN, Data.INGRIDIENT_HOT_SAUCE, Data.INGRIDIENT_INVINCIBEL_MEAT, Data.INGRIDIENT_CHEES, Data.INGRIDIENT_BUN};
        Response createResponse = orderSteps.createNewOrder(ingridients, "");
        assertThat(createResponse.statusCode(), equalTo(SC_OK));
        assertThat(createResponse.path("order.number"), notNullValue());
    }

    @Test
    @DisplayName("Создан заказа без ингридиентов")
    @Description("Проверка получения ошибки 400 при попытке создания заказа без ингридиентов")
    public void createNewOrderWhithoutIngridientsTest() {
        String[] ingridients = {};
        Response createResponse = orderSteps.createNewOrder(ingridients, "");
        assertThat(createResponse.statusCode(), equalTo(SC_BAD_REQUEST));
        assertThat(createResponse.path("message"), equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Получение ошибки 500 при невалидном хеше ингредиента")
    @Description("Проверка получения ошибки 500 при попытке создания заказа с невалидном хешем ингредиента")
    public void createNewOrderWhithIngridientHashTest() {
        String[] ingridients = {Data.NOT_VALID_INGRIDIENT};
        Response createResponse = orderSteps.createNewOrder(ingridients, "");
        assertThat(createResponse.statusCode(), equalTo(SC_INTERNAL_SERVER_ERROR));
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
