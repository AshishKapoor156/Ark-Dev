package com.ark.bus.bookbus.DB;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.ark.bus.bookbus.commons.constant.Constants;
import com.ark.bus.bookbus.commons.Apputil.Config;
import com.ark.bus.bookbus.commons.Apputil.Util;
import com.ark.bus.bookbus.datasource.JndiDataSource;
import com.ark.bus.bookbus.storedprocedure.ResponseStatus;


public class MysqlDatabaseSchema {

@Autowired
private Config config;

@Autowired
private JndiDataSource jndiDataSource;


public static Map<String, MethodHandle> setterMethodHandles;
static {
	 setterMethodHandles = new ConcurrentHashMap<String, MethodHandle>();
	 
	List<Class<?>> classes = new ArrayList<Class<?>>();
	classes.add(ResponseStatus.class);
	
	try {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		for (Class<?> clazz : classes) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field f : fields) {
				f.setAccessible(true);
				setterMethodHandles.put(f.getDeclaringClass()
						.getCanonicalName() + f.getName(),
						lookup.unreflectSetter(f));
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
		
	}
}

	public Connection getconnection() throws Exception
	{
		String URL = config.getDbUrl();
		String UserName = config.getDbName();
		String Password = config.getDbCode();
		String DriverName = config.getDriverName();
		
		//try {
		if(Constants.DB_CONNECTION_TYPE_JNDI.equals(config.getDbType()))
		{
			DataSource ds = null;

			ds = jndiDataSource.getDataSource(config
					.getMasterJNDI());
			
			//ds = jndiDataSource.getDataSource("Test");
			
			return ds.getConnection();
		}
		
		else if(Constants.DB_CONNECTION_TYPE_JDBC.equals(config.getDbType()))
		{
			System.out.println("DB Details :" + " URL :" + URL + " Userbane :" +UserName +" password :" +Password + " dbtypr :" +config.getDbType() + " JNDI NAME :" +config.getMasterJNDI());
				Class.forName(DriverName);
			
			return DriverManager.getConnection(URL, UserName, Password);
		
		}
		else
			throw new Exception("Invalid Connection type");
		
	
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
			e.printStackTrace();
		}
	}
	
	public List<Object> assignValuesFromResultSet(Object resultSetObject,
			Object object) throws Exception {
		
		int inc = 0;
		ArrayList<Object> objList = new ArrayList<Object>();
		for (ResultSet rs : (List<ResultSet>) resultSetObject) {
			if (rs == null)
				continue;
			Class<?> clazz = ((List<Class<?>>) object).get(inc);
			ResultSetMetaData rsmt = rs.getMetaData();
			List list = new ArrayList();
			while (rs.next()) {
				Object o = clazz.newInstance();
				iterateResultSet(rs, rsmt, clazz, o);
				list.add(o);
			}
			objList.add(list);
			inc++;
		}
		return objList;
	}
	
	public List<Object> MySQLassignValuesFromResultSet(Object resultSetObject,
			Object object) throws Exception {
		List<Object> ol = (List<Object>) resultSetObject;
		boolean results = (boolean) ol.get(0);
		CallableStatement cst = (CallableStatement) ol.get(1);
		List<Object> objList = new ArrayList<Object>();
		int inc = 0;
		do {
			if (results) {
				ResultSet rs = cst.getResultSet();
				if (rs == null)
					continue;
				Class<?> clazz = ((List<Class<?>>) object).get(inc);
				ResultSetMetaData rsmt = rs.getMetaData();
				List list = new ArrayList();
				while (rs.next()) {
					Object o = clazz.newInstance();
					iterateResultSet(rs, rsmt, clazz, o);
					list.add(o);
				}
				objList.add(list);
				inc++;
				rs.close();
			}
			results = cst.getMoreResults();
		} while (results);
		return objList;
	}
	
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
							"set" + Util.capitalize(rsmt.getColumnName(i)),
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
	
	
	
}
