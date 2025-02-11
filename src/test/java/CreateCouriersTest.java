import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class CreateCouriersTest {
    protected CourierClient courierClient;
    protected int courierId;
    protected Couriers couriers;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Создание курьера")
    public void createCourier() {
        couriers = CourierGenerator.generatorCoOne();
        ValidatableResponse response = courierClient.create(couriers);
        int statusCode = response.extract().statusCode();
        assertEquals("Статус код не 201", SC_CREATED, statusCode);
        boolean isCreated = response.extract().path("ok");
        assertTrue("Курьер не создан", isCreated);
        ValidatableResponse loginResponse = courierClient.login(couriers);
        courierId = loginResponse.extract().path("id");
    }

    @Test
    @DisplayName("Создание курьера с повторяющимся логином")
    public void createSameCourier() {
        couriers = CourierGenerator.generatorCoOne();
        ValidatableResponse response1 = courierClient.create(couriers);
        ValidatableResponse response2 = courierClient.create(couriers);
        int statusCode = response2.extract().statusCode();
        assertEquals("Дубликат создан", SC_CONFLICT, statusCode);
    }

    @Test
    @DisplayName("Создание курьера без логина")
    public void createInvalidCourier() {
        couriers = CourierGenerator.generatorCoTwo();
        ValidatableResponse response = courierClient.create(couriers);
        int statusCode = response.extract().statusCode();
        assertEquals("Курьер создан без обязательного поля", SC_BAD_REQUEST, statusCode);
    }

    @After
    public void deleteCourierTest() {
        courierClient.delete(courierId);
    }
}