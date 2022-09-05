package com.ark.bus.bookbus.DB;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.ark.bus.bookbus.datasource.JdbcDatasource;
import com.ark.bus.bookbus.datasource.JndiDataSource;
import com.ark.bus.bookbus.commons.util.Config;
import com.ark.bus.bookbus.commons.util.Log;
import com.ark.bus.bookbus.commons.util.PosException;
import com.ark.bus.bookbus.commons.util.StaticStore;
import com.ark.bus.bookbus.commons.constant.Constants;
import com.ark.bus.bookbus.commons.util.Util;
import com.zaxxer.hikari.HikariDataSource;

public abstract class AbstractDatabaseApi implements DatabaseApi {

	@Autowired
	private JdbcDatasource jdbcDatasource;

	@Autowired
	private JndiDataSource jndiDataSource;

	@Autowired
	private Config config;

	private static Map<String, HikariDataSource> jdbcMap = new ConcurrentHashMap<String, HikariDataSource>();
	private static Map<String, DataSource> jndiMap = new ConcurrentHashMap<String, DataSource>();

	@Override
	public Connection getConnection(String instId) throws PosException {
		//if (Constants.MASTER_INSTID.equals(instId))
			return getMasterConnection(instId);
		//else
		//	return getChildConnection(instId);
		
	}

	private Connection getMasterConnection(String instId) throws PosException {
		try {
			if (Constants.DB_CONNECTION_TYPE_JNDI.equals(config
					.getMasterDbConnectionType())) {
				DataSource ds = jndiDataSource.getDataSource(config
						.getMasterJndi());
				return ds.getConnection();
			} else if (Constants.DB_CONNECTION_TYPE_JDBC.equals(config
					.getMasterDbConnectionType())) {
				Class.forName(getDriver());
				return DriverManager.getConnection(config.getDbUrl(),
						config.getDbName(), config.getDbCode());
			} else
				throw new Exception("Invalid Connection type");
		} catch (Exception e) {
			Log.info("Master database connection failed due to ",
					e.getMessage());
			Log.error("Master database connection failed due to ", e);
			throw new PosException(Constants.ERR_SYSTEM_ERROR);
		}
	}

	/*
	 * private Connection getChildConnection(String instId) throws PosException {
	 * try { if (!StaticStore.schemaMap.containsKey(instId)) throw new
	 * PosException(Constants.ERR_INVALID_MSP); DatabaseSchema schema =
	 * StaticStore.schemaMap.get(instId); if
	 * (Constants.DB_CONNECTION_TYPE_JNDI.equals(schema .getConectionType())) { if
	 * (!jndiMap.containsKey(instId)) registerJndiPool(schema); return
	 * jndiMap.get(instId).getConnection(); } else if
	 * (Constants.DB_CONNECTION_TYPE_JDBC.equals(schema .getConectionType())) { if
	 * (!jdbcMap.containsKey(instId)) registerJdbcPool(schema); return
	 * jdbcMap.get(instId).getConnection(); } else return null; } catch
	 * (PosException e) { Log.error("In getConnection ", e); throw e; } catch
	 * (Exception e) { Log.error("In getConnection ", e); throw new
	 * PosException(Constants.ERR_SYSTEM_ERROR); } }
	 */
	private void registerJdbcPool(DatabaseSchema schema) throws NamingException {
		Log.trace("Registering connections for " + schema.getMspAcr()
				+ " using Jdbc");
		HikariDataSource hds = jdbcDatasource.getDataSource(schema,
				getDriver(), getTestQuery());
		jdbcMap.put(schema.getMspAcr(), hds);

	}

	private void registerJndiPool(DatabaseSchema schema) throws NamingException {
		Log.trace("Registering connections for " + schema.getMspAcr()
				+ " using Jndi " + schema.getJndi());
		DataSource ds = jndiDataSource.getDataSource(schema.getJndi());
		jndiMap.put(schema.getMspAcr(), ds);
	}
	public static Map<String, MethodHandle> setterMethodHandles = new ConcurrentHashMap<String, MethodHandle>();
	protected void iterateResultSet(ResultSet rs, ResultSetMetaData rsmt,
			Class<?> clazz, Object o) throws Exception {
		try {
			for (int i = 1; i < rsmt.getColumnCount() + 1; i++) {
				Object obj = rs.getObject(i);
				String fieldName = clazz.getCanonicalName()
						+ rsmt.getColumnName(i);
				String value = obj == null ? null : String.valueOf(obj);
				MethodHandle mh = setterMethodHandles
						.get(fieldName);
				if (mh == null) {
					Method m = clazz.getDeclaredMethod(
							"set" + capitalize(rsmt.getColumnName(i)),
							new Class[] { String.class });
					m.invoke(o, value);
				} else {
					mh.invoke(o, value);
				}
			}
		} catch (Throwable e) {
			
			throw new Exception(e);
		}
	}

	public static String capitalize(final String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0)
			return str;
		final char firstChar = str.charAt(0);
		final char newChar = Character.toTitleCase(firstChar);
		if (firstChar == newChar)
			return str;
		char[] newChars = new char[strLen];
		newChars[0] = newChar;
		str.getChars(1, strLen, newChars, 1);
		return String.valueOf(newChars);
	}
	
	protected void closeConnection(ResultSet r, PreparedStatement p,
			Connection c) {
		try {
			if (r != null)
				r.close();
			if (p != null)
				p.close();
			if (c != null)
				c.close();
		} catch (Exception e) {
			Log.error("closeConnection ", e);
		}
	}

	protected abstract String getDriver();

	protected abstract String getTestQuery();

}
