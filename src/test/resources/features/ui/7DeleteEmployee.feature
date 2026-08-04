@delemp
Feature: Employee Delete Management

  Background:
    Given User is on the HRM login page
    When user enters valid username and valid password as an "Admin"
    And user clicks on login button
    Then user is able to login successfully
    And user clicks on PIM option

  @delemp @regression @smoke
  Scenario: Delete employee using a dynamically saved employee ID
    When user enters the dynamically saved employee id into the ID filter field
    And user clicks on User Management Search button
    Then the system returns exactly 1 matching record row in the data grid
    When user clicks on delete button
    And user confirms employee deletion
    Then employee is deleted successfully from the application
    And employee should not exist in the database

