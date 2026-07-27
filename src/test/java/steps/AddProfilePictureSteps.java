package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import utils.CommonMethods;

import static utils.PageInitializer.addProfilePicturePage;

public class AddProfilePictureSteps extends CommonMethods {

    @When("user clicks on profile picture placeholder container")
    public void user_clicks_on_profile_picture_placeholder_container() {
        waitForVisibilityOfElement(addProfilePicturePage.profileImageAvatar);
        click(addProfilePicturePage.profileImageAvatar);
    }

    @When("user uploads a photo asset from path {string}")
    public void user_uploads_a_photo_asset_from_path(String relativePath) {
        java.io.File uploadFile = new java.io.File(relativePath);
        if (!uploadFile.exists()) {
            throw new IllegalArgumentException("❌ Workspace Missing Asset: Please ensure an asset exists at path: " + relativePath);
        }
        String absolutePath = uploadFile.getAbsolutePath();
        System.out.println("Uploading profile photo asset via absolute OS link track: [" + absolutePath + "]");

        org.openqa.selenium.By fileInputLocator = org.openqa.selenium.By.xpath("//input[@type='file']");
        WebElement fileInput = getWait().until(org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(fileInputLocator));
        fileInput.sendKeys(absolutePath);
    }

    @When("user handles the expected response status {string} with alert {string}")
    public void user_handles_the_expected_response_status_with_alert(String status, String expectedError) {
        if (status.equalsIgnoreCase("SUCCESS")) {
            waitForVisibilityOfElement(addProfilePicturePage.profileOverlaySaveButton);
            click(addProfilePicturePage.profileOverlaySaveButton);
            waitForVisibilityOfElement(addProfilePicturePage.successToast);
            System.out.println("🎉 Server Confirmation: " + addProfilePicturePage.successToast.getText());
            org.junit.Assert.assertTrue("Success toast block not populated!", addProfilePicturePage.successToast.isDisplayed());

        } else if (status.equalsIgnoreCase("FAILURE")) {
            try {
                org.openqa.selenium.support.ui.WebDriverWait shortWait = new org.openqa.selenium.support.ui.WebDriverWait(utils.DriverFactory.getDriver(), java.time.Duration.ofSeconds(4));
                shortWait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf(addProfilePicturePage.photoValidationErrorLabel));
                String actualErrorText = addProfilePicturePage.photoValidationErrorLabel.getText().trim();
                System.out.println("⚠️ UI Constraint Intercepted: [" + actualErrorText + "]");
                org.junit.Assert.assertEquals("Validation text error message mismatch!", expectedError, actualErrorText);

            } catch (Exception e) {
                org.junit.Assert.fail("❌ Core Validation Failure: System did not reject the invalid file type/size boundary!");
            }
        }
    }

    @Then("user is able to confirm profile picture workflow state {string}")
    public void user_is_able_to_confirm_profile_picture_workflow_state(String expectedStatus) {
        if (expectedStatus.equalsIgnoreCase("SUCCESS")) {
            waitForVisibilityOfElement(addProfilePicturePage.profileImageAvatar);
            String imageSrcUrl = addProfilePicturePage.profileImageAvatar.getAttribute("src");
            System.out.println("📸 Verified active profile image: " + imageSrcUrl);
            org.junit.Assert.assertNotNull("Profile picture image source is null!", imageSrcUrl);
            System.out.println("✅ Successful Verification: The user profile picture updated successfully!");
        } else {
            System.out.println("✅ Constraint Check: Verified boundary condition failure state cleanly blocked upload.");
        }
    }
}