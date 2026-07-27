Feature: User Login for HRMS Application

  Background:
    Given User is on the HRM login page

  @adminlogin
  Scenario: Admin user can login with valid credentials
    When user enters valid username and valid password as an "Admin"
    And user clicks on login button
    Then user is able to login successfully

  @esslogin
  Scenario: ESS user can login with valid credentials
    When user enters valid username and valid password as an "ESS"
    And user clicks on login button
    Then user is able to login successfully

  @negativelogin
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
