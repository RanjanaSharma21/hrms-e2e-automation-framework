package steps.ui;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import utils.ui.CommonMethods;
import utils.ui.TestData;

public class LoginSteps extends CommonMethods {

    @Given("User is on the HRM login page")
    public void user_is_on_the_hrm_login_page() {
        waitForVisibilityOfElement(loginPage.username);
        Assert.assertTrue(loginPage.username.isDisplayed());
    }

    @When("user enters valid username and valid password as an {string}")
    public void user_enters_valid_username_and_valid_password_as_role(String role) {

        if (role.equalsIgnoreCase("Admin")) {
            setValue(loginPage.username, utils.common.ConfigReader.getProperty("username"));
            setValue(loginPage.password, utils.common.ConfigReader.getProperty("password"));
        } else if (role.equalsIgnoreCase("ESS")) {
            String essUsername = TestData.essUsername;
            String essPassword = TestData.essPassword;

            if ((TestData.essUsername == null || TestData.essUsername.isEmpty()) ||
                    (TestData.essPassword == null || TestData.essPassword.isEmpty()))
            {
                System.out.println("⚠️ Warning: No dynamic user found in memory context! Using a valid hardcoded fallback account instead.");
                essUsername = "Ranjana.Sharma";
                essPassword = "SyntaxUser@2026";
            }
            setValue(loginPage.username, TestData.essUsername);
            setValue(loginPage.password, TestData.essPassword);
        }
    }

    @When("user clicks on login button")
    public void user_clicks_on_login_button() {
        click(loginPage.submitButton);
    }

    @Then("user is able to login successfully")
    public void user_is_able_to_login_successfully() {
        waitForVisibilityOfElement(dashboardPage.dashboardHeader);
        Assert.assertTrue(dashboardPage.dashboardHeader.isDisplayed());
    }

    @When("user enters username {string} and password {string}")
    public void user_enters_username_and_password(String username, String password) {
        setValue(loginPage.username, username);
        setValue(loginPage.password, password);
    }

    /*@Then("system displays error message {string}")
    public void system_displays_error_message(String expectedMessage) {
        String actualMessage = null;

        if (expectedMessage.equalsIgnoreCase("Required")) {
            waitForVisibilityOfElement(loginPage.requiredMessages.getFirst());
            actualMessage = loginPage.requiredMessages.getFirst().getText();
            org.junit.Assert.assertEquals("Login validation mismatch!", expectedMessage, actualMessage);

        } else if (expectedMessage.equalsIgnoreCase("Invalid credentials")) {
            waitForVisibilityOfElement(loginPage.invalidCredentialMessage);
            actualMessage = loginPage.invalidCredentialMessage.getText();
            org.junit.Assert.assertEquals("Login validation mismatch!", expectedMessage, actualMessage);
        }
    }*/
}