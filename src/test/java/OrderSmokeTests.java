import controller.OrderController;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.testng.AllureTestNg;
import models.Order;
import org.apache.http.HttpStatus;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static utils.Constants.*;

@Listeners({AllureTestNg.class})
public class OrderSmokeTests extends BaseTest {

    OrderController orderController = new OrderController();

    @Test
    @Owner("Darya Andreyuk")
    @Epic("Create order")
    @Feature("API: Create order")
    @Description("Add order test with default order")
    public void createOrderTest() {
        orderController.addDefaultOrder()
                .statusCodeIs(HttpStatus.SC_OK)
                .compareWithObject(DEFAULT_ORDER, Order.class);
    }

    @Test
    @Owner("Darya Andreyuk")
    @Epic("Get order")
    @Feature("API: Get order")
    @Description("Get existing order test")
    public void getExistingOrderTest() {
        var addResponse = orderController.addDefaultOrder().statusCodeIs(HttpStatus.SC_OK);
        int id = Integer.parseInt(addResponse.getJsonValue("id"));
        orderController.findOrder(id)
                .statusCodeIs(HttpStatus.SC_OK)
                .jsonValueIsNotNull("shipDate")
                .jsonValueIs("id", String.valueOf(DEFAULT_ORDER.getId()))
                .jsonValueIs("petId", String.valueOf(DEFAULT_ORDER.getPetId()))
                .jsonValueIs("quantity", String.valueOf(DEFAULT_ORDER.getQuantity()))
                .jsonValueIs("status", DEFAULT_ORDER.getStatus())
                .jsonValueIs("complete", String.valueOf(DEFAULT_ORDER.getComplete()));
    }

    @Test
    @Owner("Darya Andreyuk")
    @Epic("Find non existing order")
    @Feature("API: Get non existing order")
    @Description("Get non existing order test")
    public void getNonExistingOrderTest() {
        orderController.findOrder(NON_EXIST_ID).statusCodeIs(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    @Description("Delete order by ID test")
    public void deleteOrderByIdTest() {
        int id = Integer.parseInt(
                orderController.addDefaultOrder()
                        .statusCodeIs(HttpStatus.SC_OK)
                        .getJsonValue("id"));
        orderController.deleteOrder(id).statusCodeIs(HttpStatus.SC_OK);
    }
}