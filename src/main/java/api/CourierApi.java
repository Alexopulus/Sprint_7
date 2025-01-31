package api;

import data.CourierData;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

public class CourierApi extends RestApi{

    public static final String CREATE_COURIER_URI = "/api/v1/courier";
    public static final String LOGIN_COURIER_URI = "/api/v1/courier/login";
    public static final String DELETE_COURIER_URI = "/api/v1/courier/{id}";

    @Step("Создание курьера")
    public Response createCourier(CourierData courier){
        return given()
                .spec(requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(CREATE_COURIER_URI);
    };

    @Step("Авторизация курьера")
    public Response loginCourier(CourierData courier) {

        return given()
                .spec(requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(LOGIN_COURIER_URI);
    }

    @Step("Удаление курьера после теста")
    public Response deleteCourier(int courierId){
        return given()
                .spec(requestSpecification())
                .and()
                .pathParam("id", courierId)
                .when()
                .delete(DELETE_COURIER_URI);
    };
}
