package org.example;

public class Main {
    private static final String NAME = "Martin B. 3";
    private static final String EMAIL = NAME + "@gmail.com";
    private static final String PASSWORD = "123456";
    private static final int USER_ID = 137033;
    private static final String NEW_NAME = "Test B.";
    private static final String NEW_EMAIL = NEW_NAME + "@abv.com";
    private static final String NEW_LOCATION = "BG";

    public static void main(String[] args) throws Exception {
        SampleRestApiImpl sampleRestApi = new SampleRestApiImpl();

        // Create,Login, Print
        sampleRestApi
                .creatUserPostRequest(NAME, EMAIL, PASSWORD)// Create new user
                .login(EMAIL, PASSWORD)// Login
                .printSingleUserData();

        // Login, get all users from given page and print them
        sampleRestApi.login(EMAIL,PASSWORD)
                .getAllUsersFromSpecificPage(1) // Get all listed user on specific page
                .printUsers();// Print users

        //Login, Get user by id, print the data
        sampleRestApi
                .login(EMAIL, PASSWORD)// Login
                .getUserById(USER_ID) // Get user info
                .printSingleUserData(); // Print user info

        // Login, Update user and print the data
        sampleRestApi
                .login(EMAIL, PASSWORD)
                .updateUser(NEW_NAME, NEW_EMAIL, NEW_LOCATION, USER_ID)
                .printSingleUserData();

        // Delete user
       if (sampleRestApi.login(EMAIL, PASSWORD).deleteUser(USER_ID) == 200 ){
           System.out.println("User has been deleted");
       }else {
           System.out.println("User hasn't been deleted");
       }
    }
}


