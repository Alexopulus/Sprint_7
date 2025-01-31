package tests;

import api.OrderApi;
import com.google.gson.Gson;
import data.OrderData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class CreateOrderTests {
    private OrderApi orderApi;
    private final OrderData orderData;
    private int trackNumber;

    public CreateOrderTests(OrderData orderData){
        this.orderData = orderData;
    }

    // Параметры, которые будут использоваться для тестов
    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][]{
                {new OrderData("Алексей", "Алексеев", "Улица", "Горьковская",
                        "89092835836", 25, "2023-10-11", "Комментарий", List.of("BLACK"))},
                {new OrderData("Иван", "Иванов", "Городская", "Пушкинская",
                        "89092835837", 30, "2023-10-12", "Комментарий", List.of("GREY"))},
                {new OrderData("Петр", "Петров", "Новая", "Лермонтовская",
                        "89092835838", 15, "2023-10-10", "Комментарий", List.of("BLACK", "GRAY"))},
                {new OrderData("Сергей", "Сергеев", "Старая", "Тихорецкая",
                        "89092835839", 20, "2023-10-15", "Комментарий", List.of())},
        };
    }

    @Before
    public void setup() {
        orderApi = new OrderApi();
    }


    @Test
    @DisplayName("Успешное создание заказа")
    @Description("Проверяем успешное создание заказа с 4 наборами различных параметров")
    public void testCreateOrder() {
        Gson gson = new Gson();
        System.out.println("Sending request with order data: " + gson.toJson(orderData)); //оставил для логирования того, что я отправляю. И для проверки, что параметризация работает

        Response response = orderApi.createOrder(orderData);
        System.out.println("Response: " + response.asString());

        trackNumber = response.jsonPath().getInt("track");

        // Проверяем, что код ответа 201 и в ответе есть поле track
        response.then()
                .statusCode(201)
                .body("track", is(notNullValue())); // Проверяем, что track не равен null

        System.out.println(orderData);
    }

    @After
    public void deleteOrder() {
        if (trackNumber > 0) {
            orderApi.cancelOrder(trackNumber);
        }
    }
}