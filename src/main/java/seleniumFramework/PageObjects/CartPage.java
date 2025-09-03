package seleniumFramework.PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumFramework.AbstractComponents.AbstractComponents;

public class CartPage extends AbstractComponents {

	WebDriver driver;
	
	public CartPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	
	}
	
	
	
	//WebElement cart =	driver.findElement(By.xpath("//li/button[@routerlink='/dashboard/cart']"));
	//cart.click();
	
	//Page Factory model
	
	
	//WebElement checkout =	driver.findElement(By.xpath("//li/button[@class='btn btn-primary']"));
	//JavascriptExecutor js =	(JavascriptExecutor)driver;
	//js.executeScript("arguments[0].click();", checkout);
	
	@FindBy(xpath="//li/button[@class='btn btn-primary']")
	WebElement checkout;
	
	
	
	
	//List<WebElement> cartProduct =	driver.findElements(By.xpath("//div[@class='cartSection']"));
	
	
	@FindBy(xpath="//div[@class='cartSection']")
	List<WebElement> cartProducts;
	
	public List<WebElement> getProductList()
	{
		return cartProducts;
	}
	
	public Boolean matchProductsByName(List<String> accessories)
	{
		
		for(String product : accessories)
		{
			
			Boolean matchProduct =	getProductList().stream().anyMatch(cartP -> cartP.findElement(By.xpath(".//h3")).getText().equals(product));
		
		if(!matchProduct)
		{
			return false;
		}
		}
		
		return true;
	}
	
	public CheckoutPage doCheckout()
	{
		clickElement(checkout);
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		return checkoutPage;
	}
	
		
		
		
	}




