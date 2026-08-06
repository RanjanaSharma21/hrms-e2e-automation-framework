Feature: Employee API using POJO serialization

  Background:
    Given a token is created


  @lombokpojo
  Scenario Outline: Create an employee using lombok pojo dynamic JSON request body
    Given a request is prepared to create an employee using lombok pojo format with "<firstName>", "<lastName>", "<middleName>", "<gender>", "<birthday>", "<jobTitle>"
    When a POST call is made to create an employee
    Then the status code for this request is 201
    And the POJO request body must match the response body
    And the response structure must match the employee JSON schema blueprint
    And the employee is stored as global variable "data.empNumber"

    Examples:
      | firstName | lastName | middleName | gender | birthday   | jobTitle |
      | Janki     | Vallabh  | Sita       | F      | 1990-01-15 | SDET     |
      | Ranjana   | Sharma   | ms         | F      | 2004-11-09 | SDET     |
      | Palash    | Sharma   |            | M      | 2006-05-15 | SDET     |