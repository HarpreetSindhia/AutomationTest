package seleniumFramework;

import org.testng.Assert;
import org.testng.annotations.Test;

import seleniumFramework.PageObjects.ProductCataloguePage;
import seleniumFramework.testComponents.BaseTest;

//Search keyword implemented

public class SearchProductTest extends BaseTest {

	@Test
	public void searchLaptop()
	{
		ProductCataloguePage productCataloguePage  = landingPage.loginApplication("happy@gmail.com", "happy123");
		Assert.assertEquals(productCataloguePage.searchLaptop(), "No Products Found");
		
		
	}
}
