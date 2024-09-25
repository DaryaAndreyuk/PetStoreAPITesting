import controller.PetController;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import models.Pet;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;
import static utils.Constants.*;
import static utils.FileConfig.getPathToResourceFile;

public class PetSmokeTests extends BaseTest {

    PetController petController = new PetController();

    @Test
    @Description("Create Pet Test")
    public void createPetTestGena() {
        Response response = petController.addDefaultPet();
        Assert.assertEquals(response.statusCode(), SUCCESS_STATUS_CODE);
    }

    @Test
    @Description("Get Existing Pet Test")
    public void getExistingPetTestGena() {
        Response addResponse = petController.addDefaultPet();
        Pet addedPet = addResponse.as(Pet.class);

        Response getResponse = petController.findPet(addedPet.getId());
        Pet getPet = getResponse.as(Pet.class);

        Assert.assertEquals(getResponse.statusCode(), SUCCESS_STATUS_CODE);
        Assert.assertEquals(addedPet, getPet);
    }

    @Test
    @Description("Get Pet By Status Test")
    public void getPetsByStatusTest() {
        Response getResponse = petController.findByStatusPet("sold");
        List<Pet> getPetList = getResponse.jsonPath().getList("", Pet.class);

        String expectedStatus = "sold";
        for (Pet pet : getPetList) {
            Assert.assertEquals(pet.getStatus(), expectedStatus);
        }
        Assert.assertEquals(getResponse.statusCode(), SUCCESS_STATUS_CODE);
    }

    @Test
    @Description("Delete Existing Pet Test")
    public void deleteExistingPetTest() {
        Response addResponse = petController.addDefaultPet();
        Pet addedPet = addResponse.as(Pet.class);
        Response deleteResponse = petController.deletePet(addedPet.getId());
        Assert.assertEquals(deleteResponse.statusCode(), SUCCESS_STATUS_CODE);
    }

    @Test
    @Description("Update Existing Pet Test")
    public void updateExistingPetTest() {
        Response addResponse = petController.addDefaultPet();
        Response updatedPetResponse = petController.updatePet();
        Pet updatedPet = updatedPetResponse.as(Pet.class);

        Assert.assertEquals(updatedPetResponse.statusCode(), SUCCESS_STATUS_CODE);
        Assert.assertEquals(UPDATED_PET, updatedPet);
    }

    @Test
    @Description("Update Existing Pet With Form Data Test")
    public void updateExistingPetFormDataTest() {
        Response addResponse = petController.addDefaultPet();
        Pet addedPet = addResponse.as(Pet.class);

        Response updatedPetResponse = petController.updatePetByIdFormData(addedPet.getId());
        Response getResponse = petController.findPet(addedPet.getId());

        Pet updatedPet = getResponse.as(Pet.class);
        Assert.assertEquals(updatedPetResponse.statusCode(), SUCCESS_STATUS_CODE);
        Assert.assertEquals(updatedPet.getName(), UPDATED_PET.getName());
        Assert.assertEquals(updatedPet.getStatus(), UPDATED_PET.getStatus());
    }

    @Test
    @Description("Update Existing Pet With Image File Test")
    public void updatePetUploadFileTest() {
        Response addResponse = petController.addDefaultPet();
        Pet addedPet = addResponse.as(Pet.class);

        Response updatedPetResponse = petController.updatePetUploadImage(addedPet.getId(), getPathToResourceFile("cat.jpeg"));
        Response getResponse = petController.findPet(addedPet.getId());
        Pet updatedPet = getResponse.as(Pet.class);

        Assert.assertEquals(updatedPetResponse.statusCode(), SUCCESS_STATUS_CODE);
    }
}