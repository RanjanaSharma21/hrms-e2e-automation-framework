package runners;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
                "src/test/resources/features/ui/1AddEmployee.feature",
                "src/test/resources/features/ui/2SearchEmployee.feature",
                "src/test/resources/features/ui/3AddUsername.feature",
                "src/test/resources/features/ui/4UpdatePersonalDetail.feature",
                "src/test/resources/features/ui/5UpdateContactDetail.feature",
                "src/test/resources/features/ui/6AddProfilePicture.feature"
                //"src/test/resources/features/ui/7DeleteEmployee.feature"
        },
        glue = "ui.steps",
        plugin = {
                "pretty",
                "html:target/cucumber-reports.html",
                "json:target/jsonReports/ui-cucumber.json",
                "junit:target/cucumber.xml",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "rerun:target/failed.txt"
        },
        dryRun = false

        //tags = "  @addpersonal"
        //tags = "@delemp"
        //tags = "@addcontact"
        //tags = "@addemp1  or @adduser" //or @negativenm\"//1 or @searchempor @addpersonal1  or @addcontact" //@delemp" // @adduser1" // or  @addpersonal1  or @addcontact or @addprofile or @searchemp"
        //tags = "@addcontact"
        //tags = "@addpersonal"
        //tags = "@addprofile"
        //tags = "@adddependenats"
        //tags = "@searchemp"


)

public class UIRunner {
}
