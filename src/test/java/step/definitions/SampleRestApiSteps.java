package step.definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

public class SampleRestApiSteps {
    private final String NAME = "Martin7D";
    private final String EMAIL = NAME + "@gmail.com";
    private final String PASSWORD = "123456";
    private final String NEW_NAME = "Test7D";
    private final String NEW_LOCATION = "BG";
    private final JSONObject requestParams = new JSONObject();
    private Response response;
    private Object token;
    private static Object createdUserId;


    @Given("User Registration API url {}")
    public void user_registration_api_url(String regURL) {
        RestAssured.baseURI = regURL;
        System.out.println("Set base URL for registration API " + regURL);
    }

    @When("I sent POST request with correct parameters")
    public void i_sent_post_request_with_correct_parameters() {
        System.out.println("Sent POST request for new registration");

        requestParams.put("name", NAME);
        requestParams.put("email", EMAIL);
        requestParams.put("password", PASSWORD);

        response = RestAssured
                .given()
                .header("Content-Type", "application/json")// Add a header stating the Request body is a JSON
                .body(requestParams.toJSONString()) // JSONObject is a class that represents a Simple JSON.
                .post();
    }

    @Then("I receive success message")
    public void i_receive_success_message() {
        System.out.println("Get message after creating a user");
       // This ID will be used in delete request
        createdUserId = response.jsonPath().getMap("data").get("Id");
        String message = response.jsonPath().getString("message");
        Assertions.assertEquals("success", message, "The message is not success!");
    }

    @Given("Login Api url {}")
    public void login_api_url(String loginUrl) {
        System.out.println("Set Login api url and save the token");
        RestAssured.baseURI = loginUrl;
        requestParams.put("email", EMAIL);
        requestParams.put("password", PASSWORD);
        // Post the request And get the Token key from the response
        token = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestParams.toJSONString())
                .post()
                .body()
                .jsonPath()
                .getMap("data")
                .get("Token");
    }

    @Given("Get All Users API url {}")
    public void get_all_users_api_is_accessible(String getAllUsersUrl) {
        System.out.println("Set base URL for Get All users" + getAllUsersUrl);
        RestAssured.baseURI = getAllUsersUrl;
    }

    @When("I sent GET request with page index {int}")
    public void i_sent_get_request_with_page_index(Integer index) {
        System.out.println("Sent GET request with page index " + index);
        RestAssured.baseURI += index;
        response = RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .get();
    }

    @Then("As response I receive {int} users")
    public void as_response_i_receive_users(Integer countOfUsers) {
        System.out.println("Assert count of received users");
        int count = response.jsonPath().getList("data").size();
        Assertions.assertEquals(countOfUsers, count, "Different count of users!");
    }

    @Given("Get User By Id API url {}")
    public void get_user_by_id_is_accessible(String getUserUrl) {
        RestAssured.baseURI = getUserUrl;
        System.out.println("Set Get USer API url " + getUserUrl);
    }

    @When("I sent GET request with existing user ID as parameter")
    public void i_sent_get_request_with_existing_user_id_as_parameter() {
        RestAssured.baseURI += createdUserId;

        response = RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .get();
        System.out.println("Sent GET request with existing user ID " + createdUserId);
    }

    @Then("I receive the user data as response")
    public void i_receive_the_user_data_as_response() {
        System.out.println("Checking the response after using Get User By Id API");
        Map<Object, Object> userData = response.jsonPath().getMap("");
        Assertions.assertEquals(createdUserId, userData.get("id"), "There is no user with id " + createdUserId);
    }

    @Given("Update User Object API url {}")
    public void update_user_object_api_is_accessible(String updateUserUrl) {
        RestAssured.baseURI = updateUserUrl;
        System.out.println("Set URL for Update User Object API " + updateUserUrl);
    }

    @When("I sent PUT request with correct data")
    public void i_sent_put_request_with_correct_credentials() {
        System.out.println("Sent PUT request for updating the user");
        RestAssured.baseURI += createdUserId;
        requestParams.put("id", createdUserId);
        requestParams.put("name", NEW_NAME);
        requestParams.put("location", NEW_LOCATION);
        response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(requestParams.toJSONString())
                .put();

    }

    @Then("I receive the user data with updated parameters as response")
    public void i_receive_the_user_data_with_updated_parameters_as_response() {
        System.out.println("Checking if the user has been updated");
        Map<Object, Object> updatedUserData = response.jsonPath().getMap("");

        Assertions.assertEquals(NEW_NAME, updatedUserData.get("name"), "The name hasn't been changed");
        Assertions.assertEquals(NEW_LOCATION, updatedUserData.get("location"), "The location hasn't been changed");
    }

    @Given("Delete user by Id url {}")
    public void delete_user_by_id_is_accessible(String deleteUserUrl) {
        RestAssured.baseURI = deleteUserUrl;
        System.out.println("Set Delete User By Id URL " + deleteUserUrl);
    }

    @When("I sent DELETE request with existing user ID as parameter")
    public void i_sent_delete_request_with_existing_user_id_as_parameter() {
        System.out.println("Sent DELETE request with existing ID ");
        RestAssured.baseURI += createdUserId;
        response = RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .delete();
    }

    @Then("Verify the status code is {int}")
    public void verify_the_status_code_is(Integer statusCode) {
        int expectedStatusCode = statusCode;
        int actualStatusCode = response.getStatusCode();
        Assertions.assertEquals(expectedStatusCode, actualStatusCode, "Status code is not " + actualStatusCode);
        System.out.println("Verify the status code");
    }
}
