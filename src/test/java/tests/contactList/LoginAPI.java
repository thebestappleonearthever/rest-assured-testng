package tests.contactList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utility.ContactListTestBase;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class LoginAPI extends ContactListTestBase {


    @Test
    public void loginTest() throws JsonProcessingException {

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("email", "keskinkerim709@gmail.com");
        userInfo.put("password", "hello1234");

        String jsonRequest = new ObjectMapper().writeValueAsString(userInfo);

        Response response =
                given().
                        contentType(ContentType.JSON).
                        accept(ContentType.JSON).
                        body(jsonRequest).
                        when().
                        post("users/login").
                        then().
                        statusCode(200).
                        contentType(ContentType.JSON).
                        log().all().
                        extract().response();

        String token = response.path("token");

        System.out.println(token);

    }

    @Test
    public void loginTestJsonPath() throws JsonProcessingException {

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("email", "keskinkerim709@gmail.com");
        userInfo.put("password", "hello1234");

        String jsonRequest = new ObjectMapper().writeValueAsString(userInfo);

        Response response =
                given().
                        contentType(ContentType.JSON).
                        accept(ContentType.JSON).
                        body(jsonRequest).
                        when().
                        post("users/login").
                        then().
                        statusCode(200).
                        contentType(ContentType.JSON).
                        log().all().
                        extract().response();

        String token = response.path("token");

        JsonPath jsonPath = response.jsonPath();

        String token1 = jsonPath.getString("token");

        System.out.println(token);
        System.out.println("=======");
        System.out.println(token);

    }


}
