package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AmazonPaymentsPage {
	WebDriver driver;
	By cartLink = By.id("nav-cart");
	By priceOnProductPage = By.xpath("(//span[@class='a-price-whole'])[1]");
	By priceOnProductPage2 = By.xpath("//div[@id='newAccordionRow_0']//div[contains(@class,'a-section')]/span[@class='a-color-price']");
	By priceOnCartPage = By.xpath("//div[@data-name='Active Items']//div[@class='sc-badge-price-to-pay']");
	By subTotalPrice = By.xpath("//span[@id='sc-subtotal-amount-buybox']");
	By removeItem = By.xpath("//div[@data-name='Active Items']//span[@data-action='delete']/span/input");
	By totalItems = By.xpath("//span[@id='sc-subtotal-label-buybox']");

	public AmazonPaymentsPage(WebDriver driver) {
		this.driver = driver;
	}

	public void openTheCart() {

		driver.findElement(cartLink).click();
	}

	public boolean cartHasMultipleItems() {
		if ((driver.findElement(totalItems).getText()).contains("2"))
			return true;
		else
			return false;
	}

	public WebElement getPriceOnProductPage() {

		return driver.findElement(priceOnProductPage);
	}
	
	public WebElement getPriceOnProductPage2() {

		return driver.findElement(priceOnProductPage2);
	}

	public void getRemoveItem() {
		
		 if(driver.findElement(removeItem).isDisplayed()) {
			 System.out.print("delete option is present");
			 driver.findElement(removeItem).click();
		 }
	}

	public WebElement getPriceOnCartPage() {

		return driver.findElement(priceOnCartPage);
	}

	public List<WebElement> getPriceOnCartPageForMultiple() {

		return driver.findElements(priceOnCartPage);
	}

	public WebElement getSubTotal() {

		return driver.findElement(subTotalPrice);
	}
}
