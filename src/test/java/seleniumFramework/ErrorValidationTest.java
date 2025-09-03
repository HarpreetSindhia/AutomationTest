package seleniumFramework;

import org.testng.Assert;
import org.testng.annotations.Test;

import seleniumFramework.testComponents.BaseTest;

//Negative Scenario implementation

public class ErrorValidationTest extends BaseTest {

	@Test(groups= {"ErrorHandling"})
	public void loginErrorValidation()
	{
		landingPage.loginApplication("happy@gmail.com", "happu123");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
	}
}
