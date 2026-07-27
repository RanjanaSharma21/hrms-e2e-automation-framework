package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UpdatePersonalDetailPage extends BasePage {

    @FindBy(xpath = "//a[contains(@class, 'oxd-main-menu-item') and contains(@href, 'viewMyDetails') and .//span[text()='My Info']]")
    public WebElement personalDetailMyInfo;

    @FindBy(xpath = "//input[@name='firstName' and @placeholder='First Name']")
    public WebElement personalDetailFirstName;

    @FindBy(xpath = "//input[@name='middleName' and @placeholder='Middle Name']")
    public WebElement personalDetailMiddleName;

    @FindBy(xpath = "//input[@name='lastName' and @placeholder='Last Name']")
    public WebElement personalDetailLastName;

    @FindBy(xpath = "//label[text()='Nationality']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text') and @tabindex='0']")
    public WebElement personalDetailNationality;

    @FindBy(xpath = "//label[text()='Marital Status']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text') and @tabindex='0']")
    public WebElement personalDetailMaritalStatus;

    @FindBy(xpath = "//div[@role='listbox']")
    public WebElement personalDetailListBox;

    @FindBy(xpath = "//a[text()='Contact Details' and contains(@class, 'orangehrm-tabs-item') and contains(@href, 'viewContactDetails')]")
    public WebElement personalDetailContactDetailsSubMenuLink;

    @FindBy(xpath = "//label[contains(.,'Female')]//span")
    public WebElement personalDetailFemale;

    @FindBy(xpath = "//label[contains(.,'Male')]//span")
    public WebElement personalDetailMale;

    @FindBy(xpath = "//h6[text()='Personal Details']/following-sibling::form//button[@type='submit']")
    public WebElement personalDetailsSaveButton;

    @FindBy(xpath = "//div[contains(@class, 'oxd-toast')]")
    public WebElement toastMessageWrapper;
}
