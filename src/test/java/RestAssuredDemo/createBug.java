package RestAssuredDemo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;
public class createBug {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI = "https://rahushettyacademy-team.atlassian.net/";
		String response = given().header("Content-Type","application/json").header("Authorization","Basic cG1vbmFsaTYyQGdtYWlsLmNvbTpBVEFUVDN4RmZHRjAwVTREWmJkUkF2aDAzTHBxa1JEVm5yUlpBY0RSeTh5UlZ6VXlzaXdaanJDd1pEUVBXTWVEcG0wZ1FfelhGT3ZUX2JsTFVXb0lPVnlXWXFJbzFwX1lTMGxxLU12aVNZODF2RkZibG53WUZkaEE2NkVGajNWUEx5MEh3ZVl5Y2l2NkVNZ2RUSGg3aFZvb3RIMm1TYTE3eDVUdjFnS0pWd0lDUXBPdUVfWmdpQzg9MkMzRjBFMDg=").body("{\r\n"
				+ "	\"fields\": {\r\n"
				+ "		\"project\": {\r\n"
				+ "			\"key\": \"SCRUM\"\r\n"
				+ "		},\r\n"
				+ "		\"summary\": \" Unable to select radio button for options\",\r\n"
				+ "		\"issuetype\": {\r\n"
				+ "			\"name\": \"Bug\"\r\n"
				+ "		}\r\n"
				+ "	}\r\n"
				+ "}").log().all().post("rest/api/3/issue").then().log().all().assertThat().statusCode(201).extract().response().asString();
		JsonPath js = new JsonPath(response);
		String IssueId = js.getString("id");
		System.out.println("Issue Id is "+IssueId);
		
		//ADD ATTACHMENT
		
		given().pathParam("key", IssueId).
		header("X-Atlassian-Token","no-check").
		header("Authorization","Basic cG1vbmFsaTYyQGdtYWlsLmNvbTpBVEFUVDN4RmZHRjAwVTREWmJkUkF2aDAzTHBxa1JEVm5yUlpBY0RSeTh5UlZ6VXlzaXdaanJDd1pEUVBXTWVEcG0wZ1FfelhGT3ZUX2JsTFVXb0lPVnlXWXFJbzFwX1lTMGxxLU12aVNZODF2RkZibG53WUZkaEE2NkVGajNWUEx5MEh3ZVl5Y2l2NkVNZ2RUSGg3aFZvb3RIMm1TYTE3eDVUdjFnS0pWd0lDUXBPdUVfWmdpQzg9MkMzRjBFMDg=").multiPart("file",new File("C:\\Users\\madhu\\ss1.png")).log().all().post("/rest/api/3/issue/{key}/attachments");

	}

}
