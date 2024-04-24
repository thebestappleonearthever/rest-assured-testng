package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class Countries {

    @Test
    public void countryNameAndCurrency() {

        RestAssured.baseURI = "https://restcountries.com";

        Response response =
         given().
                 pathParam("capitalName", "baku").
                 when().
                 get("/v3.1/capital/{capitalName}").
                 then().
                 log().all().
                 extract().
                 response();

        Assert.assertEquals(response.getStatusCode(), 200);

        List country = response.path("name.common");

        Assert.assertEquals(country.get(0), "Azerbaijan");



        List currency = response.path("currencies.AZN.name");
        Assert.assertEquals(currency.get(0), "Azerbaijani manat");


    }

}
