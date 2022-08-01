@sampleApi
Feature: Test Simple Rest API

  Background:
    Given Login Api url http://restapi.adequateshop.com/api/authaccount/login

  @getUsers
  Scenario: List all users on a given page by using Get All Users API
    Given Get All Users API url http://restapi.adequateshop.com/api/users?page=
    When I sent GET request with page index 1
    Then As response I receive 10 users

  @getUserById
  Scenario: Get single user by id
    Given Get User By Id API url http://restapi.adequateshop.com/api/users/
    When I sent GET request with existing user ID as parameter
    Then I receive the user data as response

  @updateUser
  Scenario: Update single user information
    Given Update User Object API url http://restapi.adequateshop.com/api/users/
    When I sent PUT request with correct data
    Then I receive the user data with updated parameters as response

  @deleteUser
  Scenario: Delete user
    Given Delete user by Id url http://restapi.adequateshop.com/api/users/
    When I sent DELETE request with existing user ID as parameter
    Then Verify the status code is 200
