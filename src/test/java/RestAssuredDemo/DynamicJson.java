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
				.body(payload.AddBook("qwwe","3454")).when().post("Library/Addbook.php")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();
		JsonPath js = ReusableMtds.rawToJson(response);
		String Id = js.getString("ID");
		System.out.println("Parsed Id is "+Id);
				
				
				
				
	}
}
