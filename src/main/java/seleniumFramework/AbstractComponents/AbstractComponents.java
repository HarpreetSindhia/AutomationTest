package seleniumFramework.AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import seleniumFramework.PageObjects.CartPage;
import seleniumFramework.PageObjects.OrdersPage;

public class AbstractComponents {
	
	WebDriver driver;
	JavascriptExecutor js;
	Actions action;

	public AbstractComponents(WebDriver driver)
	{
		this.driver = driver;
		this.js =(JavascriptExecutor)driver;
		this.action = new Actions(driver);
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(xpath="//li/button[@routerlink='/dashboard/cart']")
	WebElement cartPage;
	
	public CartPage goToCart()
	{
		cartPage.click();
		CartPage cartPage = new CartPage(driver);
		return cartPage;
	}
	
	
	@FindBy(xpath="//li/button[@routerlink='/dashboard/myorders']")
	WebElement ordersPage;
	
	public OrdersPage goOrdersPage()
	{
		ordersPage.click();
		OrdersPage ordersPage = new OrdersPage(driver);
		return ordersPage;
	}
	
	public void waitForTheWebElementToAppear(WebElement findBy)
	{
		WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(findBy));
	}
	
	
	public WebElement waitForTheElementToAppear(By findBy)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
		return	wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
	
	public void waitForTheElementToDisappear(By findBy)
	{
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
		try {
			
			wait.until(ExpectedConditions.invisibilityOfElementLocated(findBy));
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}
	
	public void waitForElementToAppear(By findBy)
	{
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
		try {
			
			wait.until(ExpectedConditions.invisibilityOfElementLocated(findBy));
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}
	
	
	public void clickElement(WebElement target)
	{
		JavascriptExecutor js =	(JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", target);
	}
	
	public void clickwithAction(WebElement findBy)
	{
		Actions action = new Actions(driver);
		action.moveToElement(findBy).click().build().perform();;
	}
	
	
	
	
	
	
	
	
	//By loginToastLocator =	By.xpath("//div[contains(@class,'ng-trigger-flyInOut')]/div[@aria-label='Login Successfully']");
	//WebElement loginToast =	wait.until(ExpectedConditions.visibilityOfElementLocated(loginToastLocator));
}
