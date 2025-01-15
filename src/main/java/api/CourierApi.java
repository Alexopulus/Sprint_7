package api;

import data.CourierData;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierApi {
    public Response createCourier(CourierData courier){
        return given()
                .contentType("application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier");
    };

    public Response loginCourier(CourierData courier) {

        return given()
                .contentType("application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
    }

    public Response deleteCourier(int courierId){
        return given()
                .contentType("application/json")
                .pathParam("id", courierId)
                .when()
                .delete("/api/v1/courier/{id}");
    };
}
