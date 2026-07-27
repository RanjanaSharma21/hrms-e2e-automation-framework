Feature: Add an employee

  Background:
    Given User is on the HRM login page
    When user enters valid username and valid password as an "Admin"
    And user clicks on login button
    Then user is able to login successfully
    And user clicks on PIM option
    And user clicks on Add Employee option

  @addemp1
  Scenario: Add employee with system generated employee ID
    When user enters first name "Veda"
    And user enters middle name "M"
    And user enters last name "Joshi"
    And system generates unique employee id
    And user clicks on Add Employee save button
    Then employee is added successfully

  @addemp
  Scenario: Add employee with user generated unique employee ID
    When user enters first name "Arya"
    And user enters middle name "M"
    And user enters last name "Agnihotri"
    And user enters employee ID "unique id"
    And user clicks on Add Employee save button
    Then employee is added successfully

  @addemp
  Scenario: Add employee without middle name
    When user enters first name "Aryan"
    And user enters last name "Pradhan"
    And user clicks on Add Employee save button
    Then employee is added successfully

  @negative  @addemp
  Scenario: Add employee with existing employee ID
    When user enters first name "Ragini"
    And user enters middle name "M"
    And user enters last name "Sharma"
    And user enters employee ID "existing employee id"
    And user clicks on Add Employee save button
    Then system displays error message "Employee Id already exists"

  @negative @addemp
  Scenario Outline: Add employee with missing mandatory fields
    When user enters first name "<firstname>"
    And user enters last name "<lastname>"
    And user clicks on Add Employee save button
    Then system displays error message "Required"

    Examples:

      | firstname | lastname |
      |           | Sharma   |
      | Ranjana   |          |
      |           |          |