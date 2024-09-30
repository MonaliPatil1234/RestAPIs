package files;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		
JsonPath js = new JsonPath(payload.CoursePrice());

//PRINT NO OF COURSES RETURNED BY API

int count  =  js.getInt("courses.size()");
System.out.println(count);

//PRINT PURCHASE AMOUNT
int purchaseamnt = js.getInt("dashboard.purchaseAmount");
System.out.println(purchaseamnt);

//PRINT THE TITLE OF THE 1ST COURSE
String title = js.get("courses[0].title");
System.out.println(title);

//PRINT ALL COURSE TITLES WITH RESP PRICE
for(int i=0;i<count;i++)
{
	String title1 = js.getString("courses["+i+"].title");
	System.out.println(title1);
	int price1= js.getInt("courses["+i+"].price");
	System.out.println(price1);
}

//PRINT THE NO OF COPIES FOR THE COURSE NAMED RPA
for(int i=0;i<count;i++)
{
	String title1 = js.getString("courses["+i+"].title");
	if(title1.equalsIgnoreCase("RPA")) {
		System.out.println("No of Copies for the course RPA are "+js.get("courses["+i+"].copies").toString());
		break;// stop the loop execution once RPA is found
	}
}	
	//VERIFY IF PURCHASE AMOUNT MATCHES WITH THE SUM OF COURSE PRICES
	int sum = 0;
	for(int i=0;i<count;i++)
	{
		
		
		int price1= js.getInt("courses["+i+"].price");
		int numofcopies = js.getInt("courses["+i+"].copies");
		sum = sum + (price1*numofcopies);
		
	}
	System.out.println(sum);
	Assert.assertEquals(sum, purchaseamnt);

 
	}

}
