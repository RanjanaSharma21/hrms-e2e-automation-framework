package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        // path of the feature directory
        features = "src/test/resources/features/api/",
        // path of the steps package, name must be same
        glue = "steps.api",
        // when you set the value of dry run to true, it stops execution and scans all the step def
        // and provide missing step definition, to execute the test case, set the value to false
        dryRun = false,
        // to execute test cases in a group, we need tagnames in feature file,
        // and we can use those tagnames here to execute the test cases in a group
        //tags = "@map or @rawjson or @dynamicjson or @lombokpojo",
        //tags = "@lombokpojo",
        tags = "@apiCrudIntegration or @lombokpojo or @map or @rawjson or @dynamicjson",
        // local reference for  qa
        // pretty - it prints all the steps in console, so that you can check which is failing with links
        plugin = {"pretty",
                "html:target/cucumber.html",
                "json:target/cucumber.json",
                "rerun:target/failed.txt",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        // this plugin will generate a txt file with the name of failed.txt and
        // it will store the failed test cases in that file, so that we can easily
        // rerun the failed test cases by using that file
        monochrome = true
)

public class APIRunner {
}
