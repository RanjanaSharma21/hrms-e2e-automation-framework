package ui.utils;

import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
//import ui.utils.common;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class CommonMethods extends PageInitializer {

    private static WebDriverWait wait;

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

        String webUrl = ConfigReader.getProperty("webUrl");
        if (webUrl == null || webUrl.isEmpty()) {
            throw new RuntimeException("Webapp not defined in ConfigReader");
        }

        DriverFactory.getDriver().get(webUrl);
        DriverFactory.getDriver().manage().window().maximize();
        DriverFactory.getDriver().manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(Constants.IMPLICIT_WAIT));

        initPageElements();
    }

    public void setValue(WebElement element, String value) {
        waitForElementToBeClickable(element);
        element.clear();
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            element.sendKeys(Keys.chord(Keys.COMMAND, "a"));
        } else {
            element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        }
        element.sendKeys(Keys.BACK_SPACE);
        element.sendKeys(value);
    }

    public void setReactValue(WebElement element, String value) {

        waitForElementToBeClickable(element);

        element.click();

        element.sendKeys(Keys.chord(Keys.COMMAND, "a"));
        element.sendKeys(Keys.BACK_SPACE);
        element.sendKeys(value);

        element.sendKeys(Keys.TAB);
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

    public void selectCustomDropdownValue(WebElement dropdownField, String optionText) {
        // 1. Force execution safety by waiting for the dropdown wrapper to become clickable
        waitForElementToBeClickable(dropdownField);
        click(dropdownField);

        // 2. Build the multi-attribute sure-shot selector dynamically
        By optionLocator = By.xpath("//div[@role='listbox']//div[contains(@class, 'oxd-select-option') and contains(., '" + optionText + "')]");

        // 3. Find and click the target choice row element directly
        WebElement targetingRow = DriverFactory.getDriver().findElement(optionLocator);
        click(targetingRow);
    }

    public void waitForVisibility(WebElement element) {
        wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void getValidationMessage(String expectedMessage) {

        if (expectedMessage.equalsIgnoreCase("Required")) {

            String currentUrl = DriverFactory.getDriver().getCurrentUrl();

            if (currentUrl.contains("/login")) {

                waitForVisibility(loginPage.requiredMessages.getFirst());
                String actualMessage = loginPage.requiredMessages.getFirst().getText();

                Assert.assertEquals(
                        "Login validation mismatch!",
                        expectedMessage,
                        actualMessage
                );

            } else if (currentUrl.contains("/pim/addEmployee")) {

                By requiredMessageLocator =
                        By.xpath("//span[contains(@class,'oxd-input-field-error-message') and text()='Required']");

                WebDriverWait wait = new WebDriverWait(
                        DriverFactory.getDriver(),
                        Duration.ofSeconds(10)
                );

                wait.until(ExpectedConditions.presenceOfElementLocated(requiredMessageLocator));

                List<WebElement> requiredMessages =
                        DriverFactory.getDriver().findElements(requiredMessageLocator);

                System.out.println("Number of Required messages displayed: " + requiredMessages.size());

                Assert.assertTrue(
                        "Required validation message is not displayed",
                        requiredMessages.size() > 0
                );

                for (WebElement message : requiredMessages) {

                    Assert.assertEquals(
                            "Employee field validation mismatch!",
                            expectedMessage,
                            message.getText()
                    );
                }
            }

        } else if (expectedMessage.equalsIgnoreCase("Invalid credentials")) {

            waitForVisibility(loginPage.invalidCredentialMessage);

            Assert.assertEquals(
                    "Login validation mismatch!",
                    expectedMessage,
                    loginPage.invalidCredentialMessage.getText()
            );

        } else if (expectedMessage.equalsIgnoreCase("Employee Id already exists")) {

            waitForVisibility(addEmployeePage.addEmployeeEmployeeIdExists);

            Assert.assertEquals(
                    "Employee Id validation mismatch!",
                    expectedMessage,
                    addEmployeePage.addEmployeeEmployeeIdExists.getText()
            );
        }










        /*String actualMessage = null;

        if (expectedMessage.equalsIgnoreCase("Required")) {
            if (Objects.requireNonNull(DriverFactory.getDriver().getCurrentUrl()).contains("/login")) {
                waitForVisibility(loginPage.requiredMessages.getFirst());
                actualMessage = loginPage.requiredMessages.getFirst().getText();
                org.junit.Assert.assertEquals("Login validation mismatch!", expectedMessage, actualMessage);
            }
            else if (DriverFactory.getDriver().getCurrentUrl().contains("/pim/addEmployee")){
                waitForVisibility(addEmployeePage.addEmployeeRequiredMessage.get(0));

                // CASE A: Both First Name AND Last Name fields are empty (Size will be 2)
                if (addEmployeePage.addEmployeeRequiredMessage.size() == 2) {
                    String firstNameError = addEmployeePage.addEmployeeRequiredMessage.get(0).getText();
                    String lastNameError = addEmployeePage.addEmployeeRequiredMessage.get(1).getText();
                    // Assert both items dynamically in one step
                    org.junit.Assert.assertEquals("First name validation missing!", expectedMessage, firstNameError);
                    org.junit.Assert.assertEquals("Last name validation missing!", expectedMessage, lastNameError);
                    actualMessage = expectedMessage;
                }
                else {
                    actualMessage = addEmployeePage.addEmployeeRequiredMessage.getFirst().getText();
                    org.junit.Assert.assertEquals("Employee name field validation mismatch!", expectedMessage, actualMessage);
                }
            }
        }
        else if (expectedMessage.equalsIgnoreCase("Invalid credentials")) {
            waitForVisibility(loginPage.invalidCredentialMessage);
            actualMessage = loginPage.invalidCredentialMessage.getText();
            org.junit.Assert.assertEquals("Login validation mismatch!", expectedMessage, actualMessage);
        }
        else if (expectedMessage.equalsIgnoreCase("Employee Id already exists")) {
            waitForVisibility(addEmployeePage.addEmployeeEmployeeIdExists);
            actualMessage = addEmployeePage.addEmployeeEmployeeIdExists.getText();
            System.out.println(actualMessage);
            Assert.assertEquals("Employee Id validation mismatch!", expectedMessage, actualMessage);
        }*/
    }

}
