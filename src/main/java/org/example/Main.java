package org.example;

public class Main {
    private static final String NAME = "Martin BA2";
    private static final String EMAIL = NAME + "@gmail.com";
    private static final String PASSWORD = "123456";
    private static final int USER_ID = 137851;
    private static final String NEW_NAME = "Test B.B2";
    private static final String NEW_EMAIL = NEW_NAME + "@abv.com";
    private static final String NEW_LOCATION = "BG";

    public static void main(String[] args) {
        SampleRestApiImpl sampleRestApi = new SampleRestApiImpl();

        // Create,Login, Print
        sampleRestApi
                .creatUserPostRequest(NAME, EMAIL, PASSWORD)// Create new user
                .login(EMAIL, PASSWORD)// Login
                .printSingleUserData();

        // Get all users from given page and print them
        sampleRestApi
                .getAllUsersFromSpecificPage(1) // Get all listed user on specific page
                .printUsers();// Print users

        // Get user by id, print the data
        sampleRestApi
                .getUserById(USER_ID) // Get user info
                .printSingleUserData(); // Print user info

        // Update user and print the data
        sampleRestApi
                .updateUser(USER_ID, NEW_NAME, NEW_EMAIL, NEW_LOCATION)
                .printSingleUserData();

        // Delete user
        if (sampleRestApi.login(EMAIL, PASSWORD).deleteUser(USER_ID) == 200) {
            System.out.println("\nUser has been deleted");
        } else {
            System.out.println("User hasn't been deleted");
        }
    }
}


