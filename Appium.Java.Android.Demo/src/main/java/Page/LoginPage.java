package Page;

import org.openqa.selenium.WebElement;

import Common.BasePage;
import Common.By;

public class LoginPage extends BasePage {

	public String login = "btn_login";

	public LoginPage() {
	}

	public WebElement loginButton() {
		return find(By.id, "com.tencent.mobileqq:id/btn_login");
	}

	public WebElement registe() {
		return find(By.id, "com.tencent.mobileqq:id/btn_register");
	}

	public WebElement userName() {
		return getViewbyXPathwithcontentdesc("android.widget.EditText", "请输入QQ号码或手机或邮箱");
	}

	public WebElement passWord() {
		return find(By.id, "com.tencent.mobileqq:id/password");
	}

	public WebElement login() {
		return find(By.id, "com.tencent.mobileqq:id/login");
	}

	public WebElement forgetPassWord() {
		return getViewbyXPathwithtext("android.widget.Button", "忘记密码？");
	}

	public WebElement newUser() {
		return getViewbyXPathwithtext("android.widget.Button", "新用户注册");
	}
}
