@addprofile
Feature: User updates profile picture

  Background:
    Given User is on the HRM login page
    When user enters valid username and valid password as an "ESS"
    And user clicks on login button
    Then user is able to login successfully

  Scenario Outline:: User uploads profile picture with valid and invalid files
    When user clicks on My Info option
    And user clicks on profile picture placeholder container
    And user uploads a photo asset from path "<filePath>"
    And user handles the expected response status "<expectedStatus>" with alert "<errorMessage>"
    Then user is able to confirm profile picture workflow state "<expectedStatus>"

    Examples:

      | filePath                                     | expectedStatus | errorMessage                        |
      | src/test/resources/fixtures/avatar_valid.png | SUCCESS        | None                                |
      | src/test/resources/fixtures/large_file.jpeg  | FAILURE        | Attachment Size Exceeded            |
      | src/test/resources/fixtures/invalid_doc.pdf  | FAILURE        | File type not allowed               |
