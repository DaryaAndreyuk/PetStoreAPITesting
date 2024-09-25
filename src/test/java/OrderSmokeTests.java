import controller.OrderController;
import io.restassured.response.Response;
import models.Order;
import org.testng.Assert;
import org.testng.annotations.Test;
import static utils.Constants.*;

public class OrderSmokeTests extends BaseTest {

    OrderController orderController = new OrderController();

    @Test
    public void createOrderTest() {
        Response response = orderController.addDefaultOrder();
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(), SUCCESS_STATUS_CODE);
    }

    @Test
    public void getExistingOrderTest() {
        Response addResponse = orderController.addDefaultOrder();

        addResponse.prettyPrint();
        Order addedOrder = addResponse.as(Order.class);

        Response getResponse = orderController.findOrder(addedOrder.getId());
        Order getOrder = getResponse.as(Order.class);
        getResponse.prettyPrint();

        Assert.assertEquals(getResponse.statusCode(), SUCCESS_STATUS_CODE);
        Assert.assertEquals(addedOrder, getOrder);
    }

    @Test
    public void getNonExistingOrderTest() {
        Response getResponse = orderController.findOrder(NON_EXIST_ID_INT);
        getResponse.prettyPrint();
        Assert.assertEquals(getResponse.statusCode(), NOT_FOUND_STATUS_CODE);
    }

    @Test
    public void deleteOrderByIdTest() {
        Response addResponse = orderController.addDefaultOrder();
        addResponse.prettyPrint();
        Order addedOrder = addResponse.as(Order.class);
        Response deleteResponse = orderController.deleteOrder(addedOrder.getId());
        deleteResponse.prettyPrint();
        Assert.assertEquals(deleteResponse.statusCode(), SUCCESS_STATUS_CODE);
    }
}