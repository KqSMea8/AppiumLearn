package Flow;

import org.openqa.selenium.WebElement;

import Page.LoginPage;
import Utils.Log;

public class LoginFlow {
	
	/**
	 * Login with special user info
	 * @param userName
	 * @param passWord
	 */
	public static void Login(String userName, String passWord) {
		LoginPage loginPage = new LoginPage();
		boolean isFirstLoginPage = loginPage.isElementVisable(loginPage.login);
		if(isFirstLoginPage) {
			Log.info("It's the first page for login, need to click Login Button");
			loginPage.loginButton().click();			
		}
		WebElement user = loginPage.userName();
		WebElement password = loginPage.passWord();
		if(user.getText().equalsIgnoreCase(userName)) {
			Log.info("The current user info is same as except, direct login");
		} else {
			Log.info("The current user info is not same as except, Clear the info and login");
			user.clear();
			user.sendKeys(userName);
			password.clear();
			password.sendKeys(passWord);
		}
		loginPage.login().click();			
	}

}
