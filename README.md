# Rajat-Rastogi
This code has API test automation architecture in Java. Jersey client API used to automate web services
1.	Create a Maven Project named TestAPIs 
2.	Create a pom.xml and added all dependencies required for this project.
3.	Create a src>>resource folder which has 2 properties file named config.properties and url.properties.
•	config.properties has environment, database, host and api urls.

4.	Create a TestUtil.java class to load environment details from config.properties file under src>>framework>>util package.
5.	Create an AbstractTestCase.java class which has method definitions of set test file location and load test case data under src>>framework package.
6.	Create a test.csv file to have test data and test case information
7.	Create a TransactionAPIModel.java class to get, set attributes data in API under src>>framework>>models package.
8.	Create a PayloadFormatter.java class to generate payload for APIs from get-set attributes through TransactionAPIModel.java class under src>>framework>> jsonpayloadformatter package.
9.	Create a dbconnection class e.g. MySQLDBAccess.java file under src>>framework>> dbaccess package.
10.	Create a BaseTransactionAPIRestClient .java class to read APIs URL from url.properties file under src>>framework>>ws package.
11.	Create a TransactionAPIRestClient.java class which has test API definitions along with assertions like DB verification, response code verification, response message verification etc under src>>testautomation>>wsclients package. Will use jersey.api library to POST/PUT/GET/DELETE http APIs.
12.	Create a TestTransactionRestAPIs.java class which has API test cases under src>>testautomation>>wsclients>>test package. Test execution priority is defined by TestNG priority annotation and also BeforeTest, AfterTest annotations used for pre-and-post-test execution. Will use jersey.api library to POST/PUT/GET/DELETE http APIs.
13.	Create a test.csv file for API test cases and test data under src>>testautomation>>wsclients>>test package
14.	In pom.xml, specify path of TestTransactionRestAPIs class file and run as Maven test
