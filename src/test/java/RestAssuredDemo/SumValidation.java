package RestAssuredDemo;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {
	@Test
	public void validateSum() {
		JsonPath js = new JsonPath(payload.CoursePrice());

		// PRINT NO OF COURSES RETURNED BY API

		int count = js.getInt("courses.size()");
		System.out.println(count);

		// PRINT PURCHASE AMOUNT
		int purchaseamnt = js.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseamnt);
		int sum = 0;
		for (int i = 0; i < count; i++) {

			int price1 = js.getInt("courses[" + i + "].price");
			int numofcopies = js.getInt("courses[" + i + "].copies");
			sum = sum + (price1 * numofcopies);

		}
		System.out.println(sum);
		Assert.assertEquals(sum, purchaseamnt);
	}

}
