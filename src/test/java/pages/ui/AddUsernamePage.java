package pages.ui;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.ui.DriverFactory;

public class AddUsernamePage extends BasePage {

    @FindBy(xpath = "//span[text()='Admin']")
    public WebElement adminMenu;

    @FindBy(xpath = "//button[normalize-space()='Add']")
    public WebElement addButton;

    @FindBy(xpath = "(//div[contains(@class,'oxd-select-text-input')])[1]")
    public WebElement userRoleDropdown;

    @FindBy(xpath = "//div[@role='option']//span[text()='ESS']")
    public WebElement essOption;

    @FindBy(xpath = "//input[@placeholder='Type for hints...']")
    public WebElement employeeNameField;

    /*@FindBy(xpath = "//div[@role='option']//span[normalize-space()='Automation Hamid']")
    public WebElement firstEmployeeOption;*/

    @FindBy(xpath = "(//div[contains(@class,'oxd-select-text-input')])[2]")
    public WebElement statusDropdown;

    @FindBy(xpath = "//div[@role='option']//span[text()='Enabled']")
    public WebElement enabledOption;

    @FindBy(xpath = "(//input[@class='oxd-input oxd-input--active'])[2]")
    public WebElement usernameField;

    @FindBy(xpath = "(//input[@type='password'])[1]")
    public WebElement passwordField;

    @FindBy(xpath = "(//input[@type='password'])[2]")
    public WebElement confirmPasswordField;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement saveButton;

    @FindBy(xpath = "//h5[normalize-space()='System Users']")
    public WebElement systemUsersHeader;

    @FindBy(xpath = "//label[text()='User Role']/ancestor::div[contains(@class,'oxd-input-group')]//span")
    public WebElement userRoleErrorTag;

    @FindBy(xpath = "//label[text()='Status']/ancestor::div[contains(@class,'oxd-input-group')]//span")
    public WebElement statusErrorTag;

    @FindBy(xpath = "//label[text()='Employee Name']/ancestor::div[contains(@class,'oxd-input-group')]//span")
    public WebElement employeeNameErrorTag;

    @FindBy(xpath = "//label[text()='Username']/ancestor::div[contains(@class,'oxd-input-group')]//span")
    public WebElement usernameErrorTag;

    @FindBy(xpath = "//label[text()='Password']/ancestor::div[contains(@class,'oxd-input-group')]//span")
    public WebElement passwordErrorTag;

    @FindBy(xpath = "//div[contains(@class,'oxd-toast') or contains(@id,'oxd-toast')]")
    public WebElement searchWarningToastAlert;

    public WebElement getDynamicDropdownOption(String employeeName) {
        String xpathExpression = String.format("//div[@role='option']//span[normalize-space()='%s']", employeeName);
        return DriverFactory.getDriver().findElement(org.openqa.selenium.By.xpath(xpathExpression));
    }
}