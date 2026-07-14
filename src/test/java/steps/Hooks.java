package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.CommonMethods;

public class Hooks extends CommonMethods {

    @Before
    public void browserSetup()  {
        openBrowser();
        openWebApplication();
    }

    @After
    public void tearDown(Scenario scenario) {
        closeBrowser(scenario);
    }
}
