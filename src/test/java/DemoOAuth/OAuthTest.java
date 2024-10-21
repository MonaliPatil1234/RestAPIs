package DemoOAuth;

import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class OAuthTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//CREATE ACCESS TOKEN/ CLIENT CREDENTIAL FOR GETTING FURTHER DETAILS
		String response  = 
				given().formParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com").
				formParams("client_secret","erZOWM9g3UtwNRj340YYaK_W").
				formParams("grant_type","client_credentials").
				formParams("scope","trust").
				log().all().when().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String accesstkn = js.get("access_token");
		System.out.println(accesstkn);
			//GET FURTHER DETAILS WITH ACCESS TOKEN
		String details = given().queryParam("access_token",accesstkn).log().all()
		.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").asString();
		
		js = new JsonPath(details);
		System.out.println(details);

	}

}
