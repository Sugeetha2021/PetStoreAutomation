package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.StoreEndPoints;
import api.endpoints.UserEndPoints2;
import api.payload.Store;
import api.payload.User;
import io.restassured.response.Response;

public class StoreTest {
	
	Faker faker;
	Store storePayload;
	
	public Logger logger; // for logs
	
	@BeforeClass
	public void setup()
	{
		faker=new Faker();
		storePayload=new Store();
		
		
		storePayload.setId(faker.idNumber().hashCode());
		storePayload.setPetId(faker.number().hashCode());
		storePayload.setQuantity(faker.number().hashCode());
		storePayload.setShipDate(faker.date().hashCode());
		storePayload.setStatus("placed");
		storePayload.setComplete("true");
				
		//logs
		logger= LogManager.getLogger(this.getClass());
		
		logger.debug("debugging.....");
		
	}
	


	@Test(priority=1)
	public void testGetUserByName()
	{
		
		Response response= StoreEndPoints.RetunPetInventory();
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
			
	}
	
	@Test(priority=2)
	public void testPostUser()
	{
		logger.info("********** Creating user  ***************");
		Response response=StoreEndPoints.PlaceOrderForPet(storePayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("**********User is creatged  ***************");
			
	}

	@Test(priority=3)
	public void testGetUserByOrderID()
	{
        int Id = this.storePayload.getId();  // Ensure this retrieves a valid order ID
        System.out.println("printing order id ID: " + Id);
        
        Response response = StoreEndPoints.readOrderByorderID(Id);
        response.then().log().all();
        
        //Assert.assertEquals(response.getStatusCode(), 200);
    }

	@Test(priority=4)
	public void deleteOrderById()
	{
		logger.info("**********   Store order details  ***************");
		
		int Id = this.storePayload.getId();  // Ensure this retrieves a valid order ID
		System.out.println("printing order id ID-Delete: " + Id);

        Response response = StoreEndPoints.deleteOrderById(Id);
        response.then().log().all();
        
        //Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("********** store order details deleted successfully ***************");
	}
}
