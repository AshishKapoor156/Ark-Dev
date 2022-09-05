package com.ark.bus.bookbus.storedprocedure;

public class TransactionEnquiry {
	
	private String procCode;
	private String cardNo;
	private String entryMode;
	//private String cardType;
	private String amount;
	private String merCurrency;
	//private String dccAmount;
	//private String dccCurrency;
	private String respCode;
	private String apprCode;
	private String invoice;
	private String stan;
	private String rrn;
	private String tid;
	private String date;
	//private String authMode;
	private String acqId;
	private String status;
	//private String settlement;
	private String compLimit;
//	private String merchantTime;
	//private String merchantDate;
	
	public String getProcCode() {
		return procCode;
	}
	public void setProcCode(String procCode) {
		this.procCode = procCode;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getEntryMode() {
		return entryMode;
	}
	public void setEntryMode(String entryMode) {
		this.entryMode = entryMode;
	}
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getMerCurrency() {
		return merCurrency;
	}
	public void setMerCurrency(String merCurrency) {
		this.merCurrency = merCurrency;
	}
	
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getApprCode() {
		return apprCode;
	}
	public void setApprCode(String apprCode) {
		this.apprCode = apprCode;
	}
	public String getInvoice() {
		return invoice;
	}
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	public String getStan() {
		return stan;
	}
	public void setStan(String stan) {
		this.stan = stan;
	}
	public String getRrn() {
		return rrn;
	}
	public void setRrn(String rrn) {
		this.rrn = rrn;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getAcqId() {
		return acqId;
	}
	public void setAcqId(String acqId) {
		this.acqId = acqId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCompLimit() {
		return compLimit;
	}
	public void setCompLimit(String compLimit) {
		this.compLimit = compLimit;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TransactionEnquiry [procCode=").append(procCode)
				.append(", cardNo=").append(cardNo).append(", entryMode=")
				.append(entryMode).append(", amount=").append(amount)
				.append(", merCurrency=").append(merCurrency)
				.append(", respCode=").append(respCode).append(", apprCode=")
				.append(apprCode).append(", invoice=").append(invoice)
				.append(", stan=").append(stan).append(", rrn=").append(rrn)
				.append(", tid=").append(tid).append(", date=").append(date)
				.append(", acqId=").append(acqId).append(", status=")
				.append(status).append(", compLimit=").append(compLimit)
				.append("]");
		return builder.toString();
	}
	 
	
}
