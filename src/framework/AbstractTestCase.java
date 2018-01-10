package framework;

import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;

import framework.util.TestUtil;

import au.com.bytecode.opencsv.CSVReader;

public class AbstractTestCase {

	
	public String testDataFilePath = "";
	
	private static Logger logger = Logger.getLogger(AbstractTestCase.class);
	private static boolean automationEnvPrintFlag=true;
	public String testFileName = "test.csv";
	
	final public String testIDColName = "TCID";

	
	
	static {
		initializeLogger();
	}

	@BeforeClass
	public void setup() {

	}


	public void setTestDataFilePath(){
		URL location =null;
		
		String testFile= testFileName;
	
		if(automationEnvPrintFlag)
		{
			logger.info("automation.env :"+TestUtil.loadProperties().getProperty("automation.env"));
			automationEnvPrintFlag=false;
		}
		else
		{
			logger.debug("automation.env :"+TestUtil.loadProperties().getProperty("automation.env"));
			automationEnvPrintFlag=false;
		}

		if(TestUtil.loadProperties().getProperty("automation.env")!=null){

			testFile = TestUtil.loadProperties().getProperty("automation.env").concat("-").concat(testFileName);
		
		}
		logger.debug("TestFile Name="+testFile);
	
		location = this.getClass().getResource(testFile);
		testDataFilePath = location.getPath();
		testDataFilePath = testDataFilePath.replace("%20", " ");
		
		}
	
	public Properties loadTestCaseData(String testCaseID) {
		Properties prop = null;

		try {
			setTestDataFilePath();
			logger.debug("testdataFile:" + testDataFilePath);
			CSVReader csvReader = new CSVReader(
					new FileReader(testDataFilePath));
			String[] row = null;
			int rCount = 0;
			int pCount = 0;
			String[] pRow = null;

			while ((row = csvReader.readNext()) != null) {
				rCount++;
				if (rCount == 1) {
					pCount = row.length;
					pRow = row;
				} else {
					if (row[0].equalsIgnoreCase(testCaseID)) {
//						System.out.println("Co,mingggggg");
						prop = new Properties();
						for (int ind = 0; ind < pCount; ind++) {
//							System.out.println("Ind:"+ind);
							prop.put(pRow[ind].trim(), row[ind].trim());
//							System.out.println("adding properties:"+prop);
						}
					}
				}
			}
			
			csvReader.close();

		} catch (Exception e) {
			System.err.println(e.getMessage());
//			logger.error("Test data file not found:");
		}
		return prop;
	}
	
	
	public List<Object> getTestCaseIdList() {
		List<Object> testCaseIdList = new ArrayList<Object>();
		try {
			setTestDataFilePath();
			logger.debug("testdataFile:" + testDataFilePath);
			CSVReader csvReader = new CSVReader(new FileReader(testDataFilePath));
			String[] row = null;
			int rCount = 0;
			int pCount = 0;
			String[] pRow = null;

			while ((row = csvReader.readNext()) != null) {
				rCount++;
				if (rCount == 1) {
					pCount = row.length;
					pRow = row;
				} else {
					testCaseIdList.add(row[0].trim());
				}
			}
			csvReader.close();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Test data file not found:");
		}
		return testCaseIdList;
	}


	
	/**
	 * initialize the log4j.properties
	 * 
	 */
	public static void initializeLogger() {
		try {
			// used to load the log file
			PropertyConfigurator
					.configureAndWatch("resources/log4j.properties");
			logger.info("Logging initialised...");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.ERROR, "Exception while loading logger properties");
		}
	}
	
}