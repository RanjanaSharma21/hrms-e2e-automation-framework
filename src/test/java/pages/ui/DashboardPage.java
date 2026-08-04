package pages.ui;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPage extends BasePage {

    @FindBy(css = "div.oxd-topbar-header")
    public WebElement dashboardHeader;

    @FindBy(xpath = "//a[contains(@class, 'oxd-main-menu-item') and contains(@href, 'viewMyDetails') and .//span[text()='My Info']]")
    public WebElement dashboardPageMyInfo;

    @FindBy(xpath="//span[text()='PIM']")
    public WebElement dashboardPagePim;

    @FindBy(xpath = "//a[contains(@class, 'oxd-main-menu-item') and contains(@href, 'viewAdminModule') and .//span[text()='Admin']]")
    public WebElement dashboardAdminOption;


}
