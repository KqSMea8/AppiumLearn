package Utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log {

	private static Logger Log = Logger.getLogger(Log.class.getName());

	public static void info(String message) {
		PropertyConfigurator.configure(".\\Config\\log4j.properties");
		Log.info(message);
	}

	public static void warn(String message) {
		PropertyConfigurator.configure(".\\Config\\log4j.properties");
		Log.warn(message);
	}

	public static void error(String message) {
		PropertyConfigurator.configure(".\\Config\\log4j.properties");
		Log.error(message);
	}

	public static void debug(String message) {
		PropertyConfigurator.configure(".\\Config\\log4j.properties");
		Log.debug(message);
	}

}