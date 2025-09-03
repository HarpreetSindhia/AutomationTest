package seleniumFramework;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class StandAloneTest {
	
	String productName = "ADIDAS ORIGINAL";

	@Test
	public void purchaseProduct()
	{
		
		//Handling Google Prompts -> Change your password
		
		//Create Preferences Map
		
		Map<String,Object> prefs = new HashMap<>();
		prefs.put("credentials_enabled_service", false);
		prefs.put("profile.password_manager_enabled_service", false);
		prefs.put("profile.password_manager_leak_detection", false);
		
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
		
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		driver.get("https://rahulshettyacademy.com/client/");
		driver.findElement(By.xpath("//input[@id='userEmail']")).sendKeys("happy@gmail.com");
		driver.findElement(By.xpath("//input[@id='userPassword']")).sendKeys("happy123");
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		
		//Validate if toast message for log in appeared
		
		WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(5));
		
		
		//Getting all product cards into a List
		
		WebElement loginToast =	driver.findElement(By.xpath("//div[contains(@class,'ng-trigger-flyInOut')]//div[@aria-label='Login Successfully']"));
		wait.until(ExpectedConditions.visibilityOf(loginToast));
		
		Assert.assertTrue(loginToast.isDisplayed(), "Login Toast not appeared");
		Assert.assertEquals(loginToast.getText(), "Login Successfully");
		
		
		List<WebElement> elementList =	driver.findElements(By.xpath("//div[contains(@class,'col-lg-4')]"));
		
		WebElement productSelect =	elementList.stream().filter(element -> element.findElement(By.xpath(".//h5/b")).getText().equalsIgnoreCase(productName)).findFirst().orElse(null);
		
		if(productSelect!= null)
		{
			WebElement addProduct =	productSelect.findElement(By.xpath(".//button[@class='btn w-10 rounded']"));
			addProduct.click();
		}
		
		//Validate if confirmation message appeared
		
		
		WebElement confirmationMessage =	driver.findElement(By.xpath("//div[contains(@class,'ng-trigger')]//div[@aria-label='Product Added To Cart']"));
		wait.until(ExpectedConditions.visibilityOf(confirmationMessage));
		
		Assert.assertTrue(confirmationMessage.isDisplayed(), "Confirmation Message is not visible");
		
		Assert.assertEquals(confirmationMessage.getText(),"Product Added To Cart");
		
		
		//Cart Section
		
		JavascriptExecutor js =	(JavascriptExecutor)driver;
		WebElement cart =	driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']"));
		js.executeScript("arguments[0].click();", cart);
		
		
		//Validate if same product added to View Cart
		
		List<WebElement> cartProducts =	driver.findElements(By.xpath("//div[@class='cartSection']"));
		
		Boolean productMatch =	cartProducts.stream().anyMatch(cartProduct -> cartProduct.findElement(By.xpath(".//h3")).getText().equalsIgnoreCase(productName));
		
		Assert.assertTrue(productMatch, "Product not matched");
		
		//Checkout Section
		
		driver.findElement(By.xpath("//li/button[@class='btn btn-primary']")).click();
		
		driver.findElement(By.xpath("//input[@placeholder='Select Country']")).sendKeys("Ind");
		
		List<WebElement> countryList =	driver.findElements(By.xpath("//button[contains(@class,'ta-item')]"));
		
		WebElement countrySelect =	countryList.stream().filter(country -> country.findElement(By.tagName("span")).getText().equalsIgnoreCase("India")).findFirst().orElse(null);
		
		if(countrySelect != null)
		{
			countrySelect.click();
		}
		
		driver.findElement(By.xpath("//a[contains(@class,'btnn action__submit')]")).click();
		
		WebElement orderToast =	driver.findElement(By.xpath("//div[contains(@class,'ng-trigger-flyInOut')]//div[@aria-label='Order Placed Successfully']"));
		
		Assert.assertTrue(orderToast.isDisplayed(), "Order Toast did not appeared");
		
		Assert.assertEquals(orderToast.getText(), "Order Placed Successfully");
		
		//Orders Page
		
		
		
		String orderID =	driver.findElement(By.xpath("//table//label[@class='ng-star-inserted']")).getText().replace("|", "").trim();
		
		System.out.println(orderID);
		
		driver.findElement(By.xpath("//li//button[@routerlink='/dashboard/myorders']")).click();
		
		By productLocator = 	By.xpath("//tr[@class='ng-star-inserted']");
		
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productLocator));
		
		List<WebElement> productOrderID =	driver.findElements(productLocator);
		
		Boolean orderMatch =	productOrderID.stream().anyMatch(order -> order.findElement(By.xpath(".//th")).getText().trim().equalsIgnoreCase(orderID));
		
		//System.out.println(order);
		
		Assert.assertTrue(orderMatch);
		
		
		
			/*List<WebElement> orderID2 =	driver.findElements(By.xpath("//table//tr[3]//label"));
			
			List<String> orderIDText =	orderID2.stream().map(element -> element.getText().replace("|", "").trim()).collect(Collectors.toList());
			
			driver.findElement(By.xpath("//li/button[@routerlink='/dashboard/myorders']")).click();
			
				
			List<WebElement> orderPID =	driver.findElements(By.xpath("//table//tbody//tr"));
			
			for(String myOrder : orderIDText)
			{
				Boolean matchFoundID =	orderPID.stream().anyMatch(element -> 
				{
					String rowOrderID =	element.findElement(By.xpath(".//th")).getText().trim();
					return rowOrderID.equals(myOrder);
				});
				
			
				Assert.assertTrue(matchFoundID);
			}*/
			
			
			
			
		
		
		
		
		
		
		
		
	}
}
