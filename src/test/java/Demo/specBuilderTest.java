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
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class specBuilderTest {

	public static void main(String[] args) throws IOException {
		// validate if AddPlace API is working as expected
		// GIVEN - ALL INPUT DETAILS
		// WHEN - SUBMIT THE API, resource, http method
		// THEN - VALIDATE THE RESPONSE
		// Request specification builder combines the repetitive method calls and
		// reduces the line of code while calling the api methods
		
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
		
		RequestSpecification req = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123")
				.setContentType(ContentType.JSON)
				.build();
		
		
		RequestSpecification res = given()
				.spec(req)
				.body(ap);
		
		ResponseSpecification resspec = new ResponseSpecBuilder()
				.expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		Response response = res
				.when()
				.post("maps/api/place/add/json")
				.then()
				.log()
				.all()
				.spec(resspec)
				.extract()
				.response();
		
		String resp = response.asPrettyString();
		System.out.println(resp);

	}
}