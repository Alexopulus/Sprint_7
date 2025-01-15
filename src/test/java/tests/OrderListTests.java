package tests;

import api.OrderApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class OrderListTests {

    private OrderApi orderApi;

    @Before
    public void setup() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";

        orderApi = new OrderApi();
    }

    @Test
    @DisplayName("Успешное получение списка всех заказов")
    @Description("Запрос должен возвращать код 200 и список всех заказов")
    public void testGetOrderList() {

        Response response = orderApi.getOrderList();

        response.then()
                .assertThat().statusCode(200)
                .body("orders", is(notNullValue()));
    }

}
