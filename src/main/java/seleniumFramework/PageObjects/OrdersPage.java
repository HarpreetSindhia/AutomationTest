package seleniumFramework.PageObjects;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumFramework.AbstractComponents.AbstractComponents;

public class OrdersPage extends AbstractComponents {

	WebDriver driver;
	
	public OrdersPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	//List<WebElement> orderIDs =	driver.findElements(By.xpath("//tr[3]//td[@class='em-spacer-1']/label"));
	
	@FindBy(xpath="//tr[3]//td[@class='em-spacer-1']/label")
	List<WebElement> orderIDs;
	
	public List<WebElement> getOrderID()
	{
		return orderIDs;
	}
	
	//List<WebElement> orders =	driver.findElements(By.xpath("//tr[@class='ng-star-inserted']"));
	
	@FindBy(xpath="//tr[@class='ng-star-inserted']")
	List<WebElement> orders;
	
	
	public List<WebElement> orderHistory()
	{
		return orders;
	}
	
	
	public List<String> getOrderIDText()
	{
		List<String> orderIDText =	getOrderID().stream().map(order -> order.getText().replace("|", "").trim()).collect(Collectors.toList());
		return orderIDText;
	}
	
	
	public Boolean matchOrderByID()
	{
		List<String> orderIDText = getOrderIDText();
		
		for(String myOrder : orderIDText)
		{
			Boolean orderMatch =	orderHistory().stream().anyMatch(orderP -> orderP.findElement(By.xpath(".//th")).getText().trim().equals(myOrder));
			
			if(!orderMatch)
			{
				return false;
			}
			
			
		}
		
		return true;
	}
	
	/*for(String myOrder : orderIDText)
	{
		Boolean orderMatch =	orders.stream().anyMatch(orderP -> orderP.findElement(By.xpath(".//th")).getText().trim().equals(myOrder));
		
		Assert.assertTrue(orderMatch , "Order did not match");
	}*/
}

