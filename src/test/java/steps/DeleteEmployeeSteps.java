package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
//import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverFactory;
import utils.CommonMethods;
import utils.Constants;
import utils.DriverFactory;
import utils.TestData;
import org.testng.Assert;


import java.time.Duration;
import java.util.Objects;

import static utils.PageInitializer.addEmployeePage;
import static utils.PageInitializer.deleteEmployeePage;

public class DeleteEmployeeSteps extends CommonMethods {

    @When("user clicks on delete button")
    public void user_clicks_on_delete_button() {
        org.openqa.selenium.support.ui.WebDriverWait wait =
                new org.openqa.selenium.support.ui.WebDriverWait(utils.DriverFactory.getDriver(), java.time.Duration.ofSeconds(10));
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(deleteEmployeePage.deleteButton)).click();
    }
    @When("user confirms employee deletion")
    public void user_confirms_employee_deletion() {
        org.openqa.selenium.support.ui.WebDriverWait wait =
                new org.openqa.selenium.support.ui.WebDriverWait(utils.DriverFactory.getDriver(), java.time.Duration.ofSeconds(10));
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(deleteEmployeePage.yesDeleteConfirmation)).click();
    }
    @Then("employee is deleted successfully from the application")
    public void employee_is_deleted_successfully_from_the_application() {

        Assert.assertEquals(
                deleteEmployeePage.employeeRows.size(),
                0,
                "Employee was not deleted. Employee row is still displayed."
        );

        /*WebDriverWait wait = new WebDriverWait(
                DriverFactory.getDriver(),
                Duration.ofSeconds(10)
        );
        //wait.until(
          //      ExpectedConditions.invisibilityOf(
            //            deleteEmployeePage.employeeRow
              //  )
        //);

        wait.until(
                ExpectedConditions.visibilityOf(
                        deleteEmployeePage.successMessage
                )
        );
        Assert.assertTrue(
                deleteEmployeePage.successMessage.isDisplayed(),
                "Success message was not displayed after employee deletion."
        );

       WebElement toast = wait.until(
                ExpectedConditions.visibilityOf(
                        deleteEmployeePage.toastMessageWrapper
                )
        );
        Assert.assertTrue(
                toast.isDisplayed(),
                "Success message was not displayed after employee deletion."
        );

        // 3. Now wait for the employee row to disappear
        wait.until(
                ExpectedConditions.invisibilityOf(
                        deleteEmployeePage.employeeRow
                )
        );

        // 4. Verify no employee row remains
        Assert.assertEquals(
                deleteEmployeePage.employeeRows.size(),
                0,
                "Employee was not deleted. Employee row is still displayed."
        );
        wait.until(
                ExpectedConditions.invisibilityOf(deleteEmployeePage.employeeRow)
        );
        Assert.assertEquals(
                deleteEmployeePage.employeeRows.size(),
                0,
                "Employee was not deleted. Employee row is still displayed."
        );
        //wait.until(
        //        ExpectedConditions.visibilityOf(deleteEmployeePage.successMessage)
        //);
        wait.until(
                ExpectedConditions.visibilityOf(deleteEmployeePage.toastMessageWrapper)
        );
        Assert.assertTrue(
                deleteEmployeePage.toastMessageWrapper.isDisplayed(),
                "Success message was not displayed after employee deletion."
        );*/
    }
    @Then("employee should not exist in the database")
    public void employee_should_not_exist_in_the_database() {

    }






}
