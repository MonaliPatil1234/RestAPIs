package RestAssuredDemo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.testng.Assert;

import files.ReusableMtds;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;


import org.testng.annotations.Test;

public class DynamicJson {

	@Test
	public void addBook()
	{
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().header("Content-Type", "application/json")
				.accept(null)
				
				
				
				)
	}
}
