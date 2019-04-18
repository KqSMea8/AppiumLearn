package Common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import Utils.Log;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class BasePage {

	public static String USERNAME;
	public static String PASSWORD;
	
	@SuppressWarnings("rawtypes")
	public static AndroidDriver driver = init();
	public static Duration duration = Duration.ofSeconds(1);

	@SuppressWarnings("rawtypes")
	public static AndroidDriver init() {
		InitDriver driver = new InitDriver();
		try {
			driver.initConfigData();
			USERNAME = driver.getUSERNAME();
			PASSWORD = driver.getPASSWORD();
		} catch (IOException e) {
			Log.error(e.getMessage());
		}
		return driver.getDriver();
	}

	public WebElement find(By by, String locator) {
		if(waitFor(locator)) {
			return driver.findElement(by.toString(), locator);
		} else {
			String[] methName = locator.split("/");
			takeScreenShot(methName[methName.length - 1]);
			Log.error("Element：" + locator + "Not Exist; ");
			return null;
		}
	}
	
	
	/**
	 * 隐藏键盘
	 * 
	 * @param driver
	 */
	public static void closeKeyBoard() {
		try {
			driver.hideKeyboard();// 隐藏键盘
		} catch (WebDriverException ex) {
			Log.debug("<Keyboard>Soft keyboard not present, cannot hide keyboard!!!");
		}
	}
	
	/***
	 * Switch to WebView to find elemen*/
	@SuppressWarnings("unchecked")
	public static void switchtoWeb() {
		try {
			Set<String> contextNames = driver.getContextHandles();
			for (String contextName : contextNames) {
				// 用于返回被测app是NATIVE_APP还是WEBVIEW，如果两者都有就是混合型App
				if (contextName.contains("WEBVIEW") || contextName.contains("webview")) {
					driver.context(contextName);
					System.out.println("跳转到web页 开始操作web页面");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public void swipeToUp() {
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		TouchAction action1 = new TouchAction(driver).press(PointOption.point(width / 2, height * 3 / 4))
				.waitAction(WaitOptions.waitOptions(duration)).moveTo(PointOption.point(width / 2, height / 4))
				.release();
		action1.perform();
	}

	@SuppressWarnings("rawtypes")
	public void swipeToDown() {
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		TouchAction action2 = new TouchAction(driver).press(PointOption.point(width / 2, height / 4))
				.waitAction(WaitOptions.waitOptions(duration)).moveTo(PointOption.point(width / 2, height * 3 / 4))
				.release();
		action2.perform();
	}

	@SuppressWarnings("rawtypes")
	public void swipeToLeft() {
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		TouchAction action3 = new TouchAction(driver).press(PointOption.point(width * 3 / 4, height / 2))
				.waitAction(WaitOptions.waitOptions(duration)).moveTo(PointOption.point(width / 4, height / 2))
				.release();
		action3.perform();
	}

	@SuppressWarnings("rawtypes")
	public void swipeToRight() {
		int width = driver.manage().window().getSize().width;
		int height = driver.manage().window().getSize().height;
		TouchAction action4 = new TouchAction(driver).press(PointOption.point(width / 4, height / 2))
				.waitAction(WaitOptions.waitOptions(duration)).moveTo(PointOption.point(width * 3 / 4, height / 2))
				.release();
		action4.perform();
	}

	/***
	 * xpath根据content-desc查找元素
	 * @param view的类型
	 * @param content-desc的内容
	 * @return
	 */
	public static WebElement getViewbyXPathwithcontentdesc(String view, String name) {
		return driver.findElementByXPath("//" + view + "[contains(@content-desc,'" + name + "')]");
	}

	/***
	 * xpath根据text查找元素
	 * @param view的类型
	 * @param text的内容
	 * @return
	 */
	public static WebElement getViewbyXPathwithtext(String view, String name) {
		return driver.findElementByXPath("//" + view + "[contains(@text,'" + name + "')]");
	}

	/**
	 * @method: 通过想点击文字的控件id和顺序index点击文字，当第一屏未找到文字会向下滚动查找，若滚到底扔未发现文字断言失败；方法结束后休眠1s
	 * @param: String id 想点击文字的id int index 顺序
	 * @param index
	 */
	public void clickTextById(String id, int index) {

		String page1;
		String page2;
		int result = 0;

		do {
			page1 = driver.getPageSource();
			if (page1.contains(id)) {
				if (index == 0) {
					WebElement imageElement = driver
							.findElementByXPath("//android.widget.TextView[contains(@resource-id,'" + id + "')]");
					Log.info("Text " + id + " found.");
					imageElement.click();
					Log.info("Text " + id + " clicked.");
					result = 1;
				} else if (index > 0) {
					@SuppressWarnings("unchecked")
					List<WebElement> imageElements = driver
							.findElementsByXPath("//android.widget.TextView[contains(@resource-id,'" + id + "')]");
					WebElement imageElement = imageElements.get(index);
					Log.info("Text " + id + " found.");
					imageElement.click();
					Log.info("Text " + id + " clicked.");
					result = 1;
				}
				break;
			} else {
				this.swipeToDown();
				page2 = driver.getPageSource();
			}
		} while (!page1.equals(page2));

		if (result == 0) {
			Log.error("Clicking Text " + id + " failed.");
		}
	}

	public void takeScreenShot(String methodName) {
		File srcFile = driver.getScreenshotAs(OutputType.FILE);
		SimpleDateFormat df = new SimpleDateFormat("MM_dd_HH_mm");
		String fileName = methodName + "_" + df.format(new Date()) + ".png";
		try {
			FileUtils.copyFile(srcFile, new File(".\\Pic\\" + fileName));
		} catch (IOException e) {
			Log.error(e.getMessage());
		}
		Log.info("Get the ScreenShot: " + fileName);
	}

	public Boolean isElementVisable(String list) {
		boolean isVisable = false;
		String pageSource = driver.getPageSource();
		if (!list.contains(",")) {
			if (pageSource.contains(list)) {
				Log.debug(list + " is found.");
				isVisable = true;
			} else {
				Log.error("Searching " + list + " failed.");
			}
		} else {
			String elements[] = list.split(",");
			for (int i = 0; i <= ((elements.length) - 1); i++) {
				if (elements[i].contains(" ")) {
					elements[i] = elements[i].trim();
				}
				if (pageSource.contains(elements[i])) {
					Log.debug(elements[i] + " is found.");
					isVisable = true;
				} else {
					Log.error("Searching " + elements[i] + " failed.");
				}
			}
		}
		return isVisable;
	}
	
	public Boolean isElementVisable(String list, Boolean isTakeScreenShot) {
		boolean isVisable = false;
		String pageSource = driver.getPageSource();
		if (!list.contains(",")) {
			if (pageSource.contains(list)) {
				Log.debug(list + " is found.");
				isVisable = true;
			} else {
				if(isTakeScreenShot) {
					takeScreenShot("FAILURE(search)_" + list);					
				}
				Log.error("Searching " + list + " failed.");
			}
		} else {
			String elements[] = list.split(",");
			for (int i = 0; i <= ((elements.length) - 1); i++) {
				if (elements[i].contains(" ")) {
					elements[i] = elements[i].trim();
				}
				if (pageSource.contains(elements[i])) {
					Log.debug(elements[i] + " is found.");
					isVisable = true;
				} else {
					if(isTakeScreenShot) {
						takeScreenShot("FAILURE(search)_" + list);						
					}
					Log.error("Searching " + elements[i] + " failed.");
				}
			}
		}
		return isVisable;
	}

	/***
	 * 根据UIautomator底层方法得到对应desc的view
	 * 
	 * @param desc名
	 * @return View
	 */
	public static WebElement getViewbyUidesc(String name) {
		return driver.findElementByAndroidUIAutomator("new UiSelector().descriptionContains(\"" + name + "\")");
	}

	/***
	 * 根据UIautomator底层方法得到对应text的view
	 * 
	 * @param text名
	 * @return View
	 */
	public static WebElement getViewbyUitext(String name) {
		return driver.findElementByAndroidUIAutomator("new UiSelector().textContains(\"" + name + "\")");
	}

	/*
	 * @method: 比sleep更有客观的延迟方法，在一定时间内等待某元素在屏幕中出现。
	 * 
	 * @param: String target 等待的目标 int time 等待时间，单位为s，必须是1的正整数倍
	 */
	public boolean waitFor(String target, int time) {
		try {
			if (!(time >= 0)) {
				Log.error("waitFor: Parameter ERROR!");
			}
			String result = null;
			String page = driver.getPageSource();
			Log.debug("Waiting for " + target + " ...");

			for (int i = 1; i <= time * 1000; i++) {
				if (page.contains(target)) {
					result = "Succeeded.";
					Log.info(result);
					break;
				} else {
					page = driver.getPageSource();
				}
			}
		} catch (Exception e) {
			Log.error("Element：" + target + "Not Exist; " + e.getMessage());
			return false;
		}
		return true;
	}

	/*
	 * @method: 比sleep更有客观的延迟方法，在一定时间内等待某元素在屏幕中出现。
	 */
	public boolean waitFor(String target) {
		try {
			String result = null;
			String page = driver.getPageSource();
			Log.debug("Waiting for " + target + " ...");

			for (int i = 1; i <= 10 * 1000; i++) {
				if (page.contains(target)) {
					result = "Succeeded.";
					Log.debug(result);
					break;
				} else {
					page = driver.getPageSource();
				}
			}
		} catch (Exception e) {
			Log.error("Element：" + target + "Not Exist; " + e.getMessage());
			return false;
		}
		return true;
	}

	public static void tearDown() throws Exception {
		if (null != driver) {
			driver.quit();
		}
	}
}
