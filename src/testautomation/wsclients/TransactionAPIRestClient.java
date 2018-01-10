
package testautomation.wsclients;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import java.util.Map;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;


import org.apache.log4j.Logger;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;

import org.junit.Assert;
import org.testng.Reporter;



import framework.ws.BaseTransactionAPIRestClient;


import framework.jsonpayloadformatter.PayloadFormatter;

import framework.models.TransactionAPIModel;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;



public class TransactionAPIRestClient extends BaseTransactionAPIRestClient{
	
	private static final Logger logger = Logger.getLogger(BaseTransactionAPIRestClient.class);

	public static final String idPropKey = "id";
	public static final String typePropKey = "type";
	
//	MySQLDBAccess mySQLDBAccess = new MySQLDBAccess();
	private String url_saveTransaction = qa_url_saveTransaction;
	private String url_getTransaction = qa_url_getTransaction;
	private String url_getSum = qa_url_getSum;
	public static TransactionAPIModel createsaveTransactionPayload = null;
	public static String createsaveTransactionPayloadJson = null;
	
	
	/** ===================================API to Save a Transaction with Parent Id================= 
	* 		Module: 
	* 			Store Transaction with parent* 			 
	*  			verifyRestResponseforSaveTransaction - Verify API response call 
	*  			
	* 		API Details 
	* 			 API : /transactionservice/transaction/<id>
	* 			 Method : PUT 
	* 			 Pass Code: Success:200 - Created.
	* ===================================================================================*/

	public ClientResponse saveTransaction(Properties prop) throws IOException{
		WebResource webResource;
		ClientResponse response = null;
		ObjectMapper mapper = new ObjectMapper();
		Integer id = Integer.parseInt(prop.getProperty(TransactionAPIRestClient.idPropKey));			
		TransactionAPIRestClient obj =  new TransactionAPIRestClient();
					
			PayloadFormatter payloadGen = new PayloadFormatter();
			try {
				createsaveTransactionPayload = payloadGen.SaveTransactionPayload(prop);

			mapper.getSerializationConfig().setSerializationInclusion(org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_NULL);
				
				createsaveTransactionPayloadJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(createsaveTransactionPayload);
					
				System.out.println("Json Payload :" + createsaveTransactionPayloadJson);
			Client client = obj.hostIgnoringClient();
			webResource = client.resource(HOST+url_saveTransaction+'/'+id);
				response = webResource.header("Content-Type", "application/json").entity(createsaveTransactionPayloadJson).put(ClientResponse.class);

			} catch (Exception e) {
				System.err.println(e.getMessage());
				LOG.error("***Fatal error: Unable to perform operation", e);
			} finally {
				
			}
			return response;
		}
		
	
	
	/** ===================================API to Save a Transaction without Parent Id================= 
	* 		Module: 
	* 			Store Transaction without parent* 			 
	*  			verifyRestResponseforSaveTransaction - Verify API response call 
	*  			
	* 		API Details 
	* 			 API : /transactionservice/transaction/<id>
	* 			 Method : PUT 
	* 			 Pass Code: Success:200 - Created.
	* ===================================================================================*/

	public ClientResponse saveTransactionwoParent(Properties prop) throws IOException{
		WebResource webResource;
		ClientResponse response = null;
		ObjectMapper mapper = new ObjectMapper();
		Integer id = Integer.parseInt(prop.getProperty(TransactionAPIRestClient.idPropKey));			
		TransactionAPIRestClient obj =  new TransactionAPIRestClient();
					
			PayloadFormatter payloadGen = new PayloadFormatter();
			try {
				createsaveTransactionPayload = payloadGen.SaveTransactionPayloadWithoutParent(prop);

			mapper.getSerializationConfig().setSerializationInclusion(org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_NULL);
				
				createsaveTransactionPayloadJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(createsaveTransactionPayload);
					
				System.out.println("Json Payload :" + createsaveTransactionPayloadJson);
			Client client = obj.hostIgnoringClient();
			webResource = client.resource(HOST+url_saveTransaction+'/'+id);
				response = webResource.header("Content-Type", "application/json").entity(createsaveTransactionPayloadJson).put(ClientResponse.class);

			} catch (Exception e) {
				System.err.println(e.getMessage());
				LOG.error("***Fatal error: Unable to perform operation", e);
			} finally {
				
			}
			return response;
		}
		
	/** ===================================API to return  TransactionId based on given type================= 
	* 		Module: 
	* 			return  TransactionId based on given type* 			 
	*  			verifyRestResponseGetTransaction - Verify API response call 
	*  			
	* 		API Details 
	* 			 API : /transactionservice/types/<type>
	* 			 Method : GET 
	* 			 Pass Code: Success:200 - Created.
	* ===================================================================================*/

	public ClientResponse getTransactionId(Properties prop) throws IOException{
		WebResource webResource;
		ClientResponse response = null;
		ObjectMapper mapper = new ObjectMapper();
		String type = prop.getProperty(TransactionAPIRestClient.typePropKey);			
		TransactionAPIRestClient obj =  new TransactionAPIRestClient();
					
		try{
			Client client = obj.hostIgnoringClient();
			webResource = client.resource(HOST+url_getTransaction+'/'+type);
				response = webResource.header("Content-Type", "application/json").get(ClientResponse.class);
				//System.out.println(response.getEntity(String.class));
			} catch (Exception e) {
				System.err.println(e.getMessage());
				LOG.error("***Fatal error: Unable to perform operation", e);
			} finally {
				
			}
			return response;
		}
		
	/** ===================================API to return  sum based on given transaction id================= 
	* 		Module: 
	* 			return  sum based on given transaction id* 			 
	*  			verifyRestResponserGetSum - Verify API response call 
	*  			
	* 		API Details 
	* 			 API : /transactionservice/sum/<id>
	* 			 Method : GET 
	* 			 Pass Code: Success:200 - Created.
	* ===================================================================================*/

	public ClientResponse getSum(Properties prop) throws IOException{
		WebResource webResource;
		ClientResponse response = null;
		ObjectMapper mapper = new ObjectMapper();
		String id = prop.getProperty(TransactionAPIRestClient.idPropKey);			
		TransactionAPIRestClient obj =  new TransactionAPIRestClient();
					
		try{
			Client client = obj.hostIgnoringClient();
			webResource = client.resource(HOST+url_getSum+'/'+id);
				response = webResource.header("Content-Type", "application/json").get(ClientResponse.class);
				//System.out.println(response.getEntity(String.class));
			} catch (Exception e) {
				System.err.println(e.getMessage());
				LOG.error("***Fatal error: Unable to perform operation", e);
			} finally {
				
			}
			return response;
		}
		
	
	/** ===================================/** VERIFICATION OF APIs/RO RESPONSES================= 
	* 	
	* ===================================================================================*/
	

	
	//verification for Save Transaction API response
		public void verifyRestResponseforSaveTransaction(ClientResponse response){
			String jsonResponse = response.getEntity(String.class);
			try{
				Reporter.log("\n\n Response:\n"+jsonResponse+"\n", true);
				Assert.assertEquals(200, response.getStatus());
				Map<String, Object> map = new ObjectMapper().readValue(jsonResponse, Map.class);
				Assert.assertEquals(true,(map.get("status").equals("ok"))); 
				
				logger.info("Verified: Transaction is stored from API");
			} catch(Exception e){
				
			} finally{
				
			}
		}
	
		//verification for Get Transaction API response
				public void verifyRestResponseGetTransaction(ClientResponse response){
					String Response = response.getEntity(String.class);
					try{
						Assert.assertEquals(200, response.getStatus());
						System.out.println("Transaction ID is "+ Response.substring(1, Response.length()-1));
				//Need DB verification here
						logger.info("Verified: Transaction is stored from API");
					} catch(Exception e){
						
					} finally{
						
					}
				}
					
				
				//verification for Get Sum API response
				public void verifyRestResponseGetSum(ClientResponse response){
					String jsonResponse = response.getEntity(String.class);
					try{
						Assert.assertEquals(200, response.getStatus());
						Map<String, Object> map = new ObjectMapper().readValue(jsonResponse, Map.class);
						System.out.println("Sum is "+map.get("sum")); 
				//Need DB verification here
						logger.info("Verified: Transaction is stored from API");
					} catch(Exception e){
						
					} finally{
						
					}
				}
	
	private static void waitFor(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			logger.info(e);
		}
	}

		
	private Client hostIgnoringClient() {
		try {
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, null, null);
			DefaultClientConfig config = new DefaultClientConfig();
			Map<String, Object> properties = config.getProperties();
			HTTPSProperties httpsProperties = new HTTPSProperties(new HostnameVerifier() {
				public boolean verify(String s, SSLSession sslSession) {

					return true;
				}

			}, sslcontext);
			properties.put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, httpsProperties);
			config.getClasses().add(JacksonJsonProvider.class);
			return Client.create(config);
		} catch (KeyManagementException e) {
			throw new RuntimeException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
