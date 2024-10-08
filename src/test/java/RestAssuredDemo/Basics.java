package RestAssuredDemo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.ReusableMtds;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Basics {
	public static void main(String[] args) throws IOException {
		// validate if AddPlace API is working as expected
		// GIVEN - ALL INPUT DETAILS
		// WHEN - SUBMIT THE API, resource, http method
		// THEN - VALIDATE THE RESPONSE
		System.out.println("************************Add Place API***************************");
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().queryParam("key", "qaclick123").header("Content-Type",
				"application/json")//passing body from addplace.json file using String & bytes
				.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\madhu\\eclipse-workspace\\RestAssuredDemo\\src\\test\\java\\files\\addplace.json")))).when().post("maps/api/place/add/json").then().log().all().assertThat()
				.statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract()
				.response().asString();
		System.out.println(response);
		JsonPath js = ReusableMtds.rawToJson(response);
		String placeId = js.getString("place_id");
		System.out.println(placeId);

		// UPDATE PLACE
		System.out.println("************************UPDATE Place API***************************");
		String newAdrs = "72 winter walk, Africa";
		given()
		.log()
		.all()
		.queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n" + "\"place_id\": \"" + placeId + "\",\r\n" + "\"address\": \""
				+ "" + newAdrs + "\",\r\n"
						+ "\"key\": \"qaclick123\"\r\n" + "}")
		.when()
		.put("maps/api/place/update/json")
		.then()
		.assertThat()
		.log()
		.all()
		.statusCode(200)
		.body("msg", equalTo("Address successfully updated"));

		// GET PLACE API
		System.out.println("************************GET Place API***************************");
		String response1 = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
				.when()
				.get("maps/api/place/get/json").
				then().log().all().assertThat().statusCode(200).extract().response()
				.asString();
		System.out.println(response1);
		js = ReusableMtds.rawToJson(response1);
		String adrs = js.getString("address");
		System.out.println(adrs);
		Assert.assertEquals(adrs, newAdrs);

	}

}
