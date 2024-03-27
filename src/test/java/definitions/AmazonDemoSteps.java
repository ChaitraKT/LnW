package definitions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.AmazonLoginPage;
import pages.AmazonPaymentsPage;
import pages.AmazonSearchPage;

public class AmazonDemoSteps {

	WebDriver driver = null;
	AmazonLoginPage login;
	AmazonSearchPage searchPage;
	AmazonPaymentsPage paymentsPage;
	String priceOnProductPage;
	String priceOnCartPage;
	int total=0;
	List<String> arrayList = new ArrayList<>();

	@Given("I login to Amazon page")
	public void i_login_to_this_page() {
		System.setProperty("Webdriver.chrome.driver", "/seleniumdemo/src/test/resources/drivers/chromedriver");
		// Write code here that turns the phrase above into concrete actions
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://www.amazon.in/");

		login = new AmazonLoginPage(driver);

		login.login("chaitrakt93@gmail.com", "Shop1993@");

	}

	@When("I search for {string} and press enter")
	public void i_search_for_and_press_enter(String string) {
		searchPage = new AmazonSearchPage(driver);
		System.out.print("before search" + string);
		searchPage.searchByKeyword(string);
		System.out.print("String");
	}

	@When("select the {string} item in the list")
	public void select_the_item_in_the_list(String string) throws InterruptedException {
		searchPage.selectNthItem(string);
		searchPage.switchToOtherTab();
		Thread.sleep(3000);
		paymentsPage = new AmazonPaymentsPage(driver);
		priceOnProductPage = paymentsPage.getPriceOnProductPage().getText();
		System.out.print("Price on product page is" + priceOnProductPage);
		arrayList.add(priceOnProductPage);
	}

	@When("Add the item to cart by clicking on add to cart")
	public void add_the_item_to_cart_by_clicking_on_add_to_cart() throws InterruptedException {
		searchPage.addToCart();
		Thread.sleep(10000);
//		searchPage.closeAttachedCartModal();
//		Thread.sleep(3000);
	}

	@When("open cart from the top-left")
	public void open_cart_from_the_top_left() {
		paymentsPage.openTheCart();
	}

	@Then("Verify that the price is identical to the product page")
	public void verify_that_the_price_is_identical_to_the_product_page() {
		String exactPrice = null;
		if (paymentsPage.cartHasMultipleItems())
			for (int i = arrayList.size() - 1; i >= 0; i++) {
				System.out.println("List is"+arrayList.get(i));
				priceOnProductPage = arrayList.get(i);
				List<WebElement> elements = paymentsPage.getPriceOnCartPageForMultiple();
				for (WebElement element : elements) {
					priceOnCartPage = element.getText();
					if (priceOnCartPage.contains(".")) {
						exactPrice = priceOnCartPage.substring(0, priceOnProductPage.length());
						System.out.print("Cart page price is"+exactPrice);
					}
					String formattedNumber = priceOnProductPage.replaceAll("[^\\d]", "");
					//Printing value of i  
					int j = Integer.parseInt(formattedNumber);  
					total = total + j;
					System.out.print("Total is" + total);
					System.out.print("formatted number is "+formattedNumber);
					Assert.assertEquals(priceOnProductPage, exactPrice);
				}
			}
		else {
			System.out.print("Inside else");
			priceOnCartPage = paymentsPage.getPriceOnCartPage().getText().trim();
			if (priceOnCartPage.contains(".")) {
				exactPrice = priceOnCartPage.substring(0, priceOnProductPage.length());
			}
			total= Integer.parseInt(priceOnProductPage);
			Assert.assertEquals(priceOnProductPage, exactPrice);
		}

	}

	@Then("Verify that the sub total is identical to the product page")
	public void verify_that_the_sub_total_is_identical_to_the_product_page() {
		String subTotalPrice = paymentsPage.getSubTotal().getText().trim();
		Assert.assertEquals(String.valueOf(total), subTotalPrice.substring(0, priceOnProductPage.length()));
		}
		
//		paymentsPage.getRemoveItem().click();
	

}
