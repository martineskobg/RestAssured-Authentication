package org.example;

public class Main {
    private static final String name = "marto125";
    private static final String email = name + "@gmail.com";
    private static final String password = "123456";
    private static final int userId = 136960;

    public static void main(String[] args) {
        SimpleRestApi simpleRestApi = new SimpleRestApi();

        // Create,Login, Get, Print
        simpleRestApi
                .creatUserPostRequest(name, email, password)// Create new user
                .login(email, password)// Login
                .getAllUsersFromSpecificPage(1) // Get all listed user on specific page
                .printUsers();// Print users

        //Login, Get user by id, print the data
//        simpleRestApi
//                .login("bachev14@gmail.com", password)// Login
//                .getUserById(userId) // Get user info
//               .printSingleUserData(); // Print user info
    }
}


