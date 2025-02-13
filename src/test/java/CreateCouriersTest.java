import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class CreateCouriersTest {
    protected CourierClient courierClient;
    protected int courierId = -1; // Инициализируем безопасным значением
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
        assertEquals("Статус код не 201", SC_CREATED, response.extract().statusCode());
        assertTrue("Курьер не создан", response.extract().path("ok"));

        ValidatableResponse loginResponse = courierClient.login(couriers);
        courierId = loginResponse.extract().path("id");
        assertNotNull("Не удалось получить ID курьера", courierId);
    }

    @Test
    @DisplayName("Создание курьера с повторяющимся логином")
    public void createSameCourier() {
        couriers = CourierGenerator.generatorCoOne();
        ValidatableResponse response1 = courierClient.create(couriers);

        // Логинимся, чтобы получить ID и удалить его
        ValidatableResponse loginResponse = courierClient.login(couriers);
        courierId = loginResponse.extract().path("id");
        assertNotNull("Не удалось получить ID курьера", courierId);

        // Удаляем первого курьера, чтобы тесты не конфликтовали
        courierClient.delete(courierId);
        courierId = -1; // Сбрасываем ID, так как удалили курьера

        // Пытаемся создать такого же курьера снова
        ValidatableResponse response2 = courierClient.create(couriers);
        assertEquals("Дубликат создан", SC_CONFLICT, response2.extract().statusCode());
    }

    @Test
    @DisplayName("Создание курьера без логина")
    public void createInvalidCourier() {
        couriers = CourierGenerator.generatorCoTwo();
        ValidatableResponse response = courierClient.create(couriers);
        assertEquals("Курьер создан без обязательного поля", SC_BAD_REQUEST, response.extract().statusCode());
    }

    @After
    public void deleteCourierTest() {
        if (courierId > 0) {
            courierClient.delete(courierId);
        }
    }
}
