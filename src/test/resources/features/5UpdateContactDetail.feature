@addcontact
Feature: User updates contact details

  Background:
    Given User is on the HRM login page
    When user enters valid username and valid password as an "ESS"
    And user clicks on login button
    Then user is able to login successfully

  Scenario: User updates contact details successfully
    When user clicks on My Info option
    And user clicks on Contact Details option
    And user enters street1 "123"
    And user enters street2 "My Street"
    And user enters city "Toronto"
    And user enters state "Ontario"
    And user enters zip "M4C 1C3"
    And user enters country "Canada"
    And user enters homeno "416-555-0199"
    And user enters mobileno "437-666-0199"
    And user enters workno "905-777-0199"
    And user enters workemail "test.engineer@syntax.com"
    And user enters otheremail "John@gmail.com"
    And user clicks on contact save button
    Then user is able to update contact details successfully




