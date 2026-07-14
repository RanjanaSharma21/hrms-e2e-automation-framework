package utils;

import io.cucumber.java.Scenario;
import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Objects;

public class CommonMethods extends PageInitializer {

    private static WebDriverWait wait;
    public static String previousEmployeeId = null;

    public void openBrowser() {

        WebDriver driver;
        String browser = ConfigReader.getProperty("browser");

        if (browser == null || browser.isEmpty()) {
            throw new RuntimeException("Browser not defined in ConfigReader");
        }
        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case  "ie":
                driver = new InternetExplorerDriver();
                break;
            case  "safari":
                driver = new SafariDriver();
                break;
            case  "edge":
                driver = new EdgeDriver();
                break;
            default:
                throw new RuntimeException("Browser not defined in ConfigReader");
        }
        DriverFactory.setDriver(driver);
    }

    public void openWebApplication() {

        String weburl = ConfigReader.getProperty("weburl");
        if (weburl == null || weburl.isEmpty()) {
            throw new RuntimeException("Webapp not defined in ConfigReader");
        }

        DriverFactory.getDriver().get(weburl);
        DriverFactory.getDriver().manage().window().maximize();
        DriverFactory.getDriver().manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(Constants.IMPLICIT_WAIT));

        initPageElements();
    }

    public void setValue(WebElement element, String value) {
        waitForElementToBeClickable(element);
        element.clear();
        element.sendKeys(value);
    }

    public void waitForElementToBeClickable(WebElement element) {
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(Constants.EXPLICIT_WAIT));
        }
        return wait;
    }

    public void click(WebElement element) {
        waitForElementToBeClickable(element);
        element.click();
    }

    public void waitForVisibilityOfElement(WebElement element) {
        wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(Constants.EXPLICIT_WAIT));
        getWait().until(ExpectedConditions.visibilityOf(element));
    }

    public void getValidationMessage(String expectedMessage) {

        String actualMessage = null;
        if (expectedMessage.equalsIgnoreCase("Required")) {
            waitForVisibilityOfElement(loginPage.requiredMessages.getFirst());
            actualMessage = loginPage.requiredMessages.getFirst().getText();
            Assert.assertEquals("Login validation mismatch!", actualMessage, expectedMessage);
        }
        else if (expectedMessage.equalsIgnoreCase("Invalid credentials")) {
            waitForVisibilityOfElement(loginPage.invalidCredentialMessage);
            actualMessage = loginPage.invalidCredentialMessage.getText();
            Assert.assertEquals("Login validation mismatch!", actualMessage, expectedMessage);
        }
    }

    public void closeBrowser(Scenario scenario) {

        WebDriver driver = DriverFactory.getDriver();

        if (driver != null) {
            try {
                if (scenario.isFailed()) {
                    byte[] screenshot = takeScreenshot("failed/" + scenario.getName());
                    scenario.attach(screenshot, "image/png", "Failed_Scenario_Screenshot");
                } else {
                    byte[] screenshot = takeScreenshot("passed/" + scenario.getName());
                    scenario.attach(screenshot, "image/png", "Passed_Scenario_Screenshot");
                }
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                System.out.println("Screenshot failed: " + e.getMessage());
            } finally {
                try {
                    DriverFactory.quitDriver();
                } catch (Exception e) {}
            }
        }
        else {
            System.out.println("⚠️ Warning: Hooks could not find an active driver instance to close!");
        }
    }

    public byte[] takeScreenshot(String fileName) {

        TakesScreenshot ts = (TakesScreenshot) DriverFactory.getDriver();

        File sourceFile = ts.getScreenshotAs(OutputType.FILE);
        byte[] picByte = ts.getScreenshotAs(OutputType.BYTES);

        System.out.println("Screenshot path = " + Constants.SCREENSHOT_FILEPATH);
        File dir = new File(Constants.SCREENSHOT_FILEPATH);
        System.out.println("Screenshot dir = " + dir.getAbsolutePath());
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                throw new RuntimeException("Failed to create screenshot directory: " + dir.getAbsolutePath());
            }
        }

        try {
            FileUtils.copyFile(
                    sourceFile,
                    new File(
                            Constants.SCREENSHOT_FILEPATH +
                                    fileName + "_" +
                                    getTimeStamp("yyyy-MM-dd-HH-mm-ss") +
                                    ".png"
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to save screenshot: " + fileName, e);
        }

        return picByte;
    }

    public String getTimeStamp(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
