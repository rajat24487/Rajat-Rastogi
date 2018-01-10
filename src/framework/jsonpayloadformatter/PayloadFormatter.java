package framework.jsonpayloadformatter;

import java.util.Properties;


import framework.models.TransactionAPIModel;

public class PayloadFormatter {

	public static final String amountKey = "amount";
	public static final String typeKey = "type";
	public static final String parent_idKey = "parent_id";
	
	
	public TransactionAPIModel SaveTransactionPayload(Properties prop) {

		TransactionAPIModel TransactionModel = new TransactionAPIModel();
		TransactionModel.setAmount(Double.parseDouble(prop.getProperty(amountKey)));
		TransactionModel.setType(prop.getProperty(typeKey));
		TransactionModel.setParentId(Long.parseLong(prop.getProperty(parent_idKey)));
		return TransactionModel;

	}
	
	public TransactionAPIModel SaveTransactionPayloadWithoutParent(Properties prop) {

		TransactionAPIModel TransactionModel = new TransactionAPIModel();
		TransactionModel.setAmount(Double.parseDouble(prop.getProperty(amountKey)));
		TransactionModel.setType(prop.getProperty(typeKey));
			return TransactionModel;

	}

	}