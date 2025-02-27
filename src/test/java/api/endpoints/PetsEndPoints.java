package api.endpoints;

import static io.restassured.RestAssured.given;

import java.util.ResourceBundle;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.javafaker.File;

import api.payload.Pets;
import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PetsEndPoints {
	// method created for getting URL's from properties file
		static ResourceBundle getURL()
		{
			ResourceBundle routes= ResourceBundle.getBundle("routes"); // Load properties file  // name of the properties file
			return routes;
		}

		

		public static Response createPets(Pets Payload) {
           String postpets_url=getURL().getString("PostPet_url");
			
			
			Response response=given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(Payload)
			.when()
				.post(postpets_url);
				
			return response;
		}
		
		public static Response UploadPetImage(int id, java.io.File imageFile) {
			String poststore_url=getURL().getString("PostuploadImage_url");
			
			Response response=given()
		        .pathParam("id", id)  // Ensure id is correctly passed
		        .multiPart("file", imageFile)
		        .header("Content-Type", "multipart/form-data")
		    .when()
		      		    .post(poststore_url);
			return response;
			
			/*from routes.java class passing the url
			public static Response UploadPetImage(int id, File file) {
			    return given()
			        .pathParam("id", id)  // Ensure ID is correctly passed
			        .multiPart("file", file)
			        .header("Content-Type", "multipart/form-data")
			    .when()
			        .post(Routes.PostuploadImage_url); // Ensure this contains "/pet/{id}/uploadImage"
			}*/
		}
		public static Response updatePets(Pets Payload)
		{
			String updatepets_url=getURL().getString("UpdatePet_url");
			
			Response response=given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(Payload)
			.when()
				.put(updatepets_url);
				
			return response;
		}
		public static Response getPetStauts(Pets Payload)
		{
			String getpets_url=getURL().getString("getPetStatus_url");
			
			
			Response response=given()
					.formParam("status", Payload)
							
			.when()
				.get(getpets_url);
				
			return response;
		}
		public static Response readpetbypetID(int Id)
		{
			String getpetByid_url=getURL().getString("getPetbyID_url");
			
			
			Response response=given()
					.pathParam("Id",Id)
							
			.when()
				.get(getpetByid_url);
				
			return response;
		}
		public static Response deletepetsById(int Id)
		{
			String deletepetsByID_url=getURL().getString("deletepet_url");
			
			Response response=given()
					.pathParam("Id",Id)
			.when()
				.delete(deletepetsByID_url);
				
			return response;
		}
		}

