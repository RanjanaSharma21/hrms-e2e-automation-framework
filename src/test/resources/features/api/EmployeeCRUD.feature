Feature: Employee CRUD API Operations

  Background:
    Given a token is created

  @apiCrudIntegration
  Scenario: Verify complete Employee lifecycle via API
  #1 CREATE LIFECYCLE
    Given a request is prepared to create an employee using API
    When a POST call is made to create an employee
    Then the status code for this request is 201
    And the request body must match the response body
    And the employee is stored as global variable "data.empNumber"

  # 2. GET LIFECYCLE
    Given a request is prepared to get an employee
    When a GET call is made to get a created employee
    Then the status code for this request is 200
    And the employee number "data.empNumber" must match the global variable employee number

  # 3. UPDATE LIFECYCLE
    Given a request is prepared to update an employee
    When a PUT call is made to update an employee
    Then the status code for this request is 200
    And the employee number "data.empNumber" must match the global variable employee number

  # 4. DELETE LIFECYCLE
    Given a request is prepared to delete an employee
    When a DELETE call is made to delete the employee
    Then the status code for this request is 200
    And the deleted employee numbers must match the requested employee numbers

