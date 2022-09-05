package com.ark.bus.bookbus.storedprocedure;

import java.util.List;

import com.ark.bus.bookbus.DB.DatabaseApi;

/**
 * The interface to access the stored procedures.
 * 
 * @author Priyan
 * @see DatabaseApi
 */
public interface StoredProcedureApi extends DatabaseApi {

	/**
	 * To get the list of populated beans from database.
	 * 
	 * @param params
	 *            the parameters for the stored procedure (sorted)
	 * @param procedureName
	 *            name of the stored procedure
	 * @param classList
	 *            {@link Class} of the beans in list. Must be equal to the
	 *            outputs produced by the stored procedure
	 * @param mspAcr
	 *            id to identify the database
	 * @return list of beans holding the data from database
	 * @throws Exception
	 *             thrown when exception occurs at runtime
	 */
	public List<Object> getBean(List<Object> params, String procedureName,
			List<Class<?>> classList, String mspAcr) throws Exception;

	/**
	 * To get one column result from stored procedure
	 * 
	 * @param params
	 *            the parameters for the stored procedure (sorted)
	 * @param procedureName
	 *            name of the stored procedure
	 * @param mspAcr
	 *            id to identify the database
	 * @return {@link String} object holding the data
	 * @throws Exception
	 *             thrown when exception occurs at runtime
	 */
	public String getString(List<Object> params, String procedureName,
			String mspAcr) throws Exception;

}
