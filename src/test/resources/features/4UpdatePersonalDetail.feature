Feature: User updates personal details

  Background:
    Given User is on the HRM login page
    When user enters valid username and valid password as an "ESS"
    And user clicks on login button
    Then user is able to login successfully

  @addpersonal1
  Scenario: User updates personal details successfully
    When user clicks on My Info option
    And user selects nationality as "Indian"
    And user selects marital status as "Single"
    And user selects gender as "Male"
    And user enters firstname "Aryan"
    And user enters middlename "G"
    And user enters lastname "Parikh"
    And user clicks on save button
    Then user is able to update personal details in the application successfully
    And user is able to update personal details in the database successfully

  @negative
  Scenario Outline: User cannot update personal details without required fields
    When user clicks on My Info option
    When user enters firstname "<firstname>"
    And user enters lastname "<lastname>"
    And user clicks on save button
    Then system displays error message "<error>"

    Examples:

      | firstname     | lastname       | error               |
      |               | Sharma         | Required            |
      | Ranjana       |                | Required            |
      |               |                | Required            |
