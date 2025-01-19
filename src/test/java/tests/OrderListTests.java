package tests;

import api.OrderApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class OrderListTests {

    private OrderApi orderApi;

    @Before
    public void setup() {
        orderApi = new OrderApi();
    }

    @Test
    @DisplayName("Успешное получение списка всех заказов")
    @Description("Запрос должен возвращать код 200 и список всех заказов")
    public void testGetOrderList() {

        Response response = orderApi.getOrderList();

        //проверяю, что в списке есть все обязательные поля заказа
        response.then()
                .assertThat().statusCode(200)
                .body("orders", hasItems(

                        hasKey("id"),

                        hasKey("courierId"),

                        hasKey("firstName"),

                        hasKey("lastName"),

                        hasKey("address"),

                        hasKey("metroStation"),

                        hasKey("phone"),

                        hasKey("rentTime"),

                        hasKey("deliveryDate"),

                        hasKey("track"),

                        hasKey("status")

                ));
    }

}
