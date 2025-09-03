package seleniumFramework;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class RegisterUser {

	@Test
	public void userRegister()
	{
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		driver.get("https://rahulshettyacademy.com/client/");
		driver.findElement(By.xpath("//a[contains(@href,'/register')]")).click();
		driver.findElement(By.xpath("//input[@id='firstName']")).sendKeys("Amandeep");
		driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys("Sindhia");
		driver.findElement(By.xpath("//input[@id='userEmail']")).sendKeys("amanyuvi@gmail.com");
		driver.findElement(By.xpath("//input[@id='userMobile']")).sendKeys("9876486932");
		WebElement staticDropDown =	driver.findElement(By.xpath("//select[contains(@class,'custom-select')]"));
		Select dropDown = new Select(staticDropDown);
		dropDown.selectByValue("3: Engineer");
		String dropDownText =	dropDown.getFirstSelectedOption().getText();
		Assert.assertEquals(dropDownText, "Engineer");
		driver.findElement(By.xpath("//input[@value='Female']")).click();
		driver.findElement(By.xpath("//input[@id='userPassword']")).sendKeys("Amnind2$");
		driver.findElement(By.xpath("//input[@id='confirmPassword']")).sendKeys("Amnind2$");
		driver.findElement(By.xpath("//input[@formcontrolname='required']")).click();
		WebElement	register = driver.findElement(By.xpath("//input[@value='Register']"));
		JavascriptExecutor js =	(JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", register);
		driver.quit();
		
	}
}
