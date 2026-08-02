Feature: Employee Search Management

  Background:
    Given User is on the HRM login page
    When user enters valid username and valid password as an "Admin"
    And user clicks on login button
    Then user is able to login successfully
    And user clicks on PIM option

  @searchemp @regression @smoke
  Scenario: Search for employee using unique dynamic employee ID
    When user enters the dynamically saved employee id into the ID filter field
    And user clicks on User Management Search button
    Then the system returns exactly 1 matching record row in the data grid
    And the database returns exactly 1 matching record row in the data grid

  @searchemp @regression @smoke
  Scenario: Search for employee using dynamic full name
  When user enters employee name filter as "dynamic_full_name"
  And user clicks on User Management Search button
  Then the system should display matching rows containing "dynamic_full_name" in the grid
  And the database should return matching rows in the grid



  @searchempgrid @regression
  Scenario Outline: Search for employee using flexible name variations and capitalization
    When user enters employee name filter as "<searchString>"
    And user clicks on User Management Search button
    Then the system should display matching rows containing "<expectedMatch>" in the grid

    Examples:

      | searchString              | expectedMatch             | Comment                         |
      | dynamic_first_name        | dynamic_first_name        | Valid First Name Search         |
      | dynamic_last_name         | dynamic_last_name         | Valid Last Name Search          |
      | dynamic_full_name         | dynamic_full_name         | Full Name Search                |
      | dynamic_lowercase_name    | dynamic_lowercase_name    | Lowercase Verification          |
      | dynamic_uppercase_name    | dynamic_uppercase_name    | Uppercase Verification          |
      | dynamic_partial_begin     | dynamic_partial_begin     | Partial Name Begin Match        |
      | dynamic_partial_end       | dynamic_partial_end       | Partial Name End Match          |

  @searchempnegative @regression  @searchemp
  Scenario Outline: Handle search fallback alerts when no matching employee records exist
    When user enters an invalid search parameter "<filterType>" as "<invalidValue>"
    And user clicks on User Management Search button
    Then system displays a "No Records Found" warning toast notification alert banner

    Examples:

      | filterType    | invalidValue            | Comment                         |
      | Employee ID   | 9999999                 | Non-existent Numeric ID Search  |
      | Employee Name | NonExistent EmployeeXYZ | Non-existent Name Search        |
      | Complex Combo | InvalidID123-FakeName   | Invalid ID and Name Combination |
