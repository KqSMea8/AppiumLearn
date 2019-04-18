package Test;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import Common.BasePage;
import Flow.LoginFlow;
import Utils.Log;

public class LoginDemo {
  @Test
  public void Login() {
	  Log.info("Login with special user info");
	  LoginFlow.Login(BasePage.USERNAME, BasePage.PASSWORD);
  }
  @AfterClass
  public void tearnDown() throws Exception {
	  Log.info("Close the driver");
	  BasePage.tearDown();
  }
}
