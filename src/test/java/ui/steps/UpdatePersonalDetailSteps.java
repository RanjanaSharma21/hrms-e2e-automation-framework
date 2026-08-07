package ui.steps;

import database.EmployeeDB;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.utils.CommonMethods;
import database.DBUtility;
import ui.utils.DataGenerator;
import ui.utils.DriverFactory;
import ui.utils.TestData;

import java.sql.ResultSet;
import java.time.Duration;



public class UpdatePersonalDetailSteps extends CommonMethods {

    @When("user clicks on My Info option")
    public void user_clicks_on_my_info_option() {
        waitForVisibilityOfElement(dashboardPage.dashboardHeader);
        click(dashboardPage.dashboardPageMyInfo);
    }

    @When("user enters firstname {string}")
    public void user_enters_firstname(String firstName) {
        setValue(updatePersonalDetailPage.personalDetailFirstName, DataGenerator.uniqueFirstName(firstName));
        //setReactValue(updatePersonalDetailPage.personalDetailFirstName, firstName);
        TestData.firstName = updatePersonalDetailPage.personalDetailFirstName.getAttribute("value");
    }

    @When("user enters middlename {string}")
    public void user_enters_middlename(String middleName) {
        setValue(updatePersonalDetailPage.personalDetailMiddleName, middleName);
        TestData.middleName = middleName;
    }

    @When("user enters lastname {string}")
    public void user_enters_lastname(String lastName) {
        setValue(updatePersonalDetailPage.personalDetailLastName, DataGenerator.uniqueFirstName(lastName));
        TestData.lastName = updatePersonalDetailPage.personalDetailLastName.getAttribute("value");
    }

    @When("user selects nationality as {string}")
    public void user_selects_nationality_as(String targetNationality) {
        selectCustomDropdownValue(updatePersonalDetailPage.personalDetailNationality, targetNationality);
    }

    @When("user selects marital status as {string}")
    public void user_selects_marital_status_as(String targetMaritalStatus) {
        selectCustomDropdownValue(updatePersonalDetailPage.personalDetailMaritalStatus, targetMaritalStatus);
        TestData.maritalStatus = targetMaritalStatus;
    }

    @When("user selects gender as {string}")
    public void user_selects_gender_as(String gender) {

        if (gender.equalsIgnoreCase("Male")) {
            click(updatePersonalDetailPage.personalDetailMale);
            Assert.assertTrue("Male radio selection was not verified!",
                    updatePersonalDetailPage.personalDetailMale.isDisplayed());
        } else if (gender.equalsIgnoreCase("Female")) {
            click(updatePersonalDetailPage.personalDetailFemale);
            Assert.assertTrue("Female radio selection was not verified!",
                    updatePersonalDetailPage.personalDetailFemale.isDisplayed());
        }
        else {
            throw new IllegalArgumentException("Unknown gender value parsed to script: " + gender);
        }
    }

    @When("user clicks on save button")
    public void user_clicks_on_save_button() throws InterruptedException {
        /*WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10));
        //wait.until(ExpectedConditions.elementToBeClickable(updatePersonalDetailPage.personalDetailsSaveButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(updatePersonalDetailPage.personalDetailsSaveButton));
        ((JavascriptExecutor) DriverFactory.getDriver())
                .executeScript("arguments[0].scrollIntoView(true);", updatePersonalDetailPage.personalDetailsSaveButton);

        //updatePersonalDetailPage.personalDetailsSaveButton.click();*/
        WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10));

        WebElement saveButton = wait.until(
                ExpectedConditions.visibilityOf(updatePersonalDetailPage.personalDetailsSaveButton));

        ((JavascriptExecutor) DriverFactory.getDriver())
                .executeScript("arguments[0].scrollIntoView({block:'center'});", saveButton);

        ((JavascriptExecutor) DriverFactory.getDriver())
                .executeScript("arguments[0].click();", saveButton);
    }

    @Then("user is able to update personal details in the application successfully")
    public void user_is_able_to_update_personal_details_in_the_application_successfully() {
        waitForVisibilityOfElement(updatePersonalDetailPage.toastMessageWrapper);
        String toastText = updatePersonalDetailPage.toastMessageWrapper.getText();
        Assert.assertTrue("Update verification failed!", toastText.contains("Successfully Updated"));
        /*By toast = By.xpath("//div[contains(@class,'oxd-toast')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(toast));
        String toastText = DriverFactory.getDriver()
                .findElement(toast)
                .getText();
        System.out.println("Toast message = " + toastText);
        Assert.assertTrue(toastText.contains("Successfully"));*/
    }

    @Then("user is able to update personal details in the database successfully")
    public void user_is_able_to_update_personal_details_in_the_database_successfully() throws Exception{
        DBUtility.connect();
        ResultSet rs = EmployeeDB.getEmployee(TestData.employeeId);
        Assert.assertTrue(rs.next());
        Assert.assertEquals(TestData.employeeId, rs.getString("employee_id"));
        Assert.assertEquals(TestData.firstName, rs.getString("emp_firstname"));
        Assert.assertEquals(TestData.middleName, rs.getString("emp_middle_name"));
        Assert.assertEquals(TestData.lastName, rs.getString("emp_lastname"));
        Assert.assertEquals(TestData.maritalStatus, rs.getString("emp_marital_status"));
        DBUtility.close();
    }
}
