Feature: Admin User Login for HRMS Application

  Background:
    Given User is on the HRM login page

  @login
  Scenario: User can login with valid credentials
    When user enters valid username and valid password
    And user clicks on login button
    Then user is able to login successfully

  @login
  Scenario Outline: User cannot login with invalid credentials
    When user enters username "<username>" and password "<password>"
    And user clicks on login button
    Then system displays error message "<error>"

    Examples:

      | username      | password       | error               |
      |               | Hrm_user@123   | Required            |
      | hrm_user      |                | Required            |
      | wrongUsername | Hrm_user@123   | Invalid credentials |
      | hrm_user      | wrongPassword  | Invalid credentials |
      | wrongUsername | wrongPassword  | Invalid credentials |
