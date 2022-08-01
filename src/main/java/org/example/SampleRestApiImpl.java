package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SampleRestApiImpl implements SampleRestApi {
    private Response response;
    private final JSONObject requestParams = new JSONObject();
    private List<Map<Object, Object>> users = new ArrayList<>();
    private Map<Object, Object> userData;
    private Object loginToken;

    /**
     * Registrate a user with correct credentials
     * @param name String
     * @param email String
     * @param password String
     * @return SampleRestApiImpl.class
     */
    public SampleRestApiImpl creatUserPostRequest(String name, String email, String password) {
        RestAssured.baseURI = registrationUrl;

        // Put all needed credentials in the request in JSONObject requestParams
        requestParams.put("name", name);
        requestParams.put("email", email);
        requestParams.put("password", password);

        // Post the request And get the data as Map from the response
        userData = RestAssured
                .given()
                .header("Content-Type", "application/json")// Add a header stating the Request body is a JSON
                .body(requestParams.toJSONString()) // JSONObject is a class that represents a Simple JSON.
                .post()
                .body()
                .jsonPath()
                .getMap("data");// Post the request and check the response

        System.out.println("New created User: " + userData);
        return this;

    }

    /**
     * Login by email na password
     * @param email String
     * @param password String
     * @return SampleRestApiImpl.class
     */
    public SampleRestApiImpl login(String email, String password) {
        RestAssured.baseURI = loginUrl;
        requestParams.put("email", email);
        requestParams.put("password", password);
        // Post the request And get the Token key from the response
        loginToken = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestParams.toJSONString())
                .post()
                .body()
                .jsonPath()
                .getMap("data")
                .get("Token");
        return this;
    }

    /**
     * Get all users from given page
     * @param pageIndex int
     * @return SampleRestApiImpl.class
     */
    public SampleRestApiImpl getAllUsersFromSpecificPage(int pageIndex) {
        RestAssured.baseURI = getAllUsersUrl + pageIndex;
        // Make GET request And get the response
        response = RestAssured
                .given()
                .header("Authorization", "Bearer " + loginToken)
                .get();

        // Add all listed users in users Map
        users = response.jsonPath().getList("data");
        return this;
    }

    /**
     * Print List of users data
     */
    public void printUsers() {
        System.out.println("*********** All users *********** ");
        for (Map<Object, Object> user : users) {
            System.out.println();
            user.forEach((key, value) -> System.out.println(key + " -> " + value));
        }
    }

    /**
     * Print user data
     */
    public void printSingleUserData() {
        System.out.println();
        userData.forEach((key, value) -> System.out.println(key + " -> " + value));
    }

    /**
     * Get user by ID
     * @param userId int
     * @return SampleRestApiImpl.class
     */
    public SampleRestApiImpl getUserById(int userId){
        RestAssured.baseURI = getUsersUrl + userId;

        // Make GET request And get the response
        response = RestAssured
                .given()
                .header("Authorization", "Bearer " + loginToken)
                .get();

        // Save the result as map
        userData = response.jsonPath().getMap("");
        return this;
    }

    /**
     * Sent request to update given user
     * @param userId int
     * @param newName String
     * @param newEmail String
     * @param newLocation String
     * @return SampleRestApiImpl.class
     */
    public SampleRestApiImpl updateUser(int userId, String newName, String newEmail, String newLocation) {
        RestAssured.baseURI = updateUserUrl + userId;
        requestParams.put("id", userId);
        requestParams.put("name", newName);
        requestParams.put("email", newEmail);
        requestParams.put("location", newLocation);
        // Make PUT request
        response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + loginToken)
                .body(requestParams.toJSONString())
                .put();

        userData = response.jsonPath().getMap("");
        return this;
    }

    /**
     * Delete user by ID
     * @param userId int
     * @return Status code returned from the API
     */
    public int deleteUser(int userId) {
        RestAssured.baseURI = deleteUserUrl + userId;
        // Returns status code
        return RestAssured
                .given()
                .header("Authorization", "Bearer " + loginToken)
                .delete()
                .getStatusCode();
    }
}
