package seleniumFramework.PageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumFramework.AbstractComponents.AbstractComponents;

public class ProductCataloguePage extends AbstractComponents {

	WebDriver driver;
	
	public ProductCataloguePage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	
	
	//List<WebElement> elementList =	driver.findElements(By.xpath("//div[contains(@class,'col-sm-10')]"));
	
	By confirmationToastLocator =	By.xpath("//div[contains(@class,'ng-trigger-flyInOut')]/div[@aria-label='Product Added To Cart']");
	
	By addToCart =	By.xpath(".//button[@class='btn w-10 rounded']");
	
	
	By spinner =	By.tagName("ngx-spinner");
	

	By spinnerLocator =	By.xpath("//div[contains(@class,'ng-trigger-fadeIn')]");
	
	//PageFactory Model
	
	@FindBy(xpath="//div[contains(@class,'col-sm-10')]")
	List<WebElement> elementList;
	
	public List<WebElement> getProductList()
	{
		return elementList;
	}
	
	
	public List<WebElement> getProductsByName(List<String> accessories)
	{
		List<WebElement> matchProducts = new ArrayList<>();
		
		for(String product : accessories)
		{
		WebElement selectProduct =	getProductList().stream().filter(element -> element.findElement(By.xpath(".//h5/b")).getText().equals(product)).findFirst().orElse(null);
		
		if(selectProduct != null)
		{
			matchProducts.add(selectProduct);
		}
		}
		return matchProducts;
	
	}
	
	
	public WebElement addProductsToCart(List<String> accessories)
	{
		List<WebElement> matchProducts =	getProductsByName(accessories);
		
		for(WebElement product : matchProducts)
		{
			WebElement addCart =	product.findElement(addToCart);
			waitForElementToAppear(spinner);
			waitForTheElementToDisappear(spinnerLocator);
			addCart.click();
		}
		
		return waitForTheElementToAppear(confirmationToastLocator);
	}
	
	
	
	
	//WebElement checkBox =	driver.findElement(By.xpath("//div[@class='form-group ng-star-inserted']/label[text()='laptops']"));
	
	@FindBy(xpath="//label[text()='laptops']/preceding-sibling::input[@type='checkbox']")
	WebElement checkBox;
	
	//WebElement errorToast =	driver.findElement(By.xpath("//div[contains(@class,'toast-error')]"));
	
	@FindBy(xpath="//div[contains(@class,'toast-error')]")
	WebElement errorToast;
	
	public String searchLaptop()
	{
		//waitForTheWebElementToAppear(checkBox);
		clickElement(checkBox);
		waitForTheWebElementToAppear(errorToast);
		return errorToast.getText();
	}
	
	
}
	
	
	/*for(String product : accessories)
	{
	WebElement selectProduct =	elementList.stream().filter(element -> element.findElement(By.xpath(".//h5/b")).getText().equals(product)).findFirst().orElse(null);
	
	if(selectProduct != null)
	{
		WebElement addProduct =	selectProduct.findElement(By.xpath(".//button[@class='btn w-10 rounded']"));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(spinnerLocator));
		addProduct.click();
	}*/
	

