package AllRequests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Pojo.OrderDetails;
import Pojo.Orders;
import Pojo.classLoginRequest;
import Pojo.classLoginResponse;

//USE relaxedHTTPSValidation() mtd after given() when the website asks for SSL certificate validation
public class EcommAPITest {

	public static void main(String[] args) {
		
		  RestAssured.baseURI = "https://rahulshettyacademy.com";  
		
		// Login Request
		RequestSpecification req = new RequestSpecBuilder()
				.setBaseUri(RestAssured.baseURI)
				.setContentType(ContentType.JSON).build();
		
		classLoginRequest lq = new classLoginRequest();
		
		lq.setUserEmail("pmonali62@gmail.com");
		lq.setUserPassword("Admin@123");
		
		RequestSpecification Loginreq = given()
				.log()
				.all()
				.spec(req)
				.body(lq);
		
		ResponseSpecification resspec = new ResponseSpecBuilder()
				.expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		classLoginResponse lr = Loginreq
				.when()
				.post("api/ecom/auth/login")
				.then()
				.spec(resspec)
				.log()
				.all()
				.extract()
				.response().as(classLoginResponse.class);
		
		System.out.println(lr.getToken());
		String token = lr.getToken();
		System.out.println(lr.getUserId());
		
		//ADD PRODUCT
		RequestSpecification addProductBaseRequest = new RequestSpecBuilder()
				.addHeader("Authorization", token)
				.setBaseUri(RestAssured.baseURI).build();
		
		RequestSpecification addProductReq = given()
				.log()
				.all()
				.spec(addProductBaseRequest)
				.param("productName", "Flowers")
		.param("productAddedBy", lr.getUserId())
		.param("productCategory", "Aesthetic")
		.param("productSubCategory", "Flowers")
		.param("productPrice", "12345")
		.param("productDescription", "MRMP")
		.param("productFor", "All")
		.multiPart("productImage", new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Hydrangeas.jpg"));
		
		ResponseSpecification resspecAddProd = new ResponseSpecBuilder()
				.expectStatusCode(201).expectContentType(ContentType.JSON).build();
		
		String addProductResponse = addProductReq
				.when()
				.post("/api/ecom/product/add-product")
				.then()
				.spec(resspecAddProd)
				.log().all()
				.extract()
				.response()
				.asString();
		
		JsonPath js = new JsonPath(addProductResponse);
		String prodId = js.get("productId");
		System.out.println(prodId);
		
		//CREATE ORDER
		
		RequestSpecification createOrderBaseRequest = new RequestSpecBuilder()
				.addHeader("Authorization", token)
				.setBaseUri(RestAssured.baseURI)
				.setContentType(ContentType.JSON)
				.build();
		
		OrderDetails ord_Det = new OrderDetails();
		ord_Det.setCountry("India");
		ord_Det.setProductOrderedId(prodId);
		
		Orders ord = new Orders();
		
		List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
		
		orderDetailsList.add(ord_Det);
		
		ord.setOrders(orderDetailsList);
		
		RequestSpecification createOrderReq = given()
				.log()
				.all()
				.spec(createOrderBaseRequest).body(ord);
		
		ResponseSpecification resspecCreateOrder = new ResponseSpecBuilder()
				.expectStatusCode(201).expectContentType(ContentType.JSON).build();
		
		String createOrderResponse = createOrderReq
				.when()
				.post("/api/ecom/order/create-order")
				.then()
				.spec(resspecCreateOrder)
				.log().all()
				.extract()
				.response()
				.asPrettyString();
		JsonPath js1 = new JsonPath(createOrderResponse);
		String OrdersParameter = js1.getString("orders");
		StringBuilder sb = new StringBuilder(OrdersParameter);  
		sb.deleteCharAt(0); // Remove first character  
		sb.deleteCharAt(sb.length() - 1); // Remove last character  
		String queryparam = sb.toString();  
		System.out.println("THIS IS THE PARAMETER TO PASS "+queryparam);
		
		//VIEW ORDER DETAILS
		RequestSpecification ViewOrderBaseRequest = new RequestSpecBuilder()
				.setBaseUri(RestAssured.baseURI)
				.addHeader("Authorization", token)
				.addQueryParam("id",queryparam)
				.build();
		
		RequestSpecification viewOrderDetailsReq = given()
				.log()
				.all()
				.spec(ViewOrderBaseRequest);
		
		ResponseSpecification viewOrderDetailsResponse = new ResponseSpecBuilder()
				.expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();
		
		String viewOrderDetailsResponseOutput = viewOrderDetailsReq
				.when()
				.get("/api/ecom/order/get-orders-details")
				.then()
				.spec(viewOrderDetailsResponse)
				.log().all()
				.extract()
				.response()
				.asPrettyString();
		JsonPath js2 = new JsonPath(viewOrderDetailsResponseOutput);
		String deletethisorder = js1.getString("productOrderedId");
		
		//DELETE PRODUCT		
		
		RequestSpecification DeleteProductBaseRequest = new RequestSpecBuilder()
				.setBaseUri(RestAssured.baseURI)
				.addHeader("Authorization", token)
				.addPathParam("productId",prodId)
				.build();
		
		RequestSpecification DeleteProductDetailsReq = given()
				.log()
				.all()
				.spec(DeleteProductBaseRequest);
		
		ResponseSpecification DeleteProductResponse = new ResponseSpecBuilder()
				.expectStatusCode(200)
				.expectContentType(ContentType.JSON)
				.build();
		//https://rahulshettyacademy.com/api/ecom/product/delete-product/671a2e0cae2afd4c0ba73961
		String DeleteProductResponseOutput = DeleteProductDetailsReq
				.when()
				.delete("/api/ecom/product/delete-product/{productId}")
				.then()
				.spec(DeleteProductResponse)
				.log().all()
				.extract()
				.response()
				.asPrettyString();
System.out.println(DeleteProductResponseOutput);
	}

}