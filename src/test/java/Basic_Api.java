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
public class Basic_Api {
    //given()
    //when()
    //then()
    //Filter params

    @Test
    public void getApiTestWithTwoQueryParams()
    {
        RestAssured.baseURI="https://spree-vapasi-prod.herokuapp.com/";

        given().log().all()
                .contentType("application/json")
                .headers("Authorization","Bearer iehrjZTEbeN7ZNDLgUx47RHvZQ8eppXQz8IFoD-7mZI")
                .queryParam("id","3")
                .queryParam("price","29.99")
                .queryParam("taxon","type")
                .queryParam("name","Ruby on Rails Baseball Jersey").log().all()
                .queryParam("show_deleted","449").log().all()
                .when().log().all()
                .get("api/v2/storefront/products")
                .then().log().all()
                .statusCode(200);



    }
//Validate the content type is JSON
    @Test
    public void getApiTest2()
    {
        RestAssured.baseURI="https://spree-vapasi-prod.herokuapp.com/";
        given()
                .when()
                .get("api/v2/storefront/products")
                .then().assertThat().statusCode(200)

        .and()
                .contentType(ContentType.JSON);


    }


//Validate Currency details
    @Test
    public void getCurrencyDetails()
    {
        Response res=given().log().all()
                .get("https://spree-vapasi-prod.herokuapp.com/api/v2/storefront/products");
        JsonPath jsonpathevaluator=res.jsonPath();
        List<Map> products = jsonpathevaluator.getList("data");
        for (Map product : products) {
            Map attributes = (Map) product.get("attributes");
            System.out.println(attributes.get("currency"));
            Assert.assertEquals(attributes.get("currency").toString(), "USD");


        }
    }

}
