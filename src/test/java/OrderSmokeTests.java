import controller.OrderController;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import models.Order;
import org.testng.Assert;
import org.testng.annotations.Test;
import static utils.Constants.*;
import org.testng.annotations.Listeners;
import io.qameta.allure.testng.AllureTestNg;

@Listeners({AllureTestNg.class})
public class OrderSmokeTests extends BaseTest {

    OrderController orderController = new OrderController();

    @Test
    @Description("Add order test with default order")
    public void createOrderTest() {
        Response response = orderController.addDefaultOrder();
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(), SUCCESS_STATUS_CODE);
    }

    @Test
    @Description("Get existing order test")
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
    @Description("Get non existing order test")
    public void getNonExistingOrderTest() {
        Response getResponse = orderController.findOrder(NON_EXIST_ID_INT);
        getResponse.prettyPrint();
        Assert.assertEquals(getResponse.statusCode(), NOT_FOUND_STATUS_CODE);
    }

    @Test
    @Description("Delete order by ID test")
    public void deleteOrderByIdTest() {
        Response addResponse = orderController.addDefaultOrder();
        addResponse.prettyPrint();
        Order addedOrder = addResponse.as(Order.class);
        Response deleteResponse = orderController.deleteOrder(addedOrder.getId());
        deleteResponse.prettyPrint();
        Assert.assertEquals(deleteResponse.statusCode(), SUCCESS_STATUS_CODE);
    }
}