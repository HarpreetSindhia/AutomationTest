package seleniumFramework.testComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import seleniumFramework.PageObjects.LandingPage;

public class BaseTest {
	
	public WebDriver driver;
	public LandingPage landingPage;

	
	@Test
	public WebDriver initializeDriver() throws IOException
	{
		
		//Handling Google Prompt -> Change your password
		
		Map<String,Object> prefs = new HashMap<>();
		prefs.put("enabled_credentials_service", false);
		prefs.put("profile.password_manager_enabled_service", false);
		prefs.put("profile.password_manager_leak_detection", false);
				
				
		
		Properties properties = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\seleniumFramework\\resources\\GlobalData.properties");
		properties.load(fis);
		
		String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : properties.getProperty("browser");
		
		if(browserName .equalsIgnoreCase("chrome"))
		{
			ChromeOptions option = new ChromeOptions();
			option.setExperimentalOption("prefs", prefs);
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(option);
		}
		else if(browserName.equalsIgnoreCase("firefox"))
		{
			FirefoxOptions option = new FirefoxOptions();
			for(Map.Entry<String, Object> entry : prefs.entrySet())
			{
				option.addPreference(entry.getKey(), entry.getValue());
				
			}
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver(option);
		}
		else if(browserName.equalsIgnoreCase("edge"))
		{
			EdgeOptions option = new EdgeOptions();
			option.setExperimentalOption("prefs", prefs);
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver(option);
		}

		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
		return driver;
	}
	
	
	public List<HashMap<String,Object>> getJsonDataToMap(String filePath) throws IOException
	{
		//Json to String
		String jsonContent =	FileUtils.readFileToString(new File(filePath),StandardCharsets.UTF_8);
		
		//String -> HashMap -> Jackson Dependency
		
		
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String,Object>> data =	mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,Object>>>(){});
		
		return data;
		
	}
	
	
	public String getScreenshot(String testCaseName , WebDriver driver) throws IOException
	{
		TakesScreenshot ts =	(TakesScreenshot)driver;
		File src =	ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir")+"//reports"+testCaseName+"//.png");
		FileUtils.copyFile(src, file);
		return System.getProperty("user.dir")+"//reports"+testCaseName+"//.png";
	}
	
	
	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException
	{
		driver =	initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goToPage();
		return landingPage;
	}
	
	@AfterMethod(alwaysRun=true)
	public void closeDriver()
	{
		driver.quit();
	}
}
