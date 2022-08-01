@sampleApi
Feature: Test Registration API

  @registerUser
  Scenario:Register a new user successfully by using User Registration API
    Given User Registration API url http://restapi.adequateshop.com/api/authaccount/registration
    When I sent POST request with correct parameters
    Then I receive success message

