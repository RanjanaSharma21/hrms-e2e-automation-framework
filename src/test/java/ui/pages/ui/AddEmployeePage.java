package ui.pages.ui;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ui.utils.DriverFactory;
import java.util.List;

public class AddEmployeePage extends BasePage {

    @FindBy(xpath = "//a[normalize-space()='Add Employee']")
    public WebElement addEmployeeTabOption; // Aligned with step file

    @FindBy(xpath = "//*[@name='firstName']")
    public WebElement addEmployeeFirstName; // Aligned with step file

    @FindBy(xpath = "//*[@name='middleName']")
    public WebElement addEmployeeMiddleName; // Aligned with step file

    @FindBy(xpath = "//*[@name='lastName']")
    public WebElement addEmployeeLastName; // Aligned with step file

    @FindBy(xpath = "//label[text()='Employee Id']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    public WebElement addEmployeeEmployeeId; // Aligned with step file

    @FindBy(xpath = "//*[@class='oxd-button oxd-button--medium oxd-button--secondary orangehrm-left-space']")
    public WebElement addEmployeeSaveButton; // Aligned with step file

    @FindBy(xpath = "//span[contains(@class, 'oxd-input-field-error-message') or contains(@class, 'oxd-input-group__message')]")
    public WebElement fieldInputErrorTag; // Aligned with step file

    @FindBy(xpath = "(//span[contains(@class, 'oxd-input-field-error-message') and text()='Required'])[1]")
    public WebElement addEmployeeRequiredfnm;

    @FindBy(xpath = "(//span[contains(@class, 'oxd-input-field-error-message') and text()='Required'])[2]")
    public WebElement addEmployeeRequiredlnm;

    @FindBy(xpath = "//span[contains(@class, 'oxd-input-field-error-message') or contains(@class, 'oxd-input-group__message')]")
    public List<WebElement> addEmployeeRequiredMessage;

    @FindBy(xpath = "//div[contains(.,'Employee Full Name')]//div[contains(@class,'--name-grouped-field')]/div[1]//span")
    public WebElement addEmployeeFirstNameRequiredMessage;

    @FindBy(xpath = "//div[contains(.,'Employee Full Name')]//div[contains(@class,'--name-grouped-field')]/div[3]//span")
    public WebElement addEmployeeLastNameRequiredMessage;

    //@FindBy(xpath = "//span[contains(@class, 'oxd-input-group__message') and text()='Employee Id already exists']")
    //@FindBy(xpath = "//span[contains(@class,'oxd-input-group__message') and contains(.,'Employee Id already exists')]")
    //public WebElement addEmployeeEmployeeidexists;
    @FindBy(xpath = "//span[text()='Employee Id already exists']")
    public WebElement addEmployeeEmployeeIdExists;

    @FindBy(xpath = "//input[@placeholder='Search']")
    public WebElement addEmployeeSearchField;

    @FindBy(xpath = "//div[contains(@class,'oxd-toast-container')]")
    public WebElement toastMessageWrapper;

    @FindBy(xpath = "//span[contains(@class,'oxd-input-field-error-message') and text()='Required']")
    public List<WebElement> addEmployeeRequiredMsg;

    public AddEmployeePage() {
        PageFactory.initElements(DriverFactory.getDriver(), this);
    }
}