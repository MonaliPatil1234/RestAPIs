package RestAssuredDemo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.testng.Assert;
import files.ReusableMtds;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DynamicJson {
	@Test(dataProvider="booksData")
	public void addBook(String isbn, String aisle)
	{
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().header("Content-Type", "application/json")
				.body(payload.AddBook(isbn,aisle)).when().post("Library/Addbook.php")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();
		JsonPath js = ReusableMtds.rawToJson(response);
		String Id = js.getString("ID");
		System.out.println("Parsed Id is "+Id);	
				}
	@DataProvider(name="booksData")
	public Object[][] getData()
	{
		return new Object[][] {
			{"qaz","121"},
			{"wsx","234"},
			{"dfe","541"}
			};
	}
	
}
