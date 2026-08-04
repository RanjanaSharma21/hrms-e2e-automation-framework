package pages.ui;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class LoginPage extends BasePage {

    @FindBy(name = "username")
    public WebElement username;

    @FindBy(name = "password")
    public WebElement password;

    @FindBy(css = "button[type='submit']")
    public WebElement submitButton;

    @FindBy(xpath = "//*[@class='oxd-text oxd-text--p oxd-alert-content-text']")
    public WebElement alertMessage;

    @FindBy(xpath = "//span[contains(@class,'oxd-input-field-error-message') and normalize-space()='Required']")
    public List<WebElement> requiredMessages;

    @FindBy(xpath = "//p[contains(@class,'oxd-alert-content-text')]")
    public WebElement invalidCredentialMessage;


    //public String getErrorMessage() {
    //  if(alertMessage.isDisplayed()) {
    //    return alertMessage.getText();
    // }
    //return requiredMessages.getFirst().getText();
   // }






}
