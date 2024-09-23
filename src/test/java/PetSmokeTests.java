import controller.PetController;
import io.restassured.response.Response;
import models.Pet;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static utils.Constants.*;
import static utils.FileConfig.getPathToResourceFile;

public class PetSmokeTests extends BaseTest {

    private static final String PET_ENDPOINT = BASE_URL + "/pet";
    private static final String NON_EXIST_PET_ENDPOINT = PET_ENDPOINT + "/" + NON_EXIST_ID;

    PetController petController = new PetController();


    @Test
    public void createPetTestGena() {
        Response response = petController.addDefaultPet();
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(), SUCCESS_STATUS_CODE);
    }

    @Test
    public void getExistingPetTestGena() {
        Response addResponse = petController.addDefaultPet();
        Pet addedPet = addResponse.as(Pet.class);

        Response getResponse = petController.findPet(addedPet.getId());
        Pet getPet = getResponse.as(Pet.class);

        getResponse.prettyPrint();
        Assert.assertEquals(getResponse.statusCode(), SUCCESS_STATUS_CODE);
        Assert.assertEquals(addedPet, getPet);
    }

    @Test
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
    public void deleteExistingPetTest() {
        Response addResponse = petController.addDefaultPet();
        addResponse.prettyPrint();
        Pet addedPet = addResponse.as(Pet.class);
        Response deleteResponse = petController.deletePet(addedPet.getId());
        deleteResponse.prettyPrint();
        Assert.assertEquals(deleteResponse.statusCode(), SUCCESS_STATUS_CODE);
    }

    @Test
    public void updateExistingPetTest() {
        Response addResponse = petController.addDefaultPet();
        addResponse.prettyPrint();

        Response updatedPetResponse = petController.updatePet();
        Pet updatedPet = updatedPetResponse.as(Pet.class);
        updatedPetResponse.prettyPrint();

        Assert.assertEquals(updatedPetResponse.statusCode(), SUCCESS_STATUS_CODE);
        Assert.assertEquals(UPDATED_PET, updatedPet);
    }

    @Test
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
    public void updatePetUploadFileTest() {
        Response addResponse = petController.addDefaultPet();
        Pet addedPet = addResponse.as(Pet.class);
        addResponse.prettyPrint();

        Response updatedPetResponse = petController.updatePetUploadImage(addedPet.getId(), getPathToResourceFile("cat.jpeg"));
        updatedPetResponse.prettyPrint();

        Response getResponse = petController.findPet(addedPet.getId());
        Pet updatedPet = getResponse.as(Pet.class);
        getResponse.prettyPrint();

        Assert.assertEquals(updatedPetResponse.statusCode(), SUCCESS_STATUS_CODE);
    }


}