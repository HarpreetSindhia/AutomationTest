package seleniumFramework.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumFramework.AbstractComponents.AbstractComponents;

public class LandingPage extends AbstractComponents{

	WebDriver driver;
	
	public LandingPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//PageFactory Model
	
	//WebElement email =	driver.findElement(By.xpath("//input[@id='userEmail']"));
	
	//WebElement password =	driver.findElement(By.xpath("//input[@id='userPassword']"));
	
	//WebElement login =	driver.findElement(By.xpath("//input[@value='Login']"));
	
	
	
	//WebElement loginToast =	wait.until(ExpectedConditions.visibilityOfElementLocated(loginToastLocator));
	
	@FindBy(xpath="//input[@id='userEmail']")
	WebElement email;
	
	@FindBy(xpath="//input[@id='userPassword']")
	WebElement password;
	
	@FindBy(xpath="//input[@value='Login']")
	WebElement login;
	
	By loginToastLocator =	By.xpath("//div[contains(@class,'ng-trigger-flyInOut')]/div[@aria-label='Login Successfully']");
	
	public ProductCataloguePage loginApplication(String uEmail , String uPassword)
	{
		email.sendKeys(uEmail);
		password.sendKeys(uPassword);
		login.click();
		ProductCataloguePage productCataloguePage = new ProductCataloguePage(driver);
		return productCataloguePage;
		
	}
	
	public WebElement getLoginToast()
	{
		return waitForTheElementToAppear(loginToastLocator);
	}
	
	public void goToPage()
	{
		driver.get("https://rahulshettyacademy.com/client/");
	}
	
	//WebElement errorToast =	driver.findElement(By.xpath("//div[contains(@class,'toast-error')]"));
	
	@FindBy(xpath="//div[contains(@class,'toast-error')]")
	WebElement errorToast;
	
	public String getErrorMessage()
	{
		waitForTheWebElementToAppear(errorToast);
		return errorToast.getText();
	}
	
	
}
