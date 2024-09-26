package RestAssuredDemo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.testng.Assert;

import files.ReusableMtds;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Basics {
	public static void main(String[] args) {
		// validate if AddPlace API is working as expected
		// GIVEN - ALL INPUT DETAILS
		// WHEN - SUBMIT THE API, resource, http method
		// THEN - VALIDATE THE RESPONSE
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(payload.AddPlace()).when().post("maps/api/place/add/json").then().log().all().assertThat()
				.statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract()
				.response().asString();
		System.out.println(response);
		JsonPath js = ReusableMtds.rawToJson(response);
		String placeId = js.getString("place_id");
		System.out.println(placeId);

		// UPDATE PLACE

		String newAdrs = "70 winter walk, Africa";
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

		String response1 = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId).when()
				.get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract().response()
				.asString();
		System.out.println(response1);
		js = ReusableMtds.rawToJson(response1);
		String adrs = js.getString("address");
		System.out.println(adrs);
		Assert.assertEquals(adrs, newAdrs);

	}

}
