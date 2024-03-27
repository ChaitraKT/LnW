package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AmazonLoginPage {

	WebDriver driver;

	By signInLink = By.id("nav-link-accountList");
	By emailField = By.id("ap_email");
	By passwordField = By.id("ap_password");
	By loginButton = By.id("signInSubmit");
	By continueButton = By.id("continue");
	By searchInput = By.id("twotabsearchtextbox");
	By searchIcon = By.id("nav-search-submit-button");

	public AmazonLoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public void login(String username, String pwd) {
		driver.findElement(signInLink).click();
		driver.findElement(emailField).sendKeys(username);
		driver.findElement(continueButton).click();
		driver.findElement(passwordField).sendKeys(pwd);
		driver.findElement(loginButton).click();

	}

	public void refresh() {
		// TODO Auto-generated method stub
		driver.navigate().refresh();
	}

}
