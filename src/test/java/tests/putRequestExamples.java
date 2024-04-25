package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class putRequestExamples {


    @Test
    public void putTest() throws JsonProcessingException {
        baseURI = "https://reqres.in/api/users/2";

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("name", "Adam");
        requestMap.put("job", "Project Manager");

        System.out.println(requestMap);

        String jsonBody = new ObjectMapper().writeValueAsString(requestMap);

        Response response =
                given().
                        contentType(ContentType.JSON).
                        accept(ContentType.JSON).
                        body(jsonBody).
                        when().
                        put("/api/users/2").
                        then().
                        statusCode(200).
                        and().
                        contentType(ContentType.JSON).
                        log().all().
                        extract().response();


        System.out.println("========================");
        String jobTitle = response.path("job");
        System.out.println();
        Assert.assertEquals(response.path("job"),"Project Manager");



    }


}
