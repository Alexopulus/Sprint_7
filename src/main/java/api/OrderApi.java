package api;

import data.OrderData;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderApi extends RestApi {

    public static final String CREATE_ORDER_URI = "/api/v1/orders";
    public static final String CANCEL_ORDER_URI = "/api/v1/orders/cancel";
    public static final String GET_LIST_ORDER_URI = "/api/v1/orders";

    @Step("Создание заказа")
    public Response createOrder(OrderData order){
        return given()
                .spec(requestSpecification())
                .and()
                .body(order)
                .when()
                .post(CREATE_ORDER_URI);
    };

    @Step("Удаление заказа после теста")
    public Response cancelOrder(Integer track){
        return given()
                .spec(requestSpecification())
                .and()
                .body(track)
                .when()
                .put(CANCEL_ORDER_URI);
    };

    @Step("Получение списка заказов")
    public Response getOrderList(){
        return given()
                .spec(requestSpecification())
                .and()
                .when()
                .get(GET_LIST_ORDER_URI);
    };


}
