package seleniumFramework.PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumFramework.AbstractComponents.AbstractComponents;

public class CheckoutPage extends AbstractComponents {

	WebDriver driver;
	
	public CheckoutPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//WebElement inputCountry =	driver.findElement(By.xpath("//input[@placeholder='Select Country']"));
	
	@FindBy(xpath="//input[@placeholder='Select Country']")
	WebElement inputCountry;
	
	
	public void selectCountry(String country)
	{
		inputCountry.sendKeys(country);
	}
	
	
	
	//List<WebElement> countryList =	driver.findElements(By.xpath("//button[contains(@class,'ta-item')]"));
	
	//WebElement placeOrder =	driver.findElement(By.xpath("//a[contains(@class,'action__submit')]"));
	
	@FindBy(xpath="//a[contains(@class,'action__submit')]")
	WebElement placeOrder;
	
	
	
	@FindBy(xpath="//button[contains(@class,'ta-item')]")
	List<WebElement> countryList;
	
	
	public List<WebElement> getCountryList()
	{
		return countryList;
	}
	
	public void selectCountryByName(String countryName)
	{
		WebElement selectCountry =	getCountryList().stream().filter(country -> country.findElement(By.xpath(".//span")).getText().equalsIgnoreCase(countryName)).findFirst().orElse(null);
		
		if(selectCountry != null)
		{
			selectCountry.click();
		}
		
	}
	
	By orderConfirmationLocator =	By.xpath("//div[contains(@class,'ng-trigger-flyInOut')]/div[@aria-label='Order Placed Successfully']");
	
	public WebElement placeOrder()
	{
		placeOrder.click();
		return waitForTheElementToAppear(orderConfirmationLocator);
	}
	
	
	
	
	
	//WebElement orderToast =	wait.until(ExpectedConditions.visibilityOfElementLocated(orderConfirmationLocator));
	
	
	/*WebElement selectCountry =	countryList.stream().filter(country -> country.findElement(By.xpath(".//span")).getText().equalsIgnoreCase("India")).findFirst().orElse(null);
	if(selectCountry != null)
	{
		selectCountry.click();
	}*/
	
	
	
}
