import controller.PetController;
import io.qameta.allure.Description;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import static utils.Constants.*;
import static utils.FileConfig.getPathToResourceFile;

public class PetSmokeTests extends BaseTest {

    PetController petController = new PetController();

    @Test
    @Description("Create Pet Test")
    public void createPetTestGena() {
        petController.addDefaultPet().statusCodeIs(HttpStatus.SC_OK)
                .jsonValueIs("id", String.valueOf(DEFAULT_PET.getId()))
                .jsonValueIs("name", String.valueOf(DEFAULT_PET.getName()))
                .jsonValueIs("status", DEFAULT_PET.getStatus());
    }

    @Test
    @Description("Get Existing Pet Test")
    public void getExistingPetTestGena() {

        var addResponse = petController.addDefaultPet().statusCodeIs(HttpStatus.SC_OK);
        int id = Integer.parseInt(addResponse.getJsonValue("id"));
        petController.findPet(id).statusCodeIs(HttpStatus.SC_OK)
                .jsonValueIs("id", String.valueOf(DEFAULT_PET.getId()))
                .jsonValueIs("name", String.valueOf(DEFAULT_PET.getName()))
                .jsonValueIs("status", DEFAULT_PET.getStatus());
    }

    @Test
    @Description("Get Pet By Status Test")
    public void getPetsByStatusTest() {
        var getResponse = petController.findByStatusPet("sold").statusCodeIs(HttpStatus.SC_OK);

        //how to validate the list?

        /*List<Pet> getPetList = getResponse.getJsonValue().getList("", Pet.class);

        String expectedStatus = "sold";
        for (Pet pet : getPetList) {
            Assert.assertEquals(pet.getStatus(), expectedStatus);
        }
        */
    }

    @Test
    @Description("Delete Existing Pet Test")
    public void deleteExistingPetTest() {
        int id = Integer.parseInt(
                petController.addDefaultPet()
                        .statusCodeIs(HttpStatus.SC_OK)
                        .getJsonValue("id"));
        petController.deletePet(id).statusCodeIs(HttpStatus.SC_OK);
    }

    @Test
    @Description("Update Existing Pet Test")
    public void updateExistingPetTest() {
        petController.addDefaultPet().statusCodeIs(HttpStatus.SC_OK);
        petController.updatePet().statusCodeIs(HttpStatus.SC_OK)
                .jsonValueIs("id", String.valueOf(UPDATED_PET.getId()))
                .jsonValueIs("name", String.valueOf(UPDATED_PET.getName()))
                .jsonValueIs("status", UPDATED_PET.getStatus());
    }

    @Test
    @Description("Update Existing Pet With Form Data Test")
    public void updateExistingPetFormDataTest() {
        long id = Long.parseLong(
                petController.addDefaultPet()
                        .statusCodeIs(HttpStatus.SC_OK)
                        .getJsonValue("id"));
        petController.updatePetByIdFormData(id).statusCodeIs(HttpStatus.SC_OK);
        petController.findPet(id).statusCodeIs(HttpStatus.SC_OK)
                .jsonValueIs("id", String.valueOf(UPDATED_PET.getId()))
                .jsonValueIs("name", String.valueOf(UPDATED_PET.getName()))
                .jsonValueIs("status", UPDATED_PET.getStatus());
    }

    @Test
    @Description("Update Existing Pet With Image File Test")
    public void updatePetUploadFileTest() {
        long id = Long.parseLong(
                petController.addDefaultPet()
                        .statusCodeIs(HttpStatus.SC_OK)
                        .getJsonValue("id"));
        petController.updatePetUploadImage(id, getPathToResourceFile("cat.jpeg")).statusCodeIs(HttpStatus.SC_OK);
        petController.findPet(id).statusCodeIs(HttpStatus.SC_OK);
        //jsonValues???
    }
}