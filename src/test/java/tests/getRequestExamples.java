package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class getRequestExamples {

    @Test
    public void testGetList() {
        given().
                get("https://reqres.in/api/users").
                then().
                statusCode(200).
                and().
                body("data[0].first_name", equalTo("George")).
                body("data[0].last_name", equalTo("Bluth")).
                body("data.first_name", hasItem("Charles")).
                body("data.last_name", hasItems("Weaver", "Bluth")).log().all();



    }


    @Test
    public void testGetListWithHeaders() {

        given().
                accept(JSON).
                header("Connection", "keep-alive").
                when().
                get("https://reqres.in/api/users").
                then().
                assertThat().
                statusCode(200).
                and().
                body("data[0].first_name", equalTo("George")).
                body("data[0].last_name", equalTo("Bluth")).
                body("data.first_name", hasItem("Charles")).
                body("data.last_name", hasItems("Weaver", "Bluth"));
//                log().all();



    }

    @Test
    public void testGetListWithAllResponse() {

        Response response =
        given().
                log().all().
                accept(JSON).
                header("Connection", "keep-alive").
                pathParam("id", 2).
                when().
                get("https://reqres.in/api/users/{id}").
                then().
                statusCode(200).
                and().
                assertThat().
                contentType(JSON).
//                body("data.first_name", equalTo("Janet")).
                log().all().
                extract().response();



        String email = response.path("data.email");
        System.out.println("Email: " + email);
        Assert.assertEquals(email, "janet.weaver@reqres.in");

        String firstName = response.path("data.first_name");
        System.out.println("First Name: " + firstName);
        Assert.assertEquals(firstName, "Janet");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.getContentType().contains(JSON.toString()));



    }

    @Test
    public void testGetListWithQueryParams() {

        Response response =
        given().
                log().all().
                queryParam("page", "3").
                queryParam("per_page", "5").
                when().
                get("https://reqres.in/api/users").
                then().
                assertThat().
                statusCode(200).
                and().
                contentType(JSON).
                log().all().
                extract().
                response();

            String name = response.path("data[0].first_name");
            System.out.println(name);
            Assert.assertEquals(name, "George");


            String expectedSupportURL = "https://reqres.in/#support-heading";
            String actualSupportURL = response.path("support.url");

            Assert.assertEquals(expectedSupportURL, actualSupportURL);


            int expectedPerPage = 5;
            int actualPerPage = response.path("per_page");

            Assert.assertEquals(expectedPerPage, actualPerPage);



    }




}
