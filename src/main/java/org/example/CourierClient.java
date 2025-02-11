import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends Client {
    private static final String CREATE_COURIER = "/api/v1/courier";
    private static final String LOGIN_COURIER = "/api/v1/courier/login";

    private static final String DELETE_COURIER = "/api/v1/courier/";
    @Step("Create courier")
    public ValidatableResponse create(Couriers couriers) {
        return given()
                .spec(getBaseSpec())
                .body(couriers)
                .when()
                .post(CREATE_COURIER)
                .then();
    }

    @Step("Login courier")
    public ValidatableResponse login(Couriers couriers) {
        return given()
                .spec(getBaseSpec())
                .body(couriers)
                .when()
                .post(LOGIN_COURIER)
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse delete(int id) {
        return given()
                .spec(getBaseSpec())
                .pathParam("id", id)
                .when()
                .delete(DELETE_COURIER + "{id}")
                .then();
    }
}