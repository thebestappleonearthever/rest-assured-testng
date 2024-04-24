package tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.ResponseValidator;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class universitiesApiTest {
    @Test
    public void verifyUniversityNames(){
        Response response =
                given().
                        queryParam("country", "United States").
                        queryParam("name", "Technology").
                        queryParam("limit", 5).
                        when().
                        get("http://universities.hipolabs.com/search").
                        then().
                        assertThat().
                        statusCode(200).
                        and().
                        extract().response();

        String expectedText = "Detroit School of Technology";
        List actualText = response.path("name");

        System.out.println(actualText);

        Assert.assertTrue(actualText.contains(expectedText));

        // assert with index
        String actualText1 = response.path("name[0]");
        System.out.println(actualText1);
        Assert.assertTrue(actualText1.contains(expectedText));

        // string check
        System.out.println("====================================");
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);

        Assert.assertTrue(responseBody.contains(expectedText));
        Assert.assertTrue(responseBody.contains("Technology"));
        Assert.assertTrue(responseBody.contains("US"));


    }




    @Test
    public void verifyUniversityNamesWithHamcrestMatcher(){

        String expectedText = "Detroit School of Technology";

                given().
                        queryParam("country", "United States").
                        queryParam("name", "Technology").
                        queryParam("limit", 5).
                        when().
                        get("http://universities.hipolabs.com/search").
                        then().
                        assertThat().
                        statusCode(200).
                        and().
                        body("name[0]", equalTo(expectedText)).
                        body("country", hasItems("United States")).
                        body("size()", is(5));




    }

    @Test
    public void verifyUniversityNamesContains() {

        String expectedText = "Technology";

        Response response =
                given().
                        queryParam("country", "United States").
                        queryParam("name", "Technology").
                        queryParam("limit", 5).
                        when().
                        get("http://universities.hipolabs.com/search").
                        then().
                        assertThat().
                        statusCode(200).extract().response();

        List<String> universityList = response.path("name");
        System.out.println(universityList);

        for (String universityName : universityList) {
            Assert.assertTrue(universityName.contains(expectedText));
        }


        }


    @Test
    public void verifyUniversityNamesWithUtility() {

        String expectedText = "Technology";

        Response response =
                given().
                        queryParam("country", "United States").
                        queryParam("name", "Technology").
                        queryParam("limit", 5).
                        when().
                        get("http://universities.hipolabs.com/search").
                        then().
                        assertThat().
                        statusCode(200).extract().response();

        List<String> universityList = response.path("name");
        System.out.println(universityList);

        Assert.assertTrue(ResponseValidator.containsSpecificText(universityList, expectedText));


    }
}
