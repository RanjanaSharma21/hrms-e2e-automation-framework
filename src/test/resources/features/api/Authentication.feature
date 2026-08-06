Feature: API Authentication
@apilogin
  Scenario:
    Given user sends valid credentials
    When authentication API is called
    Then token should be generated