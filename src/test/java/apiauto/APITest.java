package apiauto;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class APITest {

    @Test
    public void getUserTest() {
        // Define baseURI
        RestAssured.baseURI = "https://reqres.in/";

        // Test GET api/users?page=1 with total data 6 per page
        given().when().get("api/users?page=1")
                .then()
                .log().all() // log().all() is used to print the entire request to console (optional)
                .assertThat().statusCode(200) // assert the status code to be 200 (OK)
                .assertThat().body("page", Matchers.equalTo(1)) // assert that we access the correct page
                .assertThat().body("data.id", Matchers.hasSize(6)); // assert that the data has 6 entries
    }

    @Test
    public void createNewUserTest() {
        // Define baseURI
        RestAssured.baseURI = "https://reqres.in/";

        // Create POST body with parameters "name" and "job" in JSON format
        String name = "jayjay";
        String job = "student";

        JSONObject jsonObject = new JSONObject(); // HashMap alternative
        jsonObject.put("name", name);
        jsonObject.put("job", job);

        // Test POST with header that accepts JSON format
        given().log().all() // log().all() is used to print the entire request to console (optional)
                .header("Content-Type", "application/json") // RequestSpecification
                .header("Accept", "application/json")
                .body(jsonObject.toString()) // Convert JSON to string format -> {"name":"jayjay","job":"student"}
                .post("api/users")
                .then()
                .assertThat().statusCode(201) // Assert the status code to be 201 (Created)
                .assertThat().body("name", Matchers.equalTo(name)) // Assert response body "name"
                .assertThat().body("job", Matchers.equalTo(job)) // Assert response body "job"
                .assertThat().body("$", Matchers.hasKey("id")) // Assert response body has key "id"
                .assertThat().body("$", Matchers.hasKey("createdAt")); // Assert response body has key "createdAt"
    }

    @Test
    public void testDeleteUser() {
        // Define base URI
        RestAssured.baseURI = "https://reqres.in/";

        // Data to delete
        int userToDelete = 999999999;

        // Test DELETE api/users/4
        given()
                .log().all()  // Log the request details (optional)
                .when()
                .delete("api/users/" + userToDelete)  // Perform DELETE request
                .then()
                .log().all()  // Log the response details (optional)
                .statusCode(204);  // Assert the status code is 204 (No Content)
    }

//log().all() is used to print the entire response to console (optional) .assertThat().statusCode ( expectedStatusCode: 284); //assert that the status code is 284
}