package pages.ui;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ui.CommonMethods;
import utils.ui.DriverFactory;

import java.util.List;

public class SearchEmployeePage extends CommonMethods {

    @FindBy(id = "menu_pim_viewEmployeeList")
    public WebElement employeeListSubMenuTab;

    @FindBy(xpath = "//label[normalize-space()='Employee Id']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    public WebElement searchFormEmployeeIdField;

    @FindBy(xpath = "//label[normalize-space()='Employee Name']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    public WebElement searchFormEmployeeNameField;

    @FindBy(xpath = "//button[@type='submit' and normalize-space()='Search']")
    public WebElement userManagementSearchButton;

    @FindBy(id = "empsearch_employee_status")
    public WebElement searchFormEmployeeStatusDropdown;

    @FindBy(id = "empsearch_termination")
    public WebElement searchFormIncludeDropdown;

    @FindBy(id = "empsearch_supervisor_name")
    public WebElement searchFormSupervisorNameField;

    @FindBy(id = "empsearch_job_title")
    public WebElement searchFormJobTitleDropdown;

    @FindBy(id = "empsearch_sub_unit")
    public WebElement searchFormSubUnitDropdown;

    //@FindBy(id = "searchBtn")
    //public WebElement userManagementSearchButton;

    @FindBy(id = "resetBtn")
    public WebElement userManagementResetButton;

    @FindBy(id = "btnAdd")
    public WebElement addNewEmployeeButton;

    //@FindBy(xpath = "//div[contains(@class,'oxd-table-body')]//div[contains(@class,'oxd-table-row')]")
    //public List<WebElement> tableResultRows;
    @FindBy(xpath = "//div[contains(@class,'oxd-table-body')]//div[@role='row']")
    public List<WebElement> tableResultRows;

    @FindBy(xpath = "//div[contains(@class,'oxd-table-body')]//div[@role='row']")
    List<WebElement> gridRows;

    @FindBy(xpath = "//table[@id='resultTable']/tbody/tr[1]/td[3]/a")
    public WebElement firstResultEmployeeNameCell;

    @FindBy(xpath = "//table[@id='resultTable']/tbody/tr[1]/td[4]/a")
    public WebElement firstResultLastNameCell;

    //@FindBy(css = "div.toast-message, div.alert-warning")
    //public WebElement searchWarningToastAlert;
    @FindBy(xpath = "//span[normalize-space()='No Records Found']")
    public WebElement noRecordsFoundMessage;
    //@FindBy(xpath = "//*[normalize-space()='No Records Found']")
    //public WebElement noRecordsFoundMessage;
    //@FindBy(xpath = "//div[contains(.,'No Records Found')]")
    //public WebElement noRecordsFoundMessage;

    @FindBy(xpath = "//div[contains(@class,'oxd-table-body')]//div[@role='row']")
    public List<WebElement> employeeRows;

    @FindBy(xpath = "//div[contains(@class,'oxd-toast-container')]")
    public WebElement toastMessageWrapper;

    @FindBy(xpath = "//*[contains(text(),'Successfully deleted')]")
    public WebElement successMessage;

    public SearchEmployeePage() {
        PageFactory.initElements(DriverFactory.getDriver(), this);
    }
}