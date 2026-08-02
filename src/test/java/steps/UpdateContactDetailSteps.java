package steps;

import database.EmployeeDB;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import utils.*;

import java.sql.ResultSet;

public class UpdateContactDetailSteps extends CommonMethods {

    @When("user clicks on Contact Details option")
    public void user_clicks_on_contact_details_option() {
        waitForVisibilityOfElement(updateContactDetailPage.contactDetailsSidebar);
        click(updateContactDetailPage.contactDetailsSidebar);
    }

    @When("user enters street1 {string}")
    public void user_enters_street1(String street1) {
        waitForVisibilityOfElement(updateContactDetailPage.street1Field);
        setValue(updateContactDetailPage.street1Field, street1);
        TestData.street1 = street1;
    }

    @When("user enters street2 {string}")
    public void user_enters_street2(String street2) {
        waitForVisibilityOfElement(updateContactDetailPage.street2Field);
        setValue(updateContactDetailPage.street2Field, street2);
        TestData.street2 = street2;
    }

    @When("user enters city {string}")
    public void user_enters_city(String city) {
        waitForVisibilityOfElement(updateContactDetailPage.cityField);
        setValue(updateContactDetailPage.cityField, city);
        TestData.city = city;
    }

    @When("user enters state {string}")
    public void user_enters_state(String state) {
        waitForVisibilityOfElement(updateContactDetailPage.stateProvinceField);
        setValue(updateContactDetailPage.stateProvinceField, state);
        TestData.state = state;
    }

    @When("user enters zip {string}")
    public void user_enters_zip(String zip) {
        waitForVisibilityOfElement(updateContactDetailPage.zipPostalCodeField);
        setValue(updateContactDetailPage.zipPostalCodeField, zip);
        TestData.postalCode = zip;
    }

    @When("user enters country {string}")
    public void user_enters_country(String countryName) {
        click(updateContactDetailPage.countryDropdownWrapper);
        org.openqa.selenium.By optionLocator = org.openqa.selenium.By.xpath(
                String.format("//div[@role='option']//span[text()='%s']", countryName));
        getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(optionLocator));
        org.openqa.selenium.WebElement targetOption = updateContactDetailPage.getCountryOptionElement(countryName);
        click(targetOption);
    }

    @When("user enters homeno {string}")
    public void user_enters_homeno(String home) {
        waitForVisibilityOfElement(updateContactDetailPage.homePhoneField);
        setValue(updateContactDetailPage.homePhoneField, home);
    }

    @When("user enters mobileno {string}")
    public void user_enters_mobileno(String mobile) {
        waitForVisibilityOfElement(updateContactDetailPage.mobilePhoneField);
        setValue(updateContactDetailPage.mobilePhoneField, mobile);
        TestData.mobileNumber = mobile;
    }

    @When("user enters workno {string}")
    public void user_enters_workno(String work) {
        waitForVisibilityOfElement(updateContactDetailPage.workPhoneField);
        setValue(updateContactDetailPage.workPhoneField, work);
    }

    @When("user enters workemail {string}")
    public void user_enters_workemail(String workemail)  {
        waitForVisibilityOfElement(updateContactDetailPage.workEmailField);
        updateContactDetailPage.workEmailField.clear();
        String uniqueWorkEmail = DataGenerator.uniqueEmail();
        //String uniqueWorkEmail = workemail.replace("@", String.valueOf(System.currentTimeMillis()).substring(10) + "@");
        setValue(updateContactDetailPage.workEmailField, uniqueWorkEmail);
        TestData.workEmail = uniqueWorkEmail;
    }

    @When("user enters otheremail {string}")
    public void user_enters_otheremail(String otheremail) {
        waitForVisibilityOfElement(updateContactDetailPage.otherEmailField);
        updateContactDetailPage.otherEmailField.clear();
        String uniqueOtherEmail = DataGenerator.uniqueEmail();
        setValue(updateContactDetailPage.otherEmailField, uniqueOtherEmail);
    }

    @When("user clicks on contact save button")
    public void user_clicks_on_contact_save_button() {
        click(updateContactDetailPage.contactSaveButton);
        waitForVisibilityOfElement(updateContactDetailPage.successToast);
    }

    @Then("user is able to update contact details in the application successfully")
    public void user_is_able_to_update_contact_details_in_the_application_successfully() {
        //waitForVisibilityOfElement(updateContactDetailPage.successToast);
        //Assert.assertTrue("Success toast confirmation banner was not visible!", updateContactDetailPage.successToast.isDisplayed());
        Assert.assertTrue(updateContactDetailPage.successToast.getText().contains("Success"));
    }

    @Then("user is able to update contact details in the database successfully")
    public void user_is_able_to_update_contact_details_in_the_database_successfully() throws Exception{
        DBUtility.connect();
        ResultSet rs = EmployeeDB.getEmployee(TestData.employeeId);
        Assert.assertTrue(rs.next());
        Assert.assertEquals(TestData.street1,
                rs.getString("emp_street1"));
        Assert.assertEquals(TestData.street2,
                rs.getString("emp_street2"));
        Assert.assertEquals(TestData.city,
                rs.getString("city_code"));
        Assert.assertEquals(TestData.postalCode,
                rs.getString("emp_zipcode"));
        Assert.assertEquals(TestData.state,
                rs.getString("provin_code"));
        Assert.assertEquals(TestData.mobileNumber,
                rs.getString("emp_mobile"));
        Assert.assertEquals(TestData.workEmail,
                rs.getString("emp_work_email"));
        DBUtility.close();
    }
}
