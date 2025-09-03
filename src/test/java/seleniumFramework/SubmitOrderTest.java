package seleniumFramework;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import seleniumFramework.PageObjects.CartPage;
import seleniumFramework.PageObjects.CheckoutPage;
import seleniumFramework.PageObjects.OrdersPage;
import seleniumFramework.PageObjects.ProductCataloguePage;
import seleniumFramework.testComponents.BaseTest;
import seleniumFramework.testComponents.Retry;

public class SubmitOrderTest extends BaseTest {

//Adding multiple items to Cart 
	
	//String countryName = "India";
	
	//String country = "Ind";
	
	//Create an ArrayList for adding all three products
	
	//List<String> accessories =	List.of("ZARA COAT 3" , "ADIDAS ORIGINAL" , "IPHONE 13 PRO");

	@Test(dataProvider="getData" , retryAnalyzer=Retry.class)
	public void submitOrder(HashMap<String,Object> input) throws IOException
	{
		
		ProductCataloguePage productCataloguePage  = landingPage.loginApplication((String)input.get("uEmail"), (String)input.get("uPassword"));
		Assert.assertTrue(landingPage.getLoginToast().isDisplayed());
		Assert.assertTrue(landingPage.getLoginToast().getText().equals("Login Successfully"));
		WebElement confirmationToast =	productCataloguePage.addProductsToCart((List<String>) input.get("accessories"));
		Assert.assertTrue(confirmationToast.isDisplayed());
		Assert.assertTrue(confirmationToast.getText().equals("Product Added To Cart"));
		CartPage cartPage  = productCataloguePage.goToCart();
		Boolean matchProduct =	cartPage.matchProductsByName((List<String>) input.get("accessories"));
		Assert.assertTrue(matchProduct, "Product did not matched");
		CheckoutPage checkoutPage = cartPage.doCheckout();
		checkoutPage.selectCountry((String)input.get("country"));
		checkoutPage.selectCountryByName((String)input.get("countryName"));
		WebElement orderToast =	checkoutPage.placeOrder();
		Assert.assertTrue(orderToast.isDisplayed());
		Assert.assertTrue(orderToast.getText().equals("Order Placed Successfully"));
		OrdersPage ordersPage = checkoutPage.goOrdersPage();
		ordersPage.getOrderIDText();
		Boolean orderMatch =	ordersPage.matchOrderByID();
		Assert.assertTrue(orderMatch , "Order did not match");
			
	}
	
	@DataProvider
	public Object[][] getData() throws IOException
	{
		List<HashMap<String,Object>> data =	getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\seleniumFramework\\data\\PurchaseOrder.json");
		return new Object[][] {{data.get(0)}, {data.get(1)}};
	}
}
