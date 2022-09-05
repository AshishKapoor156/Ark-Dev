/**
 * 
 */
package com.ark.bus.bookbus.storedprocedure;

import java.util.List;

/**
 * @author Ashish Kapoor
 *
 */
public interface DatabaseProcCallApi {

	public Object GetProcedureCall(List<Object> inParams, String procedurename, Object outobject, int outcount) throws Exception;
	
}
