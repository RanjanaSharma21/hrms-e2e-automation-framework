package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UpdateContactDetailPage extends BasePage {

    @FindBy(xpath = "//a[text()='Contact Details']")
    public WebElement contactDetailsSidebar;

    // Contact Details Input fields
    @FindBy(xpath = "//label[text()='Street 1']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    public WebElement street1Field;

    @FindBy(xpath = "//label[text()='Street 2']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    public WebElement street2Field;

    @FindBy(xpath = "//label[text()='City']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    public WebElement cityField;

    @FindBy(xpath = "//label[text()='State/Province']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    public WebElement stateProvinceField;

    @FindBy(xpath = "//label[text()='Zip/Postal Code']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    public WebElement zipPostalCodeField;

    // Country Select Wrapper Element
    @FindBy(xpath = "//div[contains(@class,'oxd-select-wrapper')]")
    public WebElement countryDropdownWrapper;

    // Telephone Mappings
    @FindBy(xpath = "//label[text()='Home']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    public WebElement homePhoneField;

    @FindBy(xpath = "//label[text()='Mobile']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    public WebElement mobilePhoneField;

    @FindBy(xpath = "//label[text()='Work']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    public WebElement workPhoneField;

    // Email Mappings
    @FindBy(xpath = "//label[text()='Work Email']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    public WebElement workEmailField;

    @FindBy(xpath = "//label[text()='Other Email']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    public WebElement otherEmailField;

    @FindBy(xpath = "//div[@class='oxd-form-actions']//button[@type='submit']")
    public WebElement contactSaveButton;

    @FindBy(xpath = "//div[contains(@class,'oxd-toast')]")
    public WebElement successToast;

    // Dynamic country element helper selector mapping strategy
    public org.openqa.selenium.WebElement getCountryOptionElement(String countryName) {
        String xpath = String.format("//div[@role='option']//span[text()='%s']", countryName);
        return utils.DriverFactory.getDriver().findElement(org.openqa.selenium.By.xpath(xpath));
    }
}