package testautomation.wsclients.test;

import java.util.Properties;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import framework.AbstractTestCase;
import testautomation.wsclients.TransactionAPIRestClient;
import com.sun.jersey.api.client.ClientResponse;

public class TestTransactionRestAPIs extends AbstractTestCase{

	public static int testcaseTotal = 0;
	public static final String testName = "Transactions API Feature Tests ";
	public static String resultsStr = "";
	public static String resultsStartStr = "\n********************************\n "+testName+"\n  Test Start \n********************************\n";
	public static String testCaseResultsStr = "";
	public static final String testDescPropKey = "testDescription";
	

	@BeforeTest
	public void beforeTest() {
		System.out.println(resultsStartStr);
	}
	@AfterTest
	public void afterTest() {
		resultsStr = "\n********************************\n Test Results : "+testName+"\n Total testcase: "+ testcaseTotal +"\n";
		System.out.println(resultsStr);
		System.out.println("\n********************************\n");
	}

	//Test Case 1 - Save Transaction API
	@Test(priority = 1, enabled = true)
	public void saveTransaction() throws Exception {
		System.out.println("----------- START CASE: TC:1 (saveTransaction) ----------------");
		Properties prop = loadTestCaseData("TC:1");
		TransactionAPIRestClient request = new TransactionAPIRestClient();
		ClientResponse response = request.saveTransaction(prop);
		request.verifyRestResponseforSaveTransaction(response);
		testCaseResultsStr = "Testcase "+ ++testcaseTotal+": "+prop.getProperty(TestTransactionRestAPIs.testDescPropKey) +" :"+" PASS\n";
		resultsStr = resultsStr + testCaseResultsStr;
		System.out.println(testCaseResultsStr);
	}
	//Test Case 2 - Save Transaction API without Parent ID
	@Test(priority = 2, enabled = true)
	public void saveTransactionwoParent() throws Exception {
		System.out.println("----------- START CASE: TC:2 (saveTransactionWithoutParentId) ----------------");
		Properties prop = loadTestCaseData("TC:2");
		TransactionAPIRestClient request = new TransactionAPIRestClient();
		ClientResponse response = request.saveTransactionwoParent(prop);
		request.verifyRestResponseforSaveTransaction(response);
		testCaseResultsStr = "Testcase "+ ++testcaseTotal+": "+prop.getProperty(TestTransactionRestAPIs.testDescPropKey) +" :"+" PASS\n";
		resultsStr = resultsStr + testCaseResultsStr;
		System.out.println(testCaseResultsStr);
	}
	
	//Test Case 3 - Get Transaction ID based on Type
	@Test(priority = 3, enabled = true)
	public void getTransactionId() throws Exception {
		System.out.println("----------- START CASE: TC:3 (getTransactionId based on type) ----------------");
		Properties prop = loadTestCaseData("TC:3");
		TransactionAPIRestClient request = new TransactionAPIRestClient();
		ClientResponse response = request.getTransactionId(prop);
		request.verifyRestResponseGetTransaction(response);
		testCaseResultsStr = "Testcase "+ ++testcaseTotal+": "+prop.getProperty(TestTransactionRestAPIs.testDescPropKey) +" :"+" PASS\n";
		resultsStr = resultsStr + testCaseResultsStr;
		System.out.println(testCaseResultsStr);
	}
			
	//Test Case 4 - Get Sum based on Transaction ID
	@Test(priority = 4, enabled = true)
	public void getSum() throws Exception {
		System.out.println("----------- START CASE: TC:4 (getSum based on transaction id) ----------------");
		Properties prop = loadTestCaseData("TC:4");
		TransactionAPIRestClient request = new TransactionAPIRestClient();
		ClientResponse response = request.getSum(prop);
		request.verifyRestResponseGetSum(response);
		testCaseResultsStr = "Testcase "+ ++testcaseTotal+": "+prop.getProperty(TestTransactionRestAPIs.testDescPropKey) +" :"+" PASS\n";
		resultsStr = resultsStr + testCaseResultsStr;
		System.out.println(testCaseResultsStr);
	}
			
}

