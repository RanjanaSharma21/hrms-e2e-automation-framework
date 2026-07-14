package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPage extends BasePage {

    @FindBy(css = "div.oxd-topbar-header")
    public WebElement dashboardHeader;

    //@FindBy(xpath = "//div[contains(@class, 'oxd-topbar-header')]")
    //public WebElement dashboardHeader;

    public String getHeaderTitle() {
        waitForVisibilityOfElement(dashboardHeader);
        return dashboardHeader.getText(); // Will return "Dashboard"
    }
}
