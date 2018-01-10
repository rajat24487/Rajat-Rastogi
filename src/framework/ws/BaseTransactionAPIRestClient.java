package framework.ws;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import framework.util.TestUtil;


public class BaseTransactionAPIRestClient {

	protected static final Logger LOG = Logger.getLogger(BaseTransactionAPIRestClient.class);
	protected static String HOST = "";
	protected static String qa_url_saveTransaction= "";
	protected static String qa_url_getTransaction= "";
	protected static String qa_url_getSum= "";
	protected static Properties configProp = null;
	protected static String configPropFile = "config.properties";
	protected static Boolean debugFlag =false;
	
	protected static final String HOST_PropKey = "host";
	
	protected static final String debugFlag_PropKey = "debugFlag";

	protected static final String qa_url_saveTransaction_PropKey = "saveTransaction";
	protected static final String qa_url_getTransaction_PropKey = "getTransaction";
	protected static final String qa_url_getSum_PropKey = "getSum";

	static {
		configProp = new Properties();
		try {
			String propFilePath = null;
			
			propFilePath = "resources/" + configPropFile;
			
			configProp.load(new FileInputStream(propFilePath));
			HOST = configProp.getProperty(HOST_PropKey);
			debugFlag=Boolean.valueOf(configProp.getProperty(debugFlag_PropKey));
						
			qa_url_saveTransaction = configProp.getProperty(qa_url_saveTransaction_PropKey);
			qa_url_getTransaction = configProp.getProperty(qa_url_getTransaction_PropKey);
			qa_url_getSum = configProp.getProperty(qa_url_getSum_PropKey);
				
			
		} catch (Exception e) {
			LOG.error("Fatal error:" + e.getMessage());
		}
	}

}
