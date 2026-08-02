package steps;

import database.EmployeeDB;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.*;

import java.sql.ResultSet;
import java.time.Duration;
import java.util.Objects;

public class AddEmployeeSteps extends CommonMethods {

    @When("user clicks on PIM option")
    public void user_clicks_on_pim_option() {
        waitForVisibilityOfElement(dashboardPage.dashboardHeader);
        click(dashboardPage.dashboardPagePim);
    }

    @When("user clicks on Add Employee option")
    public void user_clicks_on_add_employee_option() {
        click(addEmployeePage.addEmployeeTabOption);
    }

    @When("user enters first name {string}")
    public void user_enters_first_name(String firstName) {
        //firstName = firstName + String.valueOf(System.currentTimeMillis()).substring(8);
        setValue(addEmployeePage.addEmployeeFirstName, firstName);
        //TestData.firstName = addEmployeePage.addEmployeeFirstName.getAttribute("value");

    }

    @When("user enters middle name {string}")
    public void user_enters_middle_name(String middleName) {
        setValue(addEmployeePage.addEmployeeMiddleName, middleName);
        //TestData.middleName = addEmployeePage.addEmployeeMiddleName.getAttribute("value");
    }

    @When("user enters last name {string}")
    public void user_enters_last_name(String lastName) {
        setValue(addEmployeePage.addEmployeeLastName, lastName);
        //TestData.lastName = addEmployeePage.addEmployeeLastName.getAttribute("value");
    }

    @When("system generates unique employee id")
    public void system_generates_unique_employee_id() {
        waitForVisibilityOfElement(addEmployeePage.addEmployeeEmployeeId);
        getEmpTestData();
        //TestData.employeeId = addEmployeePage.addEmployeeEmployeeId.getAttribute("value");
    }

    @When("user enters employee ID {string}")
    public void user_enters_employee_id(String empid) {
        if (empid.equalsIgnoreCase("unique id")) {
            String uniqueEmployeeId = "99" + String.valueOf(System.currentTimeMillis()).substring(8);
            setValue(addEmployeePage.addEmployeeEmployeeId, uniqueEmployeeId);
        }
        else if  (empid.equalsIgnoreCase("existing employee id")) {
            setValue(addEmployeePage.addEmployeeEmployeeId, TestData.employeeId);
        }
    }

    @When("user clicks on Add Employee save button")
    public void user_clicks_on_save_button() {
        org.openqa.selenium.support.ui.WebDriverWait wait =
                new org.openqa.selenium.support.ui.WebDriverWait(utils.DriverFactory.getDriver(), java.time.Duration.ofSeconds(10));
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(addEmployeePage.addEmployeeSaveButton)).click();
    }

    @Then("employee is added in the application successfully")
    public void employee_is_added_in_the_application_successfully() {
        new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(Constants.EXPLICIT_WAIT))
                .until(ExpectedConditions.urlContains("viewPersonalDetails"));
        Assert.assertTrue(Objects.requireNonNull(DriverFactory.getDriver().getCurrentUrl()).contains("viewPersonalDetails"));
    }

    @Then("employee is added in the database successfully")
    public void employee_is_added_in_the_database_successfully() throws Exception {
        DBUtility.connect();
        ResultSet rs = EmployeeDB.getEmployee(TestData.employeeId);
        Assert.assertTrue(rs.next());
        Assert.assertEquals(TestData.firstName,
                rs.getString("emp_firstname"));
        Assert.assertEquals(TestData.middleName,
                rs.getString("emp_middle_name"));
        Assert.assertEquals(TestData.lastName,
                rs.getString("emp_lastname"));
        DBUtility.close();
    }

    @Then("system displays error message {string}")
    public void system_displays_error_message(String expectedErrorMessage) {
        getValidationMessage(expectedErrorMessage);
    }

    public void getEmpTestData() {
        TestData.employeeId = addEmployeePage.addEmployeeEmployeeId.getAttribute("value");
        TestData.firstName = addEmployeePage.addEmployeeFirstName.getAttribute("value");
        TestData.middleName = addEmployeePage.addEmployeeMiddleName.getAttribute("value");
        TestData.lastName = addEmployeePage.addEmployeeLastName.getAttribute("value");
    }
}
