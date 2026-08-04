package pages.ui;


import org.openqa.selenium.support.PageFactory;
import utils.ui.CommonMethods;
import utils.ui.DriverFactory;

public class BasePage extends CommonMethods {

    public BasePage() {

        PageFactory.initElements(DriverFactory.getDriver(), this);
    }
}
