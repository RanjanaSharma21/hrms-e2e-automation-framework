package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import utils.CommonMethods;
import utils.ConfigReader;

import static utils.PageInitializer.loginPage;

public class LoginSteps extends CommonMethods {

    @Given("User is on the HRM login page")
    public void user_is_on_the_hrm_login_page() {
        Assert.assertTrue(loginPage.username.isDisplayed());
    }
    @When("user enters valid username and valid password")
    public void user_enters_valid_username_and_valid_password() {
        setValue(loginPage.username, ConfigReader.getProperty("username"));
        setValue(loginPage.password, ConfigReader.getProperty("password"));
    }
    @When("user clicks on login button")
    public void user_clicks_on_login_button() {
        click(loginPage.submitButton);
    }
    @Then("user is able to login successfully")
    public void user_is_able_to_login_successfully() {
        Assert.assertTrue(dashboardPage.dashboardHeader.isDisplayed());
        //String actualTitle = dashboardPage.getHeaderTitle();
        //Assert.assertEquals("Dashboard title mismatch!", "Dashboard", actualTitle);
    }
    @When("user enters username {string} and password {string}")
    public void user_enters_username_and_password(String username, String password) {
        setValue(loginPage.username, username);
        setValue(loginPage.password, password);
    }
    @Then("system displays error message {string}")
    public void system_displays_error_message(String expectedMessage) {
        getValidationMessage(expectedMessage);
    }

}
