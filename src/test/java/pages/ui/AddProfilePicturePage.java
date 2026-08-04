package pages.ui;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddProfilePicturePage extends BasePage {

    @FindBy(xpath = "//div[contains(@class,'orangehrm-edit-employee-image')]//img")
    public WebElement profileImageAvatarWrapper;

    // Hidden native OS file bridge input element container
    @FindBy(xpath = "//input[@type='file']")
    public WebElement nativeFileInputElement;

    // Modal save execution option click button
    //@FindBy(xpath = "//div[contains(@class, 'orangehrm-edit-employee-image')]//button[@type='submit']")
    //public WebElement profileOverlaySaveButton;
    @FindBy(xpath = "//button[@type='submit' and normalize-space()='Save']")
    public WebElement profileOverlaySaveButton;

    // Inline formatting/size error notification label element tag
    @FindBy(xpath = "//span[contains(@class,'oxd-input-field-error-message')]")
    public WebElement photoValidationErrorLabel;

    @FindBy(xpath = "//div[contains(@class,'oxd-toast--success')]")
    public WebElement successToast;

    @FindBy(xpath = "//div[contains(@class,'orangehrm-edit-employee-image')]//img")
    public WebElement displayedProfileAvatarImage;

    @FindBy(xpath = "//div[contains(@class,'orangehrm-edit-employee-image')]//img")
    public WebElement profileImageAvatar;

}
