package utils.ui;

import org.openqa.selenium.WebDriver;

public class DriverFactory {

    private static WebDriver driver;

    public static void setDriver(WebDriver driver){
        DriverFactory.driver = driver;
    }
    public static WebDriver getDriver(){
        return DriverFactory.driver;
    }

    public static void quitDriver(){
        if (DriverFactory.driver != null){
            DriverFactory.driver.quit();
            DriverFactory.driver = null;
        }
    }
}
