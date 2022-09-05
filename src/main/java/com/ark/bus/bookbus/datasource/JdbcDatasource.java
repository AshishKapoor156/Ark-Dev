package com.ark.bus.bookbus.datasource;

import org.springframework.stereotype.Component;

import com.ark.bus.bookbus.DB.DatabaseSchema;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * An object which creates the database connection pool. Replace the class if
 * the connection pooling need to be changed. Not applicable for jndi.
 * 
 * @author Priyan
 */
@Component
public class JdbcDatasource {

	public HikariDataSource getDataSource(final DatabaseSchema ds,
			final String driver, final String testQuery) {
		HikariConfig hc = new HikariConfig();
		hc.setDriverClassName(driver);
		hc.setJdbcUrl(ds.getUrl());
		hc.setUsername(ds.getUsername());
		hc.setPassword(ds.getDbCode());
		hc.setConnectionTestQuery(testQuery);
		hc.setConnectionTimeout(30000);
		hc.setMaximumPoolSize(20);
		hc.setMinimumIdle(2);
		hc.setIdleTimeout(2000);
		hc.setAutoCommit(true);
		return new HikariDataSource(hc);
	}

}
