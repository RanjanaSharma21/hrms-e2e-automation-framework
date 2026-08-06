Feature: Employee API Request Payload Validation

  Background:
    Given a token is created


  @map
  Scenario: Create an employee using Map request body
    Given a request is prepared to create an employee using Map format
    When a POST call is made to create an employee
    Then the status code for this request is 201
    And the Map request body must match the response body
    And the employee is stored as global variable "data.empNumber"

  @rawjson
  Scenario: Create an employee using JSON request body
    Given a request is prepared to create an employee using JSON format
    When a POST call is made to create an employee
    Then the status code for this request is 201
    And the JSON request body must match the response body
    And the employee is stored as global variable "data.empNumber"

  @dynamicjson
  Scenario Outline: Create an employee using dynamic JSON request body
    Given a request is prepared to create an employee using dynamic JSON format with "<firstName>", "<lastName>", "<middleName>", "<gender>", "<birthday>", "<jobTitle>"
    When a POST call is made to create an employee
    Then the status code for this request is 201
    And the dynamic JSON request body must match the response body
    And the employee is stored as global variable "data.empNumber"

    Examples:
      | firstName | lastName | middleName | gender | birthday   | jobTitle |
      | Janki     | Vallabh  | Sita       | F      | 1990-01-15 | SDET     |
      | Ranjana   | Sharma   | ms         | F      | 2004-11-09 | SDET     |
      | Palash    | Sharma   |            | M      | 2006-05-15 | SDET     |
