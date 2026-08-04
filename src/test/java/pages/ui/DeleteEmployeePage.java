package pages.ui;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ui.DriverFactory;

import java.util.List;

public class DeleteEmployeePage {

    @FindBy(xpath = "//button[i[contains(@class, 'bi-trash')]]")
    public WebElement deleteButton;

    //@FindBy(css = "button .bi-trash")
    //private WebElement deleteButton;

    @FindBy(xpath = "//div[@role='dialog']//button[normalize-space()='Yes, Delete']")
    public WebElement yesDeleteConfirmation;

    @FindBy(xpath = "//div[contains(@class,'oxd-toast-container')]")
    public WebElement toastMessageWrapper;

    @FindBy(xpath = "//div[contains(@class,'oxd-table-body')]//div[@role='row']")
    public List<WebElement> employeeRows;

    @FindBy(xpath = "//div[contains(@class,'oxd-table-body')]//div[@role='row']")
    public WebElement employeeRow;

    @FindBy(xpath = "//*[contains(text(),'Successfully deleted')]")
    public WebElement successMessage;



    public DeleteEmployeePage() {
        PageFactory.initElements(DriverFactory.getDriver(), this);
    }


}
