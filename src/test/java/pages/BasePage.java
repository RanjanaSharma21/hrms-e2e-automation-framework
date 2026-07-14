package pages;


import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;
import utils.DriverFactory;

public class BasePage extends CommonMethods {

    public BasePage() {

        PageFactory.initElements(DriverFactory.getDriver(), this);
    }
}
