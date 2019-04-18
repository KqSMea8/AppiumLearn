package Common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

import Utils.Log;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class InitDriver {

	private String DEVICE_NAME;
	private String PLATFORM_NAME;
	private String PLATFORM_VERSION;
	private String NO_RESET;
	private String APP_PACKAGE;
	private String APP_ACTIVITY;

	private String USERNAME;
	private String PASSWORD;
	
	@SuppressWarnings("rawtypes")
	private AndroidDriver driver;

	public String getUSERNAME() {
		return USERNAME;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}
	
	public String getDEVICE_NAME() {
		return this.DEVICE_NAME;
	}

	public String getPLATFORM_NAME() {
		return this.PLATFORM_NAME;
	}

	public String getPLATFORM_VERSION() {
		return this.PLATFORM_VERSION;
	}

	public String getNO_RESET() {
		return this.NO_RESET;
	}

	public String getAPP_PACKAGE() {
		return this.APP_PACKAGE;
	}

	public String getAPP_ACTIVITY() {
		return this.APP_ACTIVITY;
	}

	/**
	 * Get the configure inform in config.properties file
	 * @throws IOException
	 */
	public void initConfigData() throws IOException {
		Properties properties = new Properties();
		InputStream inputStream = new FileInputStream(".\\Config\\driverConfig.properties");
		properties.load(inputStream);
		Log.info("Get the infomations from the driverConfig.properties.");
		DEVICE_NAME = properties.getProperty("DEVICE_NAME");
		PLATFORM_NAME = properties.getProperty("PLATFORM_NAME");
		PLATFORM_VERSION = properties.getProperty("PLATFORM_VERSION");
		NO_RESET = properties.getProperty("NO_RESET");
		APP_PACKAGE = properties.getProperty("APP_PACKAGE");
		APP_ACTIVITY = properties.getProperty("APP_ACTIVITY");
		
		USERNAME = properties.getProperty("USERNAME");
		PASSWORD = properties.getProperty("PASSWORD");
		inputStream.close();
	}
	
	/**
	 * Navigate to the initial URL set the implicitly wait for 5 second and maximize the window
	 * @return WebDriver
	 */
	@SuppressWarnings("rawtypes")
	public AndroidDriver getDriver() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, PLATFORM_NAME);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, PLATFORM_VERSION);
		capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
		capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, APP_PACKAGE);
		capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, APP_ACTIVITY);
		try {
			driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			callWait(10);
		} catch (MalformedURLException e) {
			Log.error(e.getMessage());
		}
		return driver;
	}
	
	/**
	 * Set the implicitly wait time
	 * @param time
	 */
	public void callWait(int time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

	/**
	 * Forward page
	 */
	public void forward() {
		driver.navigate().forward();
	}
	
	/**
	 * Back page
	 */
	public void back() {
		driver.navigate().back();
	}
	
	/**
	 * Refresh page
	 */
	public void refresh() {
		driver.navigate().refresh();
	}
}
