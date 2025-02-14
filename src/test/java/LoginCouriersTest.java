import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;

import static org.junit.Assert.assertEquals;

public class LoginCouriersTest {
    protected CourierClient courierClient;
    protected int courierId;
    protected Couriers couriers;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Логин курьера в системе")
    public void loginCourier() {
        couriers = CourierGenerator.generatorCoOne();
        ValidatableResponse response = courierClient.create(couriers);
        ValidatableResponse loginResponse = courierClient.login(couriers);
        int statusCode = loginResponse.extract().statusCode();
        assertEquals("Статус код не 201", SC_OK, statusCode);
        courierId = loginResponse.extract().path("id");
    }

    @Test
    @DisplayName("Вход без логина")
    public void noLoginCourier() {
        couriers = CourierGenerator.generatorCoTwo();
        ValidatableResponse loginResponse = courierClient.login(couriers);
        int statusCode = loginResponse.extract().statusCode();
        assertEquals("Статус код не 400", SC_BAD_REQUEST, statusCode);
    }

    @Test
    @DisplayName("Логин с несуществующей парой логин-пароль")
    public void noLoginAndPassCourier() {
        couriers = CourierGenerator.generatorCoThree();
        ValidatableResponse loginResponse = courierClient.login(couriers);
        int statusCode = loginResponse.extract().statusCode();
        assertEquals("Статус код не 404", SC_NOT_FOUND, statusCode);
    }
}