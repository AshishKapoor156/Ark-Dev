package com.ark.bus.bookbus.DB;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

//import com.fss.pos.base.api.Api;
//import com.fss.pos.base.commons.PosException;

/**
 * An object that handles database connectivity.
 * 
 * @author Priyan
 *
 */
public interface DatabaseApi {

	/**
	 * To get connection by id from pool. Independent of transaction api
	 * {@link Api}.
	 * 
	 * @param instId
	 *            {@link String} object contains id to identify the database
	 * @return a live {@link Connection} object from pool
	 * @throws PosException
	 *             thrown when {@link Exception} occurs at runtime
	 */
	public Connection getConnection(String instId) throws Exception;

	/**
	 * To execute the stored procedure and get one result
	 * 
	 * @param procedureName
	 *            the name of the stored procedure
	 * @param inParams
	 *            the input parameters sorted
	 * @param instId
	 *            id to identify the database
	 * @return the {@link Object} which holds the single data
	 * @throws SQLException
	 * @throws PosException
	 */
	public Object executeStoredProcedure(String procedureName,
			List<Object> inParams, String instId) throws SQLException,
			Exception;

	/**
	 * To execute the stored procedure and get multiple results
	 * 
	 * @param instId
	 *            id to identify the database
	 * @param procedureName
	 *            the name of the stored procedure
	 * @param outCount
	 *            number of results expected from the stored procedure
	 * @param inParams
	 *            the input parameters sorted
	 * @param object
	 *            temporary object to be used
	 * @return object the {@link Object} which holds list of data
	 * @throws Exception
	 */
	public abstract Object executeStoredProcedure(String instId,
			String procedureName, int outCount, List<Object> inParams,
			Object object) throws Exception;

}
