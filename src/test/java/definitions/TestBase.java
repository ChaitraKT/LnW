package definitions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class TestBase {
	
	
	
	public static WebDriver driver=null;
	

	public void setup() throws IOException
	{
		
		Properties p=new Properties();
		FileInputStream fi=new FileInputStream("C:\\Users\\Pavan\\eclipse-workspace\\seleniumdemo\\src\\main\\resources\\global.properties");
		p.load(fi);
		//System.out.println(p.getProperty("browser"));
		
		if(p.getProperty("browser").contains("firefox"))
		{
			driver=new FirefoxDriver();
		}
		else if (p.getProperty("browser").contains("chrome"))
		{
//			System.setProperty("webdriver.chrome.driver", value);
			driver=new ChromeDriver();
			}
		else
		{
			//Internetexplorer
		}
		driver.get(p.getProperty("url"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
	}
	
}
