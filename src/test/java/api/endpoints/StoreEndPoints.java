package api.endpoints;

import static io.restassured.RestAssured.given;

import java.util.ResourceBundle;

import api.payload.Store;
import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class StoreEndPoints {

	// method created for getting URL's from properties file
	static ResourceBundle getURL()
	{
		ResourceBundle routes= ResourceBundle.getBundle("routes"); // Load properties file  // name of the properties file
		return routes;
	}

	public static Response RetunPetInventory()
	{
		String getstore_url=getURL().getString("getstore_url");
		
		
		Response response=given()
						
		.when()
			.get(getstore_url);
			
		return response;
	}
	public static Response PlaceOrderForPet(Store payload)
	{
		String poststore_url=getURL().getString("poststore_url");
		
		
		Response response=given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)
		.when()
			.post(poststore_url);
			
		return response;
	}
	public static Response readOrderByorderID(int Id)
	{
		String getstoreOrder_url=getURL().getString("getstoreorder_url");
		
		
		Response response=given()
				.pathParam("Id",Id)
						
		.when()
			.get(getstoreOrder_url);
			
		return response;
	}
	public static Response deleteOrderById(int Id)
	{
		String deletestoreOrder_url=getURL().getString("deletestore_url");
		
		Response response=given()
				.pathParam("Id",Id)
		.when()
			.delete(deletestoreOrder_url);
			
		return response;
	}
	
}
