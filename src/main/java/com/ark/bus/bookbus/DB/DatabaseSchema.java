package com.ark.bus.bookbus.DB;

public class DatabaseSchema {

	private String mspAcr;
	private String conectionType;
	private String jndi;
	private String url;
	private String username;
	private String dbCode;

	public String getMspAcr() {
		return mspAcr;
	}

	public void setMspAcr(String mspAcr) {
		this.mspAcr = mspAcr;
	}

	public String getJndi() {
		return jndi;
	}

	public void setJndi(String jndi) {
		this.jndi = jndi;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getConectionType() {
		return conectionType;
	}

	public void setConectionType(String conectionType) {
		this.conectionType = conectionType;
	}

	public String getDbCode() {
		return dbCode;
	}

	public void setDbCode(String dbCode) {
		this.dbCode = dbCode;
	}

}
