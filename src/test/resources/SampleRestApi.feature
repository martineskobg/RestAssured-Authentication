@endpoint @categories
Feature: Test Simple Rest API

  Background:
    Given Api url base url for categories "https://api.publicapis.org/categories"

  @categoriesCount
  Scenario:Verify the categories count
    Given Expected count of all categories is 51
    When Get the actual categories count
    Then Verify that expected categories count is equal to actual count

  @categoryContent
  Scenario Outline: Check for missing category
    Given Category name <name>
    When Get all categories names
    Then heck if category is missing
    Examples:
      | name                           |
      | Animals                        |
      | Anime                          |
      | Anti-Malware                   |
      | Art & Design                   |
      | Authentication & Authorization |
      | Blockchain                     |
      | Books                          |
      | Business                       |
      | Calendar                       |
      | Cloud Storage & File Sharing   |
      | Continuous Integration         |
      | Cryptocurrency                 |
      | Currency Exchange              |
      | Data Validation                |
      | Development                    |
      | Dictionaries                   |
      | Documents & Productivity       |
      | Email                          |
      | Entertainment                  |
      | Environment                    |
      | Events                         |
      | Finance                        |
      | Food & Drink                   |
      | Games & Comics                 |
      | Geocoding                      |
      | Government                     |
      | Health                         |
      | Jobs                           |
      | Machine Learning               |
      | Music                          |
      | News                           |
      | Open Data                      |
      | Open Source Projects           |
      | Patent                         |
      | Personality                    |
      | Phone                          |
      | Photography                    |
      | Programming                    |
      | Science & Math                 |
      | Security                       |
      | Shopping                       |
      | Social                         |
      | Sports & Fitness               |
      | Test Data                      |
      | Text Analysis                  |
      | Tracking                       |
      | Transportation                 |
      | URL Shorteners                 |
      | Vehicle                        |
      | Video                          |
      | Weather                        |

