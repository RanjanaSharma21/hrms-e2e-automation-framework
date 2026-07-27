package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.DashboardPage;
import utils.CommonMethods;
import utils.DriverFactory;

import java.time.Duration;
import static utils.PageInitializer.dashboardPage;
import static utils.PageInitializer.updatePersonalDetailPage;

public class UpdatePersonalDetailSteps extends CommonMethods {

    @When("user clicks on My Info option")
    public void user_clicks_on_my_info_option() {
        waitForVisibilityOfElement(dashboardPage.dashboardHeader);
        click(dashboardPage.dashboardPageMyInfo);
    }

    @When("user enters firstname {string}")
    public void user_enters_firstname(String firstName) {
        setValue(updatePersonalDetailPage.personalDetailFirstName, firstName);
    }

    @When("user enters middlename {string}")
    public void user_enters_middlename(String middleName) {
        setValue(updatePersonalDetailPage.personalDetailMiddleName, middleName);
    }

    @When("user enters lastname {string}")
    public void user_enters_lastname(String lastName) {
        setValue(updatePersonalDetailPage.personalDetailLastName, lastName);
    }

    @When("user selects nationality as {string}")
    public void user_selects_nationality_as(String targetNationality) {
        selectCustomDropdownValue(updatePersonalDetailPage.personalDetailNationality, targetNationality);
    }

    @When("user selects marital status as {string}")
    public void user_selects_marital_status_as(String targetMaritalStatus) {
        selectCustomDropdownValue(updatePersonalDetailPage.personalDetailMaritalStatus, targetMaritalStatus);
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
    public void user_clicks_on_save_button() {
        WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(updatePersonalDetailPage.personalDetailsSaveButton)).click();
    }

    @Then("user is able to update personal details successfully")
    public void user_is_able_to_update_personal_details_successfully() {
        waitForVisibilityOfElement(updatePersonalDetailPage.toastMessageWrapper);
        String toastText = updatePersonalDetailPage.toastMessageWrapper.getText();
        Assert.assertTrue("Update verification failed!", toastText.contains("Successfully Updated"));
    }
}
