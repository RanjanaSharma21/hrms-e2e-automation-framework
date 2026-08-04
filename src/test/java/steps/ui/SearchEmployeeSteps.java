package steps.ui;

import database.EmployeeDB;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ui.CommonMethods;
import database.DBUtility;
import utils.ui.DriverFactory;
import utils.ui.TestData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.time.Duration;

import static utils.ui.TestData.firstName;

public class SearchEmployeeSteps extends CommonMethods {

    @When("user enters the dynamically saved employee id into the ID filter field")
    public void user_enters_the_dynamically_saved_employee_id_into_the_id_filter_field() throws InterruptedException {
        waitForVisibilityOfElement(searchEmployeePage.searchFormEmployeeIdField);
        setValue(searchEmployeePage.searchFormEmployeeIdField, TestData.employeeId);
    }

    @When("user clicks on User Management Search button")
    public void user_clicks_on_user_management_search_button() {
        click(searchEmployeePage.userManagementSearchButton);
        getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'oxd-table-body')]")));
        getWait().until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[contains(@class,'oxd-loading-spinner')]")));

    }

    @Then("the system returns exactly {int} matching record row in the data grid")
    public void the_system_returns_exactly_matching_record_row_in_the_data_grid(Integer expectedRows) {
        By rowLocator = By.xpath("//div[contains(@class,'oxd-table-body')]//div[@role='row']");
        WebDriverWait wait = new WebDriverWait(
                DriverFactory.getDriver(),
                Duration.ofSeconds(20));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(rowLocator));
        int actualCount = DriverFactory.getDriver()
                .findElements(rowLocator)
                .size();
        Assert.assertEquals(
                "Expected exactly " + expectedRows + " matching row but found " + actualCount,
                expectedRows.intValue(),
                actualCount);
        WebElement row = DriverFactory.getDriver()
                .findElements(rowLocator)
                .get(0);
        String rowText = row.getText();
    }

    @When("user enters employee name filter as {string}")
    public void user_enters_employee_name_filter_as(String searchString) {
        waitForVisibilityOfElement(searchEmployeePage.searchFormEmployeeNameField);
        String baseFullName = TestData.firstName + " " + TestData.lastName;
        if (baseFullName == null || baseFullName.trim().isEmpty()) {
            throw new IllegalStateException("❌ State Error: CommonMethods.currentEmpName is null! Run employee generation first.");
        }
        String[] nameParts = baseFullName.trim().split("\\s+");
        String firstName = nameParts[0];
        String lastName = (nameParts.length > 1) ? nameParts[nameParts.length - 1] : firstName;
        String queryToType = "";
        switch (searchString.toLowerCase()) {
            case "dynamic_first_name":
                queryToType = firstName;
                break;
            case "dynamic_last_name":
                queryToType = lastName;
                break;
            case "dynamic_full_name":
                queryToType = nameParts.length > 1 ? firstName + " " + lastName : firstName;
                break;
            case "dynamic_lowercase_name":
                queryToType = firstName.toLowerCase();
                break;
            case "dynamic_uppercase_name":
                queryToType = firstName.toUpperCase();
                break;
            case "dynamic_partial_begin":
                queryToType = firstName.substring(0, Math.min(firstName.length(), 3));
                break;
            case "dynamic_partial_end":
                queryToType = lastName.substring(Math.max(0, lastName.length() - 4));
                break;
            default:
                queryToType = searchString;
                break;
        }
        searchEmployeePage.searchFormEmployeeNameField.sendKeys(Keys.chord(Keys.COMMAND, "a"), Keys.DELETE);
        searchEmployeePage.searchFormEmployeeNameField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        setValue(searchEmployeePage.searchFormEmployeeNameField, queryToType);
    }

    @Then("the system should display matching rows containing {string} in the grid")
    public void the_system_should_display_matching_rows_containing_in_the_grid(String expectedMatch) {
        By rowLocator = By.xpath("//div[contains(@class,'oxd-table-body')]//div[@role='row']");
        List<WebElement> rows = DriverFactory.getDriver()
                .findElements(rowLocator);
        boolean found = false;
        for (int i = 0; i < rows.size(); i++) {
            WebElement freshRow = DriverFactory.getDriver()
                    .findElements(rowLocator)
                    .get(i);
            String rowText = freshRow.getText();
            System.out.println("Checking row: " + rowText);
            if (rowText.toLowerCase().contains(firstName.toLowerCase())) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("Employee [" + firstName + "] was not found in grid", found);
    }

    @When("user enters an invalid search parameter {string} as {string}")
    public void user_enters_an_invalid_search_parameter_as(String filterType, String invalidValue) {
        searchEmployeePage.searchFormEmployeeIdField.sendKeys(Keys.chord(Keys.COMMAND, "a"), Keys.DELETE);
        searchEmployeePage.searchFormEmployeeIdField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        searchEmployeePage.searchFormEmployeeNameField.sendKeys(Keys.chord(Keys.COMMAND, "a"), Keys.DELETE);
        searchEmployeePage.searchFormEmployeeNameField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        if (filterType.equalsIgnoreCase("Employee ID")) {
            setValue(searchEmployeePage.searchFormEmployeeIdField, invalidValue);
        } else if (filterType.equalsIgnoreCase("Employee Name")) {
            setValue(searchEmployeePage.searchFormEmployeeNameField, invalidValue);
        } else if (filterType.equalsIgnoreCase("Complex Combo")) {
            setValue(searchEmployeePage.searchFormEmployeeIdField, "99999");
            setValue(searchEmployeePage.searchFormEmployeeNameField, "FakeUserXYZ");
        }
    }

    @Then("system displays a {string} warning toast notification alert banner")
    public void system_displays_a_warning_toast_notification_alert_banner(String expectedToastMessage) {
       waitForVisibilityOfElement(searchEmployeePage.noRecordsFoundMessage);
       String actual = searchEmployeePage.noRecordsFoundMessage.getText().trim();
        Assert.assertEquals("No Records Found", actual);
    }

    public String getTextWithRetry(WebElement element) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                return element.getText();
            }
            catch (StaleElementReferenceException e) {
                attempts++;
            }
        }
        throw new RuntimeException("Element remained stale after retries");
    }

    @Then("the database returns exactly {int} matching record row in the data grid")
    public void the_database_returns_exactly_matching_record_row_in_the_data_grid(Integer int1) throws Exception {
        DBUtility.connect();
        Assert.assertTrue(EmployeeDB.employeeExists(TestData.employeeId));
        DBUtility.close();
    }

    @Then("the database should return matching rows in the grid")
    public void the_database_should_return_matching_rows_in_the_grid() throws Exception {
        DBUtility.connect();
        Assert.assertTrue(EmployeeDB.getEmployeesByName(firstName, TestData.lastName));
        DBUtility.close();
    }

}