package ui.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import ui.utils.CommonMethods;

public class Hooks  {

    CommonMethods common = new CommonMethods();
    @Before
    public void browserSetup()  {
        common.openBrowser();
        common.openWebApplication();
    }

    @After
    public void tearDown(Scenario scenario) {
        common.closeBrowser(scenario);
    }
}
