import controller.OrderController;
import io.qameta.allure.Description;
import io.qameta.allure.testng.AllureTestNg;
import org.apache.http.HttpStatus;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static utils.Constants.DEFAULT_ORDER;
import static utils.Constants.NON_EXIST_ID_INT;

@Listeners({AllureTestNg.class})
public class OrderSmokeTests extends BaseTest {

    OrderController orderController = new OrderController();

    @Test
    @Description("Add order test with default order")
    public void createOrderTest() {
        orderController.addDefaultOrder()
                .statusCodeIs(HttpStatus.SC_OK)
                .jsonValueIsNotNull("shipDate")
                .jsonValueIs("id", String.valueOf(DEFAULT_ORDER.getId()))
                .jsonValueIs("petId", String.valueOf(DEFAULT_ORDER.getPetId()))
                .jsonValueIs("quantity", String.valueOf(DEFAULT_ORDER.getQuantity()))
                .jsonValueIs("status", DEFAULT_ORDER.getStatus())
                .jsonValueIs("complete", String.valueOf(DEFAULT_ORDER.getComplete()));
    }

    @Test
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
    @Description("Get non existing order test")
    public void getNonExistingOrderTest() {
        orderController.findOrder(NON_EXIST_ID_INT).statusCodeIs(HttpStatus.SC_NOT_FOUND);
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