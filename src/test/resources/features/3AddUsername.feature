@adduser
Feature: Add Username and User Account Configuration

  Background:
    Given User is on the HRM login page
    When user enters valid username and valid password as an "Admin"
    And user clicks on login button
    Then user is able to login successfully
    And user clicks on Admin option
    And user clicks on Add button

  @adduser1
  Scenario: Successfully create linked ESS account using dynamic parameters
    When user inputs the dynamically saved employee name into the search field
    And user selects the matching employee name from the auto-complete hints box
    And user selects User Role as "ESS" from the dropdown list
    And user selects Status as "Enabled" from the dropdown list
    And user enters a unique username into the form
    And user sets a secure password meeting all character constraints
    And user clicks on Add User Save button
    Then the user account should be searchable by username on the User Management page

   @negative   @adduser1
  Scenario Outline: Verify constraint validations for form fields
    When user enters Employee Name as "<empName>"
    And user selects User Role as "<role>" from the dropdown list
    And user selects Status as "<status>" from the dropdown list
    And user enters username value "<username>" into the form
    And user enters password value "<password>" and confirm password "<confirmPass>"
    And user clicks on Add User Save button
    Then system displays an inline field validation message "<expectedError>" under the "<targetField>" field block

    Examples:

      | role  | empName              | status   | username         | password        | confirmPass     | expectedError                   | targetField   |
      |       | Ranjana M Sharma     | Enabled  | ess_user26       | SyntaxUser@2026 | SyntaxUser@2026 | Required                        | User Role     |
      | ESS   | Ranjana M Sharma     |          | ess_user26       | SyntaxUser@2026 | SyntaxUser@2026 | Required                        | Status        |
      | ESS   |                      | Enabled  | ess_user26       | SyntaxUser@2026 | SyntaxUser@2026 | Required                        | Employee Name |
      | ESS   | Ranjana M Sharma     | Enabled  | ess_user26       |                 |                 | Required                        | Password      |
      | ESS   | Ranjana M Sharma     | Enabled  | short            | SyntaxUser@2026 | SyntaxUser@2026 | Should be at least 5 characters | Username      |
      | ESS   | NotExist             | Enabled  | ess_user26       | SyntaxUser@2026 | SyntaxUser@2026 | Invalid,No Records Found                | Employee Name |
      | ESS   | Ranjana M Sharma     | Enabled  | Existing username| SyntaxUser@2026 | SyntaxUser@2026 | Already exists                  | Username      |


