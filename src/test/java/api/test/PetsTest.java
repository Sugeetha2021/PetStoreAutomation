package api.test;

import java.io.File; 
import java.util.Collections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import api.endpoints.PetsEndPoints;
import api.endpoints.StoreEndPoints;
import api.payload.Category;
import api.payload.Pets;
import api.payload.Tag;
import io.restassured.response.Response;

public class PetsTest {

    Faker faker;
    Pets petsPayload;
    public Logger logger;
	private int id; // For logs

    @BeforeClass
    public void setup() {
        faker = new Faker();
        petsPayload = new Pets();

        // Creating test data using Faker
        Category category = new Category();
        category.setId(faker.number().numberBetween(1, 100));
        category.setName(faker.animal().name());

        Tag tag = new Tag();
        tag.setId(faker.number().randomDigit());
        tag.setName(faker.color().name());

        petsPayload.setId(faker.number().numberBetween(1000, 9999));
        petsPayload.setCategory(category);
        petsPayload.setName(faker.dog().name());
        petsPayload.setPhotoUrls(Collections.singletonList(faker.internet().url()));
        petsPayload.setTags(Collections.singletonList(tag));
        petsPayload.setStatus("available");

        // Logs
        logger = LogManager.getLogger(this.getClass());
        logger.debug("Debugging.....");
    }

    @Test(priority = 1)
    public void testPostPets() {
        logger.info("********** Creating pet ***************");
        Response response = PetsEndPoints.createPets(petsPayload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("********** Pet is created ***************");
    }

    @Test(priority = 2)
    public void testPostPetsUpload() {
        logger.info("********** Uploading pet image ***************");

        // Get pet ID from payload
        int id = this.petsPayload.getId();
        System.out.println("Uploading image for Pet ID: " + id); // Debugging

        // ✅ Correct File Path (Update path if necessary)
        String filePath = "C:/Users/sugir/Downloads/dog1.jpeg";  
        File imageFile = new File(filePath);

        // ✅ Ensure file exists before uploading
        if (!imageFile.exists()) {
            throw new RuntimeException("File does not exist at path: " + filePath);
        }

        // ✅ Upload pet image
        Response response = PetsEndPoints.UploadPetImage(id, imageFile);
        response.then().log().all();

        // ✅ Assert status code
        Assert.assertEquals(response.getStatusCode(), 200, "Image upload failed!");

        logger.info("********** Pet image uploaded successfully ***************");
    }

    @Test(priority = 3)
    public void testputPets() {
        logger.info("********** updating pet ***************");
        Response response = PetsEndPoints.updatePets(petsPayload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("********** Pet updated successfully ***************");
    }
    @Test(priority=4)
	public void getpetstatus()
	{
    	  logger.info("********** Getting pet status ***************");
        
        //Response response = PetsEndPoints.getPetStauts(this.petsPayload.getStatus);
    	  Response response = PetsEndPoints.getPetStauts(petsPayload);
        response.then().log().all();
              
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("********** Pet status retrived updated successfully ***************");
    }

	@Test(priority=5)
	public void testGetpetByPetID()
	{
		  logger.info("********** Getting pet by petid ***************");
        int Id = this.petsPayload.getId();  

        Response response = PetsEndPoints.readpetbypetID(Id);
        response.then().log().all();
        
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("********** Pet details retrived updated successfully ***************");
    }
	@Test(priority=6)
	public void deleteOrderById()
	{
		logger.info("**********   Deleting pets by id  ***************");
		
		 int Id = this.petsPayload.getId();  
		 System.out.println("print get is-delete order:" + Id);

        Response response = PetsEndPoints.deletepetsById(Id);
        response.then().log().all();
        
       Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("********** Pet details deleted ***************");
	}
}



