package runners;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/",
        glue = "steps",
        plugin = {
                "pretty",
                "html:target/cucumber-reports.html",
                "json:target/cucumber.json",
                "junit:target/cucumber.xml",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "rerun:target/failed.txt"
        },
        dryRun = false,
        tags = "@login"
        //tags = "@addemp",
        //tags = "@adduser",
        //tags = "@addcontact",
        //tags = "@addpersonal",
        //tags = "@addprofile",
        //tags = "@adddependenats",
        //tags = "@searchemp"

)

public class TestRunner {
}
