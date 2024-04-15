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
	int price1=0, price2=0,total =0,length =0;
	List<String> arrayList = new ArrayList<>();

	@Given("I login to Amazon page")
	public void i_login_to_this_page() {
		System.setProperty("Webdriver.chrome.driver", "/seleniumdemo/src/test/resources/drivers/chromedriver");
		// Write code here that turns the phrase above into concrete actions
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://www.amazon.in/");

		login = new AmazonLoginPage(driver);

		login.login("chaitrakt93@gmail.com", "********");

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
		if(priceOnProductPage.equals(""))
		priceOnProductPage = paymentsPage.getPriceOnProductPage2().getText().trim();
		System.out.print("Price on product page is" + priceOnProductPage);
		length = priceOnProductPage.length()-3;
		if (priceOnProductPage.contains(".")) {
			System.out.print("before removing dot "+length);
			priceOnProductPage = priceOnProductPage.substring(0, length);
		}
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
		int j=0;
		if (paymentsPage.cartHasMultipleItems()) {
			List<WebElement> elements = paymentsPage.getPriceOnCartPageForMultiple();
			for (int i = arrayList.size() - 1; i >= 0; i--) {
				price1 = Integer.parseInt(arrayList.get(i));	
					priceOnCartPage = elements.get(j++).getText().trim();
					if (priceOnCartPage.contains(".")) {
						exactPrice = priceOnCartPage.substring(0, priceOnCartPage.length()-3);
					}
					price2 = Integer.parseInt(exactPrice);  
					total = total + price2;
					Assert.assertEquals(price1, price2);
				}
			}
		else {
			System.out.print("Inside else");
			priceOnCartPage = paymentsPage.getPriceOnCartPage().getText().trim();
			System.out.print("priceOn Cart page is"+priceOnCartPage);
			if (priceOnCartPage.contains(".")) {
				System.out.print("before removing dot "+priceOnProductPage.length());
				exactPrice = priceOnCartPage.substring(0, priceOnProductPage.length());
				System.out.print("after removing dot "+exactPrice);
			    if (exactPrice.contains(",")) 
			    	System.out.print("splitting logic is"+ exactPrice.split(",")[1]);
				exactPrice = exactPrice.split(",")[0]+exactPrice.split(",")[1];		
			}
			System.out.print("price On Cart page after splitting is"+exactPrice);
			if (priceOnProductPage.contains(",")) 
				priceOnProductPage = priceOnProductPage.split(",")[0]+priceOnProductPage.split(",")[1];	
			System.out.print("priceOn Product page is"+priceOnProductPage);
			price1= Integer.parseInt(priceOnProductPage);
			System.out.print("price1 is after converting "+price1);
			price2= Integer.parseInt(exactPrice);
			System.out.print("price2 is after converting "+price2);
			Assert.assertEquals(price1, price2);
			total = price1;
		}

	}

	@Then("Verify that the sub total is identical to the product page")
	public void verify_that_the_sub_total_is_identical_to_the_product_page() {
		String subTotalPrice = paymentsPage.getSubTotal().getText().trim();
		int index=0;
		if (subTotalPrice.contains(".")) {
			index = subTotalPrice.indexOf(".");
			System.out.print("index is "+index);
			subTotalPrice = subTotalPrice.substring(0,index);	
			System.out.print("sub total after removing . is "+subTotalPrice);
		    if (subTotalPrice.contains(",")) 
		    	subTotalPrice = subTotalPrice.split(",")[0]+subTotalPrice.split(",")[1];		
		}
		paymentsPage.getRemoveItem();
		Assert.assertEquals(total, Integer.parseInt(subTotalPrice));
//		Thread.sleep(3000);
		
		}
		

	

}
