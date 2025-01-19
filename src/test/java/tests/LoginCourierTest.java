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

public class LoginCourierTest {

    private CourierApi courierApi;
    private String courierLogin;
    private String courierPassword;
    private String courierFirstName;
    private int courierId;

    @Before
    public void setup() {
        courierApi  = new CourierApi();

        courierLogin = "testovka" + UUID.randomUUID(); // Уникальный логин
        courierPassword = "password" + UUID.randomUUID(); // Уникальный пароль
        courierFirstName = "test" + UUID.randomUUID(); //Уникальное имя

    }

    @Test
    @DisplayName("Успешная авторизация курьера")
    @Description("Запрос должен возвращать код 200 и сообщение с id курьера")
    public void testLoginCourierSuccessfully() {
        CourierData successCourier = new CourierData(courierLogin, courierPassword, courierFirstName);
        courierApi.createCourier(successCourier);

        Response response = courierApi.loginCourier(successCourier);
        courierId = response.jsonPath().getInt("id");

        response.then()
                .assertThat().statusCode(200)
                .body("id", is(courierId));
    }

    @Test
    @DisplayName("Попытка авторизации курьера с несуществующим логином")
    @Description("Запрос должен возвращать код 404 и сообщение Учетная запись не найдена")
    public void testLoginCourierWithBrokenLogin() {
        CourierData unexistingCourier = new CourierData("unexistingLogin", courierPassword, courierFirstName);

        Response response = courierApi.loginCourier(unexistingCourier);

        response.then()
                .assertThat().statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Попытка авторизации курьера без пароля")
    @Description("Запрос должен возвращать код 404 и сообщение Учетная запись не найдена")
    public void testLoginCourierWithoutPassword() {
        CourierData successCourier = new CourierData(courierLogin, courierPassword, courierFirstName);
        courierApi.createCourier(successCourier);

        CourierData courierWithoutPassword = new CourierData(courierLogin, "", courierFirstName);
        Response response = courierApi.loginCourier(courierWithoutPassword);


        response.then()
                .assertThat().statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Попытка авторизации курьера без логина")
    @Description("Запрос должен возвращать код 404 и сообщение Учетная запись не найдена")
    public void testLoginCourierWithoutLogin() {
        CourierData successCourier = new CourierData(courierLogin, courierPassword, courierFirstName);
        courierApi.createCourier(successCourier);

        CourierData courierWithoutPassword = new CourierData("", courierPassword, courierFirstName);
        Response response = courierApi.loginCourier(courierWithoutPassword);


        response.then()
                .assertThat().statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }


    @After
    public void deleteCourier() {
        if (courierId > 0) {
            courierApi.deleteCourier(courierId);
        }
    }
}