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
        int orderId = this.storePayload.getId();  // Ensure this retrieves a valid order ID

        Response response = StoreEndPoints.readOrderByorderID(orderId);
        response.then().log().all();
        
        Assert.assertEquals(response.getStatusCode(), 200);
    }

	@Test(priority=4)
	public void deleteOrderById()
	{
		logger.info("**********   Deleting User  ***************");
		
		int orderId = this.storePayload.getId();  // Ensure this retrieves a valid order ID

        Response response = StoreEndPoints.deleteOrderById(orderId);
        response.then().log().all();
        
        Assert.assertEquals(response.getStatusCode(), 404);
		
		logger.info("********** User deleted ***************");
	}
}
