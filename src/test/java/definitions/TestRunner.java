package definitions;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = { "definitions" }, plugin = { "pretty",
		"html:target/HtmlReports" }, tags = "@Smoke")
public class TestRunner extends AbstractTestNGCucumberTests {

}
