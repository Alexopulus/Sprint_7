package tests;

import api.CourierApi;
import data.CourierData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.Matchers.*;

public class CreateCourierTest {

    private CourierApi courierApi;
    private String courierLogin;
    private String courierPassword;
    private String courierFirstName;
    private int courierId;

    @Before
    public void setup() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";

        courierApi  = new CourierApi();

        courierLogin = "testovka" + UUID.randomUUID(); // Уникальный логин
        courierPassword = "password" + UUID.randomUUID(); // Уникальный пароль
        courierFirstName = "test" + UUID.randomUUID(); //Уникальное имя

    }

    @Test
    @DisplayName("Успешное создание курьера")
    @Description("Успешный запрос должен возвращать код 201 и сообщение ok: true")
    public void testCreateCourierSuccessfully() {
        CourierData successCourier = new CourierData(courierLogin, courierPassword, courierFirstName);

        Response response = courierApi.createCourier(successCourier);

        response.then()
                .assertThat().statusCode(201)
                .body("ok", is(true));

        Response loginResponse = courierApi.loginCourier(new CourierData(courierLogin, courierPassword, ""));
        courierId = loginResponse.jsonPath().getInt("id");
    }

    @Test
    @DisplayName("Попытка создания курьера с отсутствующим паролем")
    @Description("Запрос должен возвращать код 400 и сообщение Недостаточно данных для создания учетной записи")
    public void testCreateCourierWithoutRequiredFields() {
        CourierData courierWithoutPassword = new CourierData(courierLogin, "", courierFirstName);

        Response response = courierApi.createCourier(courierWithoutPassword);
        response.then()
                .assertThat().statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Попытка создания курьера с пустым body")
    @Description("Запрос должен возвращать код 400 и сообщение Недостаточно данных для создания учетной записи")
    public void testCreateCourierWithEmptyBody() {
        CourierData emptyCourier = new CourierData();

        Response response = courierApi.createCourier(emptyCourier);
        response.then()
                .assertThat().statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Попытка создания курьера с уже существующим логином")
    @Description("Запрос должен возвращать код 409 и сообщение Этот логин уже используется. Попробуйте другой.")
    public void testCreateCourierWithExistingLogin() {
        CourierData courier = new CourierData(courierLogin, courierPassword, courierFirstName);

        courierApi.createCourier(courier);

        Response response = courierApi.createCourier(courier);
        response.then()
                .assertThat().statusCode(409)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));

        Response loginResponse = courierApi.loginCourier(new CourierData(courierLogin, courierPassword, ""));
        courierId = loginResponse.jsonPath().getInt("id");
    }

    @After
    public void deleteCourier() {
        if (courierId > 0) {
            courierApi.deleteCourier(courierId);
        }
    }
}