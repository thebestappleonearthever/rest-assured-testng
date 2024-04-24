package tests;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostRequestExamples {

    @Test
    public void testPostRequest() throws JsonProcessingException {

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("name", "Adam");
        requestMap.put("job", "Software Engineer");

        System.out.println(requestMap);

        System.out.println("====================");

        // RestAssured use Jackson Library to convert json to java / java to json

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(requestMap);

        System.out.println(jsonRequest); // compact print

        System.out.println("====================");



        RestAssured.baseURI = "https://reqres.in/api/users";

        Response response = given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(jsonRequest).
                when().
                post("/api/users").
                then().
                extract().response();

        Assert.assertEquals(response.getStatusCode(), 201);

        Map<String, String>  responseMap = objectMapper.readValue(response.asString(), Map.class);

        System.out.println(responseMap);

        Assert.assertEquals(responseMap.get("name"), "Adam");
        Assert.assertEquals(responseMap.get("job"), "Software Engineer");





    }


}
