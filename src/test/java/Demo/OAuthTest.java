package Demo;

import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import PojoDemo.Api;
import PojoDemo.GetCourses;
import PojoDemo.webAutomation;

public class OAuthTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// CREATE ACCESS TOKEN/ CLIENT CREDENTIAL FOR GETTING FURTHER DETAILS
		String response = given()
				.formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParams("grant_type", "client_credentials")
				.formParams("scope", "trust").log().all().when()
				.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String accesstkn = js.get("access_token");
		System.out.println(accesstkn);
		// GET FURTHER DETAILS WITH ACCESS TOKEN
		GetCourses gc = given().queryParam("access_token", accesstkn).log().all()
				.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourses.class);

		String linkedin = gc.getLinkedIn();
		System.out.println(linkedin);
		System.out.println(gc.getInstructor());

		List<Api> apicourses = gc.getCourses().getApi();

		for (int i = 0; i < apicourses.size(); i++) {
			System.out.println(apicourses.get(i).getCourseTitle());
			if (apicourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(gc.getCourses().getApi().get(i).getCourseTitle());
				System.out.println(gc.getCourses().getApi().get(i).getPrice());
				break;
			}

		}
		String[] waCourseTitles = { "Selenium Webdriver Java", "Cypress", "Protractor" };
		ArrayList<String> a = new ArrayList<String>();
		List<webAutomation> wacourses = gc.getCourses().getwebAutomation();
		for (int i = 0; i < wacourses.size(); i++) {
			a.add(wacourses.get(i).getCourseTitle());
			System.out.println(wacourses.get(i).getCourseTitle());
		}
		List<String> expectedCourseTitles = new ArrayList<String>(Arrays.asList(waCourseTitles));

		Assert.assertTrue(a.equals(expectedCourseTitles));

	}

}
