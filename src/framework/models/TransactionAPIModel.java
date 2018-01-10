package framework.models;

public class TransactionAPIModel {
	
	
	private Double amount;	
	private String type;
	private Long parent_id;	
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		 this.amount = amount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getParentId() {
		return parent_id;
	}
	public void setParentId(Long parent_id) {
		this.parent_id = parent_id;
	}


	
}
