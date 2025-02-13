import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;

public class ListOrderTest {

    @Test
    @DisplayName("Получение списка заказов")
    public void listOrders() {
        OrdersClient ordersClient = new OrdersClient();
        ValidatableResponse response = ordersClient.get();
        int statusCode = response.extract().statusCode();
        assertEquals("Статус код не 200", SC_OK, statusCode);
        response.assertThat().body("orders.id", notNullValue());
    }
}