package framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;
import org.apache.log4j.Logger;
import java.util.Set;
import java.util.Map;
import java.util.Iterator;

/**
 * This class class has methods for loading properties file.
  */
public class TestUtil  {

	private static Logger logger = Logger.getLogger(TestUtil.class);
	private static boolean systemEnvFlag=true;
	public static Properties properties;

	static {
		loadProperties();
	}

	
	/** Constructor */
	public TestUtil() {
		
	}


	
	/**
	 * Load test.properties
	 */
	public static Properties loadProperties() {

	
		String envType = null;
		Properties properties = new Properties();

		try {

			Map map = System.getenv();
			Set keyss = map.keySet();
			if(systemEnvFlag)
			{
				logger.info("System Env Props:"+keyss);
				systemEnvFlag=false;
			}
			else
			{
				logger.debug("System Env Props:"+keyss);
				systemEnvFlag=false;
			}
			Iterator iterator = keyss.iterator();
			while (iterator.hasNext()) {
				
				String key = (String) iterator.next();
				String value = (String) map.get(key);				
			
				if (key.equals("automation.env")) {
					envType = value;
				}
				}
			String propertiesFilePath = null;
		
			if (envType != null ) {
				if(!envType.equals("")){
					propertiesFilePath = "resources/" + envType
							+ "/config.properties";
				}
			}
			else
				propertiesFilePath = "resources/config.properties";
			
			properties.load(new FileInputStream(propertiesFilePath));

			Enumeration keys = properties.keys();
			String key, value = null;
			while (keys.hasMoreElements()) {
				key = (String) keys.nextElement();
				value = properties.getProperty(key);

				String existingProp = System.getProperty(key);
				
				if (key.equals("automation.env") & envType !=null) {
					properties.setProperty(key, envType);
				}
				
			}

		} catch (Exception ex) {
			logger.error("Unable to load the test.properties files -- " +ex.getMessage());
			ex.printStackTrace();
		}
		return properties;
	}

	
	public static boolean getBooleanValue(String value)
	{
		if(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes"))
			return true;
		else
			return false;
	}
}
