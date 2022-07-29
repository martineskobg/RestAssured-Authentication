package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimpleRestApi {
    private Response response;
    private final JSONObject requestParams = new JSONObject();
    private List<Map<Object, Object>> users = new ArrayList<>();
    private Map<String, String> newCreatedUserDataMap;

    private Object loginToken;


    public SimpleRestApi creatUserPostRequest(String name, String email, String password) {
        RestAssured.baseURI = "http://restapi.adequateshop.com/api/authaccount/registration";

        // Put all needed credentials in the request in JSONObject requestParams
        requestParams.put("name", name);
        requestParams.put("email", email);
        requestParams.put("password", password);

        // Post the request And get the data as Map from the response
        newCreatedUserDataMap = RestAssured.given()
                .header("Content-Type", "application/json")// Add a header stating the Request body is a JSON
                .body(requestParams.toJSONString()) // JSONObject is a class that represents a Simple JSON.
                .post()
                .body()
                .jsonPath()
                .getMap("data");// Post the request and check the response

        System.out.println("New created User: " + newCreatedUserDataMap);
        return this;

    }

    public SimpleRestApi login(String email, String password) {
        RestAssured.baseURI = "http://restapi.adequateshop.com/api/authaccount/login";
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

        System.out.println("Token: " + loginToken);
        return this;
    }

    public SimpleRestApi getAllUsersFromSpecificPage(int pageIndex) {
        RestAssured.baseURI = "http://restapi.adequateshop.com/api/users?page=" + pageIndex;
        // Make GET request And get the response
        response = RestAssured.given()
                .given()
                .header("Authorization", "Bearer " + loginToken)
                .get();

        // Add all listed users in users Map
        users = response.jsonPath().getList("data");
        return this;
    }

    public void printUsers() {
        System.out.println("*********** All users *********** ");
        for (Map<Object, Object> user : users) {
            System.out.println();
            user.forEach((key, value) -> System.out.println(key + " -> " + value));
        }
    }

    public void printSingleUserData(Map<String, String> userData){
        System.out.println();
        userData.forEach((key, value) -> System.out.println(key + " -> " + value));
    }

    public SimpleRestApi getUserById(int userId) {
        RestAssured.baseURI = "http://restapi.adequateshop.com/api/users/" + userId;

         RestAssured.given()
                .given()
                .header("Authorization", "Bearer " + loginToken)
                .get()
                .jsonPath()
                .getMap("data");

         return this;
    }
}
