package framework.dbaccess;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.testng.Assert;



import framework.util.TestUtil;


/**
 * Class to connect to MySQL DB
 * 
 * 
 * @author saurabh.pandey@aeris.net
 *
 */

public class MySQLDBAccess {
	static MySQLDBAccess mySqlDbAccessInstance = null;
	
	String dbHost;
	String dbPort;
	String database;
	String mySqlDriver = "com.mysql.jdbc.Driver";
	String dbUser;
	String dbPassword;
	String dbURL;
	String DBConfigFileName = "dbConfig.properties";
	Connection connMySQLDB = null;
	Statement stmt;
	ResultSet rs;
	
	public MySQLDBAccess(){
		String dbConfigFilePath= "resources/dbConfig.properties";
		String env = TestUtil.loadProperties().getProperty("automation.env");
		if(env!=null){
			if(!env.equals(""))
				dbConfigFilePath = "resources/"+env+"/dbConfig.properties";
		}
		
		
		Properties dbConfProperties = new Properties();
		try {
			dbConfProperties.load(new FileInputStream(dbConfigFilePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		setDbHost(dbConfProperties.getProperty("mysql.host"));
		setDbPort(dbConfProperties.getProperty("mysql.port"));
		setDbUser(dbConfProperties.getProperty("mysql.user"));
		setDbPassword(dbConfProperties.getProperty("mysql.password"));
		setDatabase(dbConfProperties.getProperty("mysql.database"));
	}
	
	public static MySQLDBAccess getMySQLDBInstance(){
		if(mySqlDbAccessInstance==null)
			mySqlDbAccessInstance = new MySQLDBAccess();
		return mySqlDbAccessInstance;
	}
	
	
	public void getMySQLDBconnection() {

		try {
			Class driver_class =Class.forName(mySqlDriver);
			Driver driver = (Driver) driver_class.newInstance();
			DriverManager.registerDriver(driver);
			connMySQLDB = DriverManager.getConnection(getMySQLDBURL()+"/"+database+"?"+"user="+dbUser+"&password="+dbPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	private String getMySQLDBURL() {
		dbURL = "jdbc:mysql://" + dbHost + ":" + dbPort;
		return dbURL;
	}
	
	public String getDbHost() {
		return dbHost;
	}

	public void setDbHost(String dbHost) {
		this.dbHost = dbHost;
	}

	public String getDbPort() {
		return dbPort;
	}

	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}
	
	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	
	public void closeConnection() {

		if (connMySQLDB != null) {
			try {
				stmt.close();
				connMySQLDB.close();
				connMySQLDB = null;
				System.out.println("******MySQL DB connection closed*****");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ResultSet executeQuery(String query) {
		try {
			stmt = connMySQLDB.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return rs;
	}
	//method added to verify package creation of Product Catalog in DB
	public void verifyPackageinDB(String sku, String package_name) {
		String query =null;
		MySQLDBAccess mySqlDBAccess = MySQLDBAccess.getMySQLDBInstance();
		try{
		
		mySqlDBAccess.getMySQLDBconnection();
		query="select * from BUNDLE where SKU='"+sku+"' and NAME='"+package_name+"'";
		
		ResultSet resultset = mySqlDBAccess.executeQuery(query);
		resultset.next();
		 } catch (Exception e) {
             Assert.fail("Execption while verifying rest api response.  " + e.getMessage());
}
finally {
	mySqlDBAccess.closeConnection();
}
		
	}
	public List<List<String>> getReportDataByAccountId(String accountId){
		List<List<String>> dbReportOutput = new ArrayList<List<String>>();
		MySQLDBAccess mySqlDBAccess = MySQLDBAccess.getMySQLDBInstance();
		mySqlDBAccess.getMySQLDBconnection();
		String query = "SELECT status_start_date,last_modified_date,services_blocked,device_id,primary_min AS primary_min,account_id,esn meid,msisdn,imsi,CASE application_type WHEN 'F' THEN 'Fixed' WHEN 'M' THEN 'Mobile' ELSE 'UNKNOWN' END AS application_type,app_hw_version,app_fw_version,reportgroup,custom_field_1,custom_field_2,custom_field_3,custom_field_4,custom_field_5,active_date,iccid ,ELT(device_status, NULL, 'Provisioned', NULL, 'Bill', 'Suspended', NULL, 'Cancelled') device_status,rate_plan,service_name,mfr_esn,pool_name,fw_version,hw_version,device_name,current_location,ip_address,hex_meid,pending_rate_plan ,pending_pool_name,technology FROM DEVICE_COMB WHERE account_id="+accountId+";";
		ResultSet resultset = mySqlDBAccess.executeQuery(query);
		
		try {
			int numcols = resultset.getMetaData().getColumnCount();
			
			while(resultset.next()){
				List <String> row = new ArrayList<String>(numcols);
				for (int i=1; i<= numcols; i++) {  
		            row.add(resultset.getString(i));
		        }
				dbReportOutput.add(row); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		mySqlDBAccess.closeConnection();
		
		return dbReportOutput;
		
	}
	
	
	public List<List<String>> getDeviceSummaryReportDataFromDB(String accountId,String groupBy){
		String query =null;
		List<List<String>> dbReportOutput = new ArrayList<List<String>>();
		MySQLDBAccess mySqlDBAccess = MySQLDBAccess.getMySQLDBInstance();
		mySqlDBAccess.getMySQLDBconnection();
		
		if(groupBy.equals("POOL_NAME"))
			query="SELECT technology AS PRODUCT,pool_name as POOL_NAME,COUNT(*) AS Device_Count FROM DEVICE_COMB WHERE account_id="+accountId+" GROUP BY technology,pool_name";
		else if(groupBy.equals("RATE_PLAN"))
			query="SELECT technology AS PRODUCT,rate_plan AS RATE_PLAN,COUNT(*) AS Device_Count FROM DEVICE_COMB WHERE account_id="+accountId+" GROUP BY technology,rate_plan";
		else if(groupBy.equals("REPORT_GROUP"))	
			query="SELECT technology AS PRODUCT,reportgroup  as REPORT_GROUP,COUNT(*) AS Device_Count FROM DEVICE_COMB WHERE account_id="+accountId+" GROUP BY technology,reportgroup";
		else if(groupBy.equals("STATUS"))
			query="SELECT technology AS PRODUCT, ELT(device_status, NULL, 'PROVISION', NULL, 'BILL', 'SUSPEND', NULL, 'CANCEL') DEVICE_STATUS,COUNT(*) AS Device_Count FROM DEVICE_COMB WHERE account_id="+accountId+" GROUP BY technology, device_status";
		else if(groupBy.equals("RATE_PLAN_AND_STATUS"))
			query="SELECT technology AS PRODUCT, rate_plan  as RATE_PLAN , ELT(device_status, NULL, 'PROVISION', NULL, 'BILL', 'SUSPEND', NULL, 'CANCEL') DEVICE_STATUS,COUNT(*) AS Device_Count FROM DEVICE_COMB WHERE account_id="+accountId+" GROUP BY technology,rate_plan,device_status";
		else if(groupBy.equals("REPORT_GROUP_AND_STATUS"))
			query="SELECT technology AS PRODUCT, reportgroup as REPORT_GROUP, ELT(device_status, NULL, 'PROVISION', NULL, 'BILL', 'SUSPEND', NULL, 'CANCEL') DEVICE_STATUS,COUNT(*) AS Device_Count FROM DEVICE_COMB WHERE account_id="+accountId+" GROUP BY technology,reportgroup,device_status";
		else if(groupBy.equals("POOL_NAME_AND_STATUS"))
			query="SELECT technology AS PRODUCT,ELT(device_status, NULL, 'PROVISION', NULL, 'BILL', 'SUSPEND', NULL, 'CANCEL') DEVICE_STATUS,pool_name as POOL_NAME,COUNT(*) AS Device_Count FROM DEVICE_COMB WHERE account_id="+accountId+" GROUP BY technology,pool_name,device_status";
			
		if(!query.contains(";"))
			query.concat(";");
		
		ResultSet resultset = mySqlDBAccess.executeQuery(query);
		
		try {
			int numcols = resultset.getMetaData().getColumnCount();
			
			while(resultset.next()){
				List <String> row = new ArrayList<String>(numcols);
				for (int i=1; i<= numcols; i++) { 
		            row.add(resultset.getString(i));
		        }
				dbReportOutput.add(row); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		mySqlDBAccess.closeConnection();
		
		return dbReportOutput;
	} 
	
	
	public List<String> getDeviceIdListFromMySQLDB(String account_id,String deviceIdentifier,String deviceIds){
		String query=null;
		List<String> deviceList = new ArrayList<String>();
		
		MySQLDBAccess mySqlDbAccess = MySQLDBAccess.getMySQLDBInstance();
		mySqlDbAccess.getMySQLDBconnection();
		
		query="SELECT device_id FROM DEVICE_COMB WHERE  (account_id="+account_id+") AND ("+deviceIdentifier+" IN ("+deviceIds+") )";
		
		if(!query.contains(";"))
			query.concat(";");
		
		ResultSet resultset = mySqlDbAccess.executeQuery(query);
		
		try {
			while(resultset.next()){
				deviceList.add(resultset.getString("device_id")); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		mySqlDbAccess.closeConnection();
		
		return deviceList;
	}
	
	public List<List<String>> getDeviceChangeRatePlanActivityByDeviceId(String technology,List<String> deviceId){
		String query =null;
		List<List<String>> dbReportOutput = new ArrayList<List<String>>();
		MySQLDBAccess mySqlDBAccess = MySQLDBAccess.getMySQLDBInstance();
		mySqlDBAccess.getMySQLDBconnection();
		
		for (String device : deviceId) {
			if(technology.equalsIgnoreCase("CDMA"))
				query="select account_id,'Rate Plan' as  change_type, csp_rate_plan as change_value from device_rate_plan_cdma where device_id="+device;
			else if(technology.equalsIgnoreCase("GSM"))
				query="select account_id,'Rate Plan' as change_type, csp_rate_plan as change_value from device_rate_plan where device_id="+device;
			if(!query.contains(";"))
				query.concat(";");
			
			ResultSet resultset = mySqlDBAccess.executeQuery(query);
			
			try {
				int numcols = resultset.getMetaData().getColumnCount();
				
				while(resultset.next()){
					List <String> row = new ArrayList<String>(numcols);
					for (int i=1; i<= numcols; i++) { 
			            row.add(resultset.getString(i));
			        }
					dbReportOutput.add(row); 
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		mySqlDBAccess.closeConnection();
		return dbReportOutput;
	}
	
	public List<List<String>> getDeviceStatusChangeActivityByDeviceId(String technology,List<String> deviceId){
		String query =null;
		List<List<String>> dbReportOutput = new ArrayList<List<String>>();
		MySQLDBAccess mySqlDBAccess = MySQLDBAccess.getMySQLDBInstance();
		mySqlDBAccess.getMySQLDBconnection();
		
		for (String device : deviceId) {
			if(technology.equalsIgnoreCase("CDMA"))
				query="select account_id,'Status' as change_type, ELT(STATUS, NULL, 'PROVISION', NULL, 'BILL', 'SUSPEND', NULL, 'CANCEL') STATUS from device_status_cdma where device_id="+device;
			else if(technology.equalsIgnoreCase("GSM"))
				query="select account_id,'Status' as change_type, ELT(STATUS, NULL, 'PROVISION', NULL, 'BILL', 'SUSPEND', NULL, 'CANCEL') STATUS from device_status where device_id="+device;
			
			if(!query.contains(";"))
				query.concat(";");
			
			ResultSet resultset = mySqlDBAccess.executeQuery(query);
			
			try {
				int numcols = resultset.getMetaData().getColumnCount();
				
				while(resultset.next()){
					List <String> row = new ArrayList<String>(numcols);
					for (int i=1; i<= numcols; i++) { 
			            row.add(resultset.getString(i));
			        }
					dbReportOutput.add(row); 
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		mySqlDBAccess.closeConnection();
		return dbReportOutput;
	}
	
	
	public List<List<String>> getPoolNameChangeActivityByDeviceId(List<String> deviceId){
		String query =null;
		List<List<String>> dbReportOutput = new ArrayList<List<String>>();
		MySQLDBAccess mySqlDBAccess = MySQLDBAccess.getMySQLDBInstance();
		mySqlDBAccess.getMySQLDBconnection();
		
		for (String device : deviceId) {
			query="select account_id,'Rate Plan Pool' as change_type, pool_name as change_value from t_device_pool_tran where device_id="+device;
			if(!query.contains(";"))
				query.concat(";");
			
			ResultSet resultset = mySqlDBAccess.executeQuery(query);
			
			try {
				int numcols = resultset.getMetaData().getColumnCount();
				
				while(resultset.next()){
					List <String> row = new ArrayList<String>(numcols);
					for (int i=1; i<= numcols; i++) { 
			            row.add(resultset.getString(i));
			        }
					dbReportOutput.add(row); 
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		mySqlDBAccess.closeConnection();
		return dbReportOutput;
	}
	
	
	
	
}
