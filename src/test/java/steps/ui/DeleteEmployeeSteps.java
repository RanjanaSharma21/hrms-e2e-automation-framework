package steps.ui;

import database.DBUtility;
import database.EmployeeDB;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import utils.ui.CommonMethods;
import utils.ui.DriverFactory;
import utils.ui.TestData;

public class DeleteEmployeeSteps extends CommonMethods {

    @When("user clicks on delete button")
    public void user_clicks_on_delete_button() {
        org.openqa.selenium.support.ui.WebDriverWait wait =
                new org.openqa.selenium.support.ui.WebDriverWait(DriverFactory.getDriver(), java.time.Duration.ofSeconds(10));
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(deleteEmployeePage.deleteButton)).click();
    }
    @When("user confirms employee deletion")
    public void user_confirms_employee_deletion() {
        org.openqa.selenium.support.ui.WebDriverWait wait =
                new org.openqa.selenium.support.ui.WebDriverWait(DriverFactory.getDriver(), java.time.Duration.ofSeconds(10));
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(deleteEmployeePage.yesDeleteConfirmation)).click();
    }
    @Then("employee is deleted successfully from the application")
    public void employee_is_deleted_successfully_from_the_application() {
        Assert.assertEquals(
                "Employee was not deleted. Employee row is still displayed.",
                0,
                deleteEmployeePage.employeeRows.size()
        );
    }
    @Then("employee should not exist in the database")
    public void employee_should_not_exist_in_the_database() throws Exception {
        DBUtility.connect();
        Assert.assertTrue(EmployeeDB.employeeDeleted(TestData.employeeId));
        DBUtility.close();
    }
}
