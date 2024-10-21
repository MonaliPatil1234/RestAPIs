package Demo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import PojoDemo.AddPlace;
import PojoDemo.Location;
import io.restassured.RestAssured;

public class serializeTest {
	
	public static void main(String[] args) throws IOException {
		// validate if AddPlace API is working as expected
		// GIVEN - ALL INPUT DETAILS
		// WHEN - SUBMIT THE API, resource, http method
		// THEN - VALIDATE THE RESPONSE
		System.out.println("************************Add Place API***************************");
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		AddPlace ap = new AddPlace();
		
		ap.setAccuracy(50);
		ap.setAddress("29, side layout, cohen 09");
		ap.setLanguage("French-IN");
		ap.setName("Frontline house");
		ap.setPhone_number("(+91) 983 893 3937");
		ap.setWebsite("http://google.com");
		List<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		ap.setTypes(myList);
		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		ap.setLocation(l);
		String response = given().queryParam("key", "qaclick123").header("Content-Type",
				"application/json")
				.body(ap)
				.when()
				.post("maps/api/place/add/json")
				.then().log().all().assertThat()
				.statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract()
				.response().asString();
		System.out.println(response);

}
}