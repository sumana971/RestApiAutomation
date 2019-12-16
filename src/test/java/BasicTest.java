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


public class BasicTest {

    String authtoken = null;


    @Test
    public void teststatuscode()
    {
        given()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products")
                .then()
                .statusCode(200);


    }

    @Test
    public void testLogging()
    {
        given()
                .log().all()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");

    }

    @Test
    public void responseParams()
    {
        Response res=given().when()
                .log().all()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        System.out.println(res.prettyPeek());
        System.out.println("******************************");
    }

    @Test
    public void testCurrency() {
        given()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products")
                .then()
                .body("data.attributes[0].currency", equalTo("USD"));
    }

     @Test
        public void getCurrencyDetails() {
         Response res = given()
                 .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
         JsonPath jsonpathevaluator = res.jsonPath();
         // Using List to get the currency details
         List<Map> products = jsonpathevaluator.getList("data");
         for (Map product : products) {
             Map attributes = (Map) product.get("attributes");
             System.out.println(attributes.get("currency"));
             Assert.assertEquals(attributes.get("currency").toString(), "USD");


         }
     }
            @Test
            public void testFilter()
            {
                Response ress=given()
                        .log().all()
                        .queryParam("name","bag")
                        .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
                System.out.println(ress.prettyPeek());
            }

            @Test
            public void testPriceValues()
            {
                Response s=given()
                        .log().all()
                        .queryParams("price","$19.9>or over")
                        .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
                System.out.println(s.prettyPeek());
            }
            @Test
            public void testTaxons()
            {
                Response t=given()
                        .log().all()
                        .queryParams("filter[taxons]","Ruby")
                        .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
                System.out.println(t.prettyPeek());
            }
            @Test
            public void testfilterids()
            {
                Response s=given()
                        .log().all()
                        .queryParams("fiter[id]","12")
                        .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
                System.out.println(s.prettyPeek());
            }
            @Test
            public void testTshirtColor()
            {
                Response st=given()
                        .log().all()
                        .queryParam("filter[options][tshirt-color]","M","Red")
                        .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
                System.out.println(st.prettyPeek());

            }
            @BeforeClass
            public void authToken()
            {
                Response r=given()
                        .formParam("grant_type","password")
                        .formParam("username","sumu971@gmail.com")
                        .formParam("password","Orange@971")
                        .post("https://spree-vapasi-prod.herokuapp.com/spree_oauth/token");
                System.out.println(r.prettyPeek());
                authtoken="Bearer " +  r.path("access_token");



            }

            @Test
    public void testPostCall()
            {
                Map<String, String> headers=new HashMap<String, String>();
                headers.put("Content-Type","application/json");
                headers.put("Authorization",authtoken);
                String respbody="{\n" +
                        "  \"variant_id\": \"17\",\n" +
                        "  \"quantity\": 5\n" +
                        "}";

                Response re=given()
                        .headers(headers)
                        .body(respbody)
                        .when()
                        .post("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart/add_item");
                Assert.assertEquals(re.statusCode(),200);
                System.out.println(re.prettyPeek());


            }

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

            @Test
            public void viewcart()
            {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", authtoken);

                Response st2 = given()
                        .headers(headers)
                        .when()
                        .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart");
               // Assert.assertEquals(st.statusCode(), 200);
                System.out.println(st2.prettyPeek());

            }
            @Test
            public void addToCart()
            {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", authtoken);


                Response st1 = given()
                        .headers(headers)
                        .when()
                        .post("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart");

                System.out.println(st1.prettyPeek());

            }
            @Test
    public void deleteItem()
            {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", authtoken);


                Response st1 = given()
                        .headers(headers)
                        .when()
                .delete("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/cart/remove_line_item/448");
                System.out.println(st1.prettyPeek());

            }










     }




