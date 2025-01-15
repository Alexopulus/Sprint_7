package api;

import data.CourierData;
import data.OrderData;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderApi {

    public Response createOrder(OrderData order){
        return given()
                .contentType("application/json")
                .body(order)
                .when()
                .post("/api/v1/orders");
    };

    public Response cancelOrder(Integer track){
        return given()
                .contentType("application/json")
                .body(track)
                .when()
                .put("/api/v1/orders/cancel");
    };

    public Response getOrderList(){
        return given()
                .contentType("application/json")
                .when()
                .get("/api/v1/orders");
    };


}
