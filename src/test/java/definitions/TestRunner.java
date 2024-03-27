package definitions;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = { "definitions" }, plugin = { "pretty",
		"html:target/HtmlReports" }, tags = "@Smoke")
public class TestRunner {

}
