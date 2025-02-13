import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrdersClient extends Client {

    private static final String POST_AND_GET_ORDER_PATH = "/api/v1/orders";

    @Step("Create orders")
    public ValidatableResponse create(Orders orders) {
        return given()
                .spec(getBaseSpec())
                .body(orders)
                .when()
                .post(POST_AND_GET_ORDER_PATH)
                .then();
    }
    @Step("Get orders")
    public ValidatableResponse get() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(POST_AND_GET_ORDER_PATH)
                .then();
    }
}