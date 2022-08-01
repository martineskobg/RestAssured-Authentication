# RestAssured: RestAssured Authentication

### Description
First project in which I'm using RestAssured,Json path and Cucumber.

#### Theory:
* What is Bearer token and How it works?

#### Practical tasks:
**Task 1**
Create Java class with one main method and execute simple RestAssured requests for:

* Create user in the api with post request
* Login with created credentials and get the baerer token
* By using the generated token, list users of page 1
* Print the results of the request to the console

**Task 2**
Create test scenarios with cucumber for:
1. Create user in the api
2. List users on the specific page
3. Get user by id
4. Update information for your user
5. Delete your user

### Instruction
Create a standard Maven project
Then copy all dependencies from the pom.xml file in this project
You should be able to use RestAssured and json path and cucumber as well

#### Prerequisites
As APIs you should use:
https://www.appsloveworld.com/sample-rest-api-url-for-testing-with-authentication

##### Useful links:
1. https://www.devopsschool.com/blog/what-is-bearer-token-and-how-it-works/