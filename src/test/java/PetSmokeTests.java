import controller.PetController;
import io.qameta.allure.Description;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import java.util.List;
import static utils.Constants.DEFAULT_PET;
import static utils.Constants.UPDATED_PET;
import static utils.FileConfig.getPathToResourceFile;

public class PetSmokeTests extends BaseTest {

    PetController petController = new PetController();

    @Test
    @Description("Create Pet Test")
    public void createPetTest() {
        petController.addDefaultPet()
                .statusCodeIs(HttpStatus.SC_OK)
                .jsonValueIs("id", String.valueOf(DEFAULT_PET.getId()))
                .jsonValueIs("name", String.valueOf(DEFAULT_PET.getName()))
                .jsonValueIs("status", DEFAULT_PET.getStatus())
                .jsonValueIs("category.id", String.valueOf(DEFAULT_PET.getCategory().getId()))
                .jsonValueIs("category.name", DEFAULT_PET.getCategory().getName())
                .jsonValueIs("photoUrls[0]", DEFAULT_PET.getPhotoUrls().getFirst())
                .jsonValueIs("tags[0].id", String.valueOf(DEFAULT_PET.getTags().getFirst().getId()))
                .jsonValueIs("tags[0].name", DEFAULT_PET.getTags().getFirst().getName());
    }

    @Test
    @Description("Get Existing Pet Test")
    public void getExistingPetTest() {

        var addResponse = petController.addDefaultPet().statusCodeIs(HttpStatus.SC_OK);
        int id = Integer.parseInt(addResponse.getJsonValue("id"));
        petController.findPet(id).statusCodeIs(HttpStatus.SC_OK)
                .jsonValueIs("id", String.valueOf(DEFAULT_PET.getId()))
                .jsonValueIs("name", String.valueOf(DEFAULT_PET.getName()))
                .jsonValueIs("status", DEFAULT_PET.getStatus())
                .jsonValueIs("category.id", String.valueOf(DEFAULT_PET.getCategory().getId()))
                .jsonValueIs("category.name", DEFAULT_PET.getCategory().getName())
                .jsonValueIs("photoUrls[0]", DEFAULT_PET.getPhotoUrls().getFirst())
                .jsonValueIs("tags[0].id", String.valueOf(DEFAULT_PET.getTags().getFirst().getId()))
                .jsonValueIs("tags[0].name", DEFAULT_PET.getTags().getFirst().getName());
    }

    @Test
    @Description("Get Pet By Status Test")
    public void getPetsByStatusTest() {
        var getResponse = petController.findByStatusPet("sold")
                .statusCodeIs(HttpStatus.SC_OK);

        List<String> petIds = getResponse.getResponse().extract().jsonPath().getList("id");

        String expectedStatus = "sold";

        for (int i = 0; i < petIds.size(); i++) {
            getResponse.jsonValueIs("[" + i + "].status", expectedStatus);
        }
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

        petController.updatePet()
                .statusCodeIs(HttpStatus.SC_OK)
                .jsonValueIs("id", String.valueOf(UPDATED_PET.getId()))
                .jsonValueIs("name", UPDATED_PET.getName())
                .jsonValueIs("status", UPDATED_PET.getStatus())
                .jsonValueIs("category.id", String.valueOf(UPDATED_PET.getCategory().getId()))
                .jsonValueIs("category.name", UPDATED_PET.getCategory().getName())
                .jsonValueIs("photoUrls[0]", UPDATED_PET.getPhotoUrls().getFirst())
                .jsonValueIs("tags[0].id", String.valueOf(UPDATED_PET.getTags().getFirst().getId()))
                .jsonValueIs("tags[0].name", UPDATED_PET.getTags().getFirst().getName());
    }


    @Test
    @Description("Update Existing Pet With Form Data Test")
    public void updateExistingPetFormDataTest() {
        long id = Long.parseLong(
                petController.addDefaultPet()
                        .statusCodeIs(HttpStatus.SC_OK)
                        .getJsonValue("id")
        );

        petController.updatePetByIdFormData(id)
                .statusCodeIs(HttpStatus.SC_OK);

        petController.findPet(id)
                .statusCodeIs(HttpStatus.SC_OK)
                .jsonValueIs("id", String.valueOf(UPDATED_PET.getId()))
                .jsonValueIs("name", UPDATED_PET.getName())
                .jsonValueIs("status", UPDATED_PET.getStatus());
    }

    @Test
    @Description("Update Existing Pet With Image File Test")
    public void updatePetUploadFileTest() {
        long id = Long.parseLong(
                petController.addDefaultPet()
                        .statusCodeIs(HttpStatus.SC_OK)
                        .getJsonValue("id")
        );

        petController.updatePetUploadImage(id, getPathToResourceFile("cat.jpeg"))
                .statusCodeIs(HttpStatus.SC_OK)
                .jsonValueContains("message", "File uploaded to ./cat.jpeg");
    }
}