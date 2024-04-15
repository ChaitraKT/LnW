package pages;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AmazonSearchPage {
	WebDriver driver;

	By searchInput = By.id("twotabsearchtextbox");
	By searchIcon = By.id("nav-search-submit-button");
	By addToCartButton = By.xpath("//input[@id ='add-to-cart-button']");
	By addToCart2Button = By.xpath("//div[@data-csa-c-is-in-initial-active-row='true']//input[@id ='add-to-cart-button']");
	By sideModalOnAddToCart = By.xpath("//div[@id='attach-added-to-cart-message']");
	By closeModal = By.xpath("//a[@id='attach-close_sideSheet-link']");

	public AmazonSearchPage(WebDriver driver) {
		this.driver = driver;
	}

	public void searchByKeyword(String productname) {
		driver.findElement(searchInput).clear();
		driver.findElement(searchInput).sendKeys(productname);
		driver.findElement(searchIcon).click();

	}

	public void selectNthItem(String number) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebElement result = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("(//div[@class='a-section']//span[contains(@class,'a-size-medium')])[" + number + "]")));
		result.click();

	}

	public void switchToOtherTab() {
		String mainWindowHandle = driver.getWindowHandle();

		// Get all window handles
		Set<String> allWindowHandles = driver.getWindowHandles();

		Iterator<String> it = allWindowHandles.iterator();
		// Iterate through all handles to find the next window handle
		String nextWindowHandle = "";
		while (it.hasNext())
		nextWindowHandle = it.next();
		
		if (!nextWindowHandle.equals(mainWindowHandle)) {
			driver.switchTo().window(nextWindowHandle);
		}
		// Switch to the next window handle

		driver.navigate().refresh();
	}

	public void closeAttachedCartModal() {
		if (driver.findElement(sideModalOnAddToCart).isDisplayed()) {
			driver.findElement(closeModal).click();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(closeModal));
		}

	}

	public void addToCart() {
		if(driver.findElement(addToCartButton).isDisplayed())
			driver.findElement(addToCartButton).click();
		else
            driver.findElement(addToCart2Button).click();
	}
}

//
//
//Scenario: Add items to cart and verify the price
//Given I login to Amazon page
//When I search for "<Item>" and press enter
//And select the "<number>" item in the list
//And Add the item to cart by clicking on add to cart
//And open "Cart" from the top-left
//Then Verify that the price is identical to the product page
//Then Verify that the sub total is identical to the product page