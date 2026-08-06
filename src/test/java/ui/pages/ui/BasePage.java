package ui.pages.ui;


import org.openqa.selenium.support.PageFactory;
import ui.utils.CommonMethods;
import ui.utils.DriverFactory;

public class BasePage extends CommonMethods {

    public BasePage() {

        PageFactory.initElements(DriverFactory.getDriver(), this);
    }
}
