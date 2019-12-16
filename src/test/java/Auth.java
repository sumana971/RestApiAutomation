import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class Auth {
    String authtoken = null;

    // Test case tp generate the Token using the POST CALL, it generates an new token by giving the form params
    @BeforeClass
    public void authToken() {

        Response r = given()
                .formParam("grant_type", "password")
                .formParam("username", "sumu971@gmail.com")
                .formParam("password", "Orange@971")
                .post("https://spree-vapasi-prod.herokuapp.com/spree_oauth/token");
        System.out.println(r.prettyPeek());
        authtoken = "Bearer " + r.path("access_token");
        System.out.println(authtoken);

    }

    //Get the product List

    @Test
    public void getProductList() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", authtoken);
        Response st2 = given()
                .headers(headers)
                .when()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        Assert.assertEquals(st2.statusCode(), 200);
        System.out.println(st2.prettyPeek());

    }

    //Show the Product List
    @Test
    public void showProductList() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", authtoken);
        Response st2 = given()
                .headers(headers)
                .when()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products/1");
        Assert.assertEquals(st2.statusCode(), 200);
        System.out.println(st2.prettyPeek());

    }

    //POST CALL TO ADD ITEMS TO THE CART
    @Test
    public void testPostCall() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", authtoken);
        String respbody = "{\n" +
                "  \"variant_id\": \"17\",\n" +
                "  \"quantity\": 5\n" +
                "}";

        Response re = given()
                .headers(headers).log().all()
                .body(respbody)
                .when().log().all()
                .post("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart/add_item");
        Assert.assertEquals(re.statusCode(), 200);
        System.out.println(re.prettyPeek());


    }

    //To add second item to cart

    @Test
    public void addsecItem() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", authtoken);
        String respbody = "{\n" +
                "  \"variant_id\": \"2\",\n" +
                "  \"quantity\": 5\n" +
                "}";

        Response st = given()
                .headers(headers)
                .body(respbody)
                .when()
                .post("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart/add_item");
        Assert.assertEquals(st.statusCode(), 200);
        System.out.println(st.prettyPeek());
    }

    //To add third item
    @Test
    public void addotherItem() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", authtoken);
        String respbody = "{\n" +
                "  \"variant_id\": \"2\",\n" +
                "  \"quantity\": 5\n" +
                "}";

        Response st = given()
                .headers(headers)
                .body(respbody)
                .when()
                .post("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart/add_item");
        Assert.assertEquals(st.statusCode(), 200);
        System.out.println(st.prettyPeek());
    }

    //To view the cart
    @Test
    public void viewcartdetails() {
        Map<String, String> headers = new HashMap<String, String>();
        //headers.put("Content-Type", "application/json");
        headers.put("Authorization", authtoken);

        Response st2 = given()
                .headers(headers).log().all()
                .when().log().all()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart");
        System.out.println(st2.prettyPeek());
    }

    //To delete this item
    //Delete item

    @Test
    public void deleteItem() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", authtoken);


        Response st1 = given()
                .headers(headers).log().all()
                .when().log().all()
                .delete("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart/remove_line_item/451");
        System.out.println(st1.prettyPeek());


    }




    @Test
    public void getCurrencyDetails() {
        Response res = given().log().all()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        JsonPath jsonpathevaluator = res.jsonPath();
        List<Map> products = jsonpathevaluator.getList("data");
        for (Map product : products) {
            Map attributes = (Map) product.get("attributes");
            System.out.println(attributes.get("currency"));
            Assert.assertEquals(attributes.get("currency").toString(), "USD");


        }

    }
}
