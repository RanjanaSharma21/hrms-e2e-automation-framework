package steps;

import database.EmployeeDB;
import database.UserDB;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.*;

import java.sql.ResultSet;
import java.util.Arrays;

import java.time.Duration;
import java.util.Arrays;

public class AddUsernameSteps extends CommonMethods {

    @Then("user clicks on Admin option")
    public void user_clicks_on_admin_option() {
        waitForVisibilityOfElement(addUsernamePage.adminMenu);
        click(addUsernamePage.adminMenu);
    }
    @Then("user clicks on Add button")
    public void user_clicks_on_add_button() {
        waitForVisibilityOfElement(addUsernamePage.addButton);
        click(addUsernamePage.addButton);
    }
    @When("user inputs the dynamically saved employee name into the search field")
    public void user_inputs_the_dynamically_saved_employee_name_into_the_search_field() {
        waitForVisibilityOfElement(addUsernamePage.employeeNameField);
        if (TestData.firstName == null || TestData.firstName.isEmpty()) {
            throw new IllegalStateException("❌ State Error: " +
                    "current username is null! " +
                    "Make sure the Add Employee test saves the name before running this scenario.");
        }
        String nm = TestData.firstName + " " + TestData.middleName + " " + TestData.lastName;
        setValue(addUsernamePage.employeeNameField, nm);
    }
    @When("user selects the matching employee name from the auto-complete hints box")
    public void user_selects_the_matching_employee_name_from_the_auto_complete_hints_box() {
        org.openqa.selenium.By listboxContainerLocator = org.openqa.selenium.By.xpath("//div[@role='listbox']");
        getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(listboxContainerLocator));
        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        addUsernamePage.employeeNameField.sendKeys(org.openqa.selenium.Keys.ARROW_DOWN);
        addUsernamePage.employeeNameField.sendKeys(org.openqa.selenium.Keys.ENTER);
    }
    @When("user selects User Role as {string} from the dropdown list")
    public void user_selects_user_role_as_from_the_dropdown_list(String role) {
        if (!role.isEmpty()) {
            selectCustomDropdownValue(addUsernamePage.userRoleDropdown, role);
        }
    }
    @When("user selects Status as {string} from the dropdown list")
    public void user_selects_status_as_from_the_dropdown_list(String status) {
        if (!status.isEmpty()) {
            selectCustomDropdownValue(addUsernamePage.statusDropdown, status);
        }
    }
    @When("user enters a unique username into the form")
    public void user_enters_a_unique_username_into_the_form() {
            String processedUsername;
            processedUsername = TestData.firstName.substring(0, Math.min(3, TestData.firstName.length()))
                    + DataGenerator.uniqueUsername(ConfigReader.getProperty("username_prefix"));
            addUsernamePage.usernameField.clear();
            setValue(addUsernamePage.usernameField, processedUsername);
            TestData.essUsername = processedUsername;
    }
    @When("user sets a secure password meeting all character constraints")
    public void user_sets_a_secure_password_meeting_all_character_constraints() {
        String complexPasswordText = "SyntaxUser@2026";
        setValue(addUsernamePage.passwordField, complexPasswordText);
        setValue(addUsernamePage.confirmPasswordField, complexPasswordText);
        TestData.essPassword = complexPasswordText;
    }
    @When("user clicks on Add User Save button")
    public void user_clicks_on_add_user_save_button() {
        click(addUsernamePage.saveButton);
    }
    @Then("the user account should be searchable by username on the User Management page")
    public void the_user_account_should_be_searchable_by_username_on_the_user_management_page() {
        waitForVisibilityOfElement(addUsernamePage.systemUsersHeader);
        WebElement userManagementSearchField = DriverFactory.getDriver().findElement(
                By.xpath("//label[text()='Username']/ancestor::div[contains(@class,'oxd-input-group')]//input"));
        setValue(userManagementSearchField, TestData.essUsername);
        WebElement searchButton = DriverFactory.getDriver().findElement(
                By.xpath("//button[@type='submit' or normalize-space()='Search']"));
        click(searchButton);
        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        java.util.List<WebElement> matchingRows = DriverFactory.getDriver().findElements(By.xpath("//div[@class='oxd-table-card']"));
        Assert.assertEquals("Account system entry filter lookup mismatch!", 1, matchingRows.size());
    }
    @When("user enters Employee Name as {string}")
    public void user_enters_employee_name_as(String empName) {
        if (!empName.isEmpty())  {
            setValue(addUsernamePage.employeeNameField, empName);
            if (!empName.equalsIgnoreCase("NotExist")) {
                org.openqa.selenium.By dynamicBy = org.openqa.selenium.By.xpath(
                        String.format("//div[@role='option']//span[normalize-space()='%s']", empName)
                );
                getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(dynamicBy));
                org.openqa.selenium.WebElement targetOption = addUsernamePage.getDynamicDropdownOption(empName);
                click(targetOption);
            }
        }
    }
    @When("user enters username value {string} into the form")
    public void user_enters_username_value_into_the_form(String username) {
        if (!username.isEmpty()) {
            String processedUsername;
            if ("short".equalsIgnoreCase(username)) {
                processedUsername = DataGenerator.shortUsername();
            }
            else if (username.equalsIgnoreCase("Existing username")) {
                processedUsername = TestData.essUsername;
            }
            else {
                processedUsername = DataGenerator.uniqueUsername(username);
            }
            addUsernamePage.usernameField.clear();
            setValue(addUsernamePage.usernameField, processedUsername);
        }
    }
    @When("user enters password value {string} and confirm password {string}")
    public void user_enters_password_value_and_confirm_password(String password, String confirmPassword) {
        if (!password.isEmpty()) {
            setValue(addUsernamePage.passwordField, password);
            setValue(addUsernamePage.confirmPasswordField, confirmPassword);
        }
    }
    @Then("system displays an inline field validation message {string} under the {string} field block")
    public void system_displays_an_inline_field_validation_message_under_the_field_block(String expectedError, String targetField) throws InterruptedException {
        String actualErrorText = "";
        if (targetField.equalsIgnoreCase("User Role")) {
            waitForVisibilityOfElement(addUsernamePage.userRoleErrorTag);
            actualErrorText = addUsernamePage.userRoleErrorTag.getText().trim();
            Assert.assertEquals("Validation text mismatch!", expectedError, actualErrorText);
        } else if (targetField.equalsIgnoreCase("Status")) {
            waitForVisibilityOfElement(addUsernamePage.statusErrorTag);
            actualErrorText = addUsernamePage.statusErrorTag.getText().trim();
            Assert.assertEquals("Validation text mismatch!", expectedError, actualErrorText);
        } else if (targetField.equalsIgnoreCase("Employee Name")) {
            waitForVisibilityOfElement(addUsernamePage.employeeNameErrorTag);
            String[] allowedErrors = expectedError.split(",");
            actualErrorText = addUsernamePage.employeeNameErrorTag.getText().trim();
            Assert.assertTrue(
                    "Unexpected validation message: " + actualErrorText,
                    Arrays.asList(allowedErrors).contains(actualErrorText)
            );
        } else if ((targetField.equalsIgnoreCase("Username")) &&
                (addUsernamePage.usernameField.getText().trim().equalsIgnoreCase("Existing username"))) {
            waitForVisibilityOfElement(addUsernamePage.usernameErrorTag);
            actualErrorText = addUsernamePage.usernameErrorTag.getText().trim();
            Assert.assertEquals("Validation text mismatch!", expectedError, actualErrorText);
        } else if (targetField.equalsIgnoreCase("Password")) {
            waitForVisibilityOfElement(addUsernamePage.passwordErrorTag);
            actualErrorText = addUsernamePage.passwordErrorTag.getText().trim();
            Assert.assertEquals("Validation text mismatch!", expectedError, actualErrorText);
        }
    }

    @Then("user credential is added in the database successfully")
    public void user_credential_is_added_in_the_database_successfully() throws Exception {
        DBUtility.connect();
        ResultSet rs = UserDB.getUser(TestData.essUsername);
        Assert.assertTrue(rs.next());
        Assert.assertEquals(TestData.essUsername,
                rs.getString("user_name"));
        DBUtility.close();
    }
}