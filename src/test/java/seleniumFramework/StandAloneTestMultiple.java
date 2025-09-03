package seleniumFramework;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTestMultiple {
	
	//Create an ArrayList for adding all three products
	
	List<String> accessories =	List.of("ZARA COAT 3" , "ADIDAS ORIGINAL" , "IPHONE 13 PRO");

	@Test
	public void submitOrder()
	{
		//Handling Google Prompt -> Change your password
		
		Map<String,Object> prefs = new HashMap<>();
		prefs.put("enabled_credentials_service", false);
		prefs.put("profile.password_manager_enabled_service", false);
		prefs.put("profile.password_manager_leak_detection", false);
		
		ChromeOptions option = new ChromeOptions();
		option.setExperimentalOption("prefs", prefs);
		
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver(option);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		driver.get("https://rahulshettyacademy.com/client/");
		driver.findElement(By.xpath("//input[@id='userEmail']")).sendKeys("happy@gmail.com");
		driver.findElement(By.xpath("//input[@id='userPassword']")).sendKeys("happy123");
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		
		//WebDriver Wait for dynamic UI Elements
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		
		By loginToastLocator =	By.xpath("//div[contains(@class,'ng-trigger-flyInOut')]/div[@aria-label='Login Successfully']");
		WebElement loginToast =	wait.until(ExpectedConditions.visibilityOfElementLocated(loginToastLocator));
		Assert.assertTrue(loginToast.isDisplayed());
		Assert.assertTrue(loginToast.getText().equals("Login Successfully"));
		
		List<WebElement> elementList =	driver.findElements(By.xpath("//div[contains(@class,'col-sm-10')]"));
		
		By spinnerLocator =	By.xpath("//div[contains(@class,'ng-trigger-fadeIn')]");
		
		for(String product : accessories)
		{
		WebElement selectProduct =	elementList.stream().filter(element -> element.findElement(By.xpath(".//h5/b")).getText().equals(product)).findFirst().orElse(null);
		
		if(selectProduct != null)
		{
			WebElement addProduct =	selectProduct.findElement(By.xpath(".//button[@class='btn w-10 rounded']"));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(spinnerLocator));
			addProduct.click();
		}
		
		}
		
		By confirmationToastLocator =	By.xpath("//div[contains(@class,'ng-trigger-flyInOut')]/div[@aria-label='Product Added To Cart']");
		WebElement confirmationToast =	wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationToastLocator));
		Assert.assertTrue(confirmationToast.isDisplayed());
		Assert.assertTrue(confirmationToast.getText().equals("Product Added To Cart"));
		
		//Cart Section
		
		WebElement cart =	driver.findElement(By.xpath("//li/button[@routerlink='/dashboard/cart']"));
		cart.click();
		
		//Validate if same products have been added to the Cart
		
		List<WebElement> cartProduct =	driver.findElements(By.xpath("//div[@class='cartSection']"));
		
		for(String product : accessories)
		{
			Boolean productMatch =	cartProduct.stream().anyMatch(cartP -> cartP.findElement(By.xpath(".//h3")).getText().equals(product));
			
			Assert.assertTrue(productMatch, "Product did not matched");
			
		}
		
		WebElement checkout =	driver.findElement(By.xpath("//li/button[@class='btn btn-primary']"));
		JavascriptExecutor js =	(JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", checkout);
		
		//Checkout Section
		
		driver.findElement(By.xpath("//input[@placeholder='Select Country']")).sendKeys("ind");
		List<WebElement> countryList =	driver.findElements(By.xpath("//button[contains(@class,'ta-item')]"));
		WebElement selectCountry =	countryList.stream().filter(country -> country.findElement(By.xpath(".//span")).getText().equalsIgnoreCase("India")).findFirst().orElse(null);
		if(selectCountry != null)
		{
			selectCountry.click();
		}
		
		driver.findElement(By.xpath("//a[contains(@class,'action__submit')]")).click();
		
		By orderConfirmationLocator =	By.xpath("//div[contains(@class,'ng-trigger-flyInOut')]/div[@aria-label='Order Placed Successfully']");
		
		WebElement orderToast =	wait.until(ExpectedConditions.visibilityOfElementLocated(orderConfirmationLocator));
		
		Assert.assertTrue(orderToast.isDisplayed());
		Assert.assertTrue(orderToast.getText().equals("Order Placed Successfully"));
		
		//Validate orders are displayed in orders page
		
		List<WebElement> orderIDs =	driver.findElements(By.xpath("//tr[3]//td[@class='em-spacer-1']/label"));
		
		List<String> orderIDText =	orderIDs.stream().map(order -> order.getText().replace("|", "").trim()).collect(Collectors.toList());
		
		driver.findElement(By.xpath("//li/button[@routerlink='/dashboard/myorders']")).click();
		
		List<WebElement> orders =	driver.findElements(By.xpath("//tr[@class='ng-star-inserted']"));
		
		for(String myOrder : orderIDText)
		{
			Boolean orderMatch =	orders.stream().anyMatch(orderP -> orderP.findElement(By.xpath(".//th")).getText().trim().equals(myOrder));
			
			Assert.assertTrue(orderMatch , "Order did not match");
		}
		
		driver.quit();
		
		
		
		
	}
}
