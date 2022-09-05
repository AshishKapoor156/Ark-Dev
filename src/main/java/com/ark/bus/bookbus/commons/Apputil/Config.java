package com.ark.bus.bookbus.commons.Apputil;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
@Component
@Configuration
public class Config {

	private static final String CONFIG_FILE_PATH;
	//public static final String SECURITY_FILE_PATH;

	static {
		Properties _properties = new Properties();
		java.io.InputStream stream = (Config.class).getClassLoader()
				.getResourceAsStream("application.properties");
		try {
			_properties.load(stream);
		} catch (Exception e) {
			//Log.trace("Error loading property file !!!");
			//Log.error("Loading property file !!!", e);
			e.printStackTrace();
		}
		if (stream == null)
		//	Log.trace("property file is not loaded");
			System.out.println("property file is not loaded");
		CONFIG_FILE_PATH = _properties.getProperty("configLocation");
		//SECURITY_FILE_PATH = _properties.getProperty("securityLocation");
	}
	
	
	/*  @Value("${spring.datasource.jndi-name}") 
	  private String MasterJNDI;*/
	 
	  
	  @Value("${DB_JIN_NAME}") 
	  private String MasterJNDI;
	
	@Value("${spring.datasource.username}")
	private String dbName;

	@Value("${spring.datasource.password}")
	private String dbCode;

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Value("${connectiontype}")
	private String dbType;

	@Value("${spring.datasource.driverClassName}")
	private String DriverName;


	 
	
		
		  public String getMasterJNDI() { return MasterJNDI; }
		  
		  public void setMasterJNDI(String masterJNDI) { MasterJNDI = masterJNDI; }
		 

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbCode() {
		return dbCode;
	}

	public void setDbCode(String dbCode) {
		this.dbCode = dbCode;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getDriverName() {
		return DriverName;
	}

	public void setDriverName(String driverName) {
		DriverName = driverName;
	}

	public static String getConfigFilePath() {
		return CONFIG_FILE_PATH;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		PropertySourcesPlaceholderConfigurer p = new PropertySourcesPlaceholderConfigurer();
		final List<Resource> resourceLst = new ArrayList<Resource>();
		resourceLst.add(new FileSystemResource(CONFIG_FILE_PATH));
		p.setLocations(resourceLst.toArray(new Resource[] {}));
		return p;
	}


}
