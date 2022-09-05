package com.ark.bus.bookbus.storedprocedure;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ark.bus.bookbus.DB.MysqlDatabaseSchema;

import oracle.jdbc.OracleTypes;



@Component
public class ProcedureCallApi extends MysqlDatabaseSchema implements DatabaseProcCallApi  {

	
	@Override
	public Object GetProcedureCall(List<Object> inParams, String procedurename, Object outobject, int outcount) throws Exception {
		
		Connection connection = null; 
		CallableStatement cst = null;
		try
		{
			connection = getconnection();
			/* Example
			 * Statement st = connection.createStatement(); ResultSet rs =
			 * st.executeQuery("select busname from busdetails where busid = 1");
			 */
		StringBuilder proc= new StringBuilder();
		
		proc.append("{call ");
		proc.append(procedurename);
		proc.append("(");
		int inputparameter = inParams.size();
	
		System.out.println(inputparameter);
		for (int i = 0; i < inputparameter ; i++)
		{
			proc.append("?");
			
			if((i+1) != inputparameter)
			{
				proc.append(",");
			}
			
		}
		proc.append(")}");
		System.out.println("Input Parameter :"+proc.toString());
		
		cst = connection.prepareCall(proc.toString());
		
		for (int i = 0; i < inputparameter;  i++)
		{
			cst.setObject(i+1, inParams.get(i));
		}
		
		/*
		 * for (int j = 1 ; j < outcount +1 ; j++) {
		 * cst.registerOutParameter(j+inputparameter, OracleTypes.CURSOR); }
		 */
		
		 long start = System.currentTimeMillis();
		 boolean result = cst.execute();
		
		 List<Object> ol = new ArrayList<Object>();
			ol.add(result);
			ol.add(cst);
		 
//		List<ResultSet> rsList = new ArrayList<ResultSet>();
//		for (int j = 1; j < outcount +1; j++)
//			rsList.add((ResultSet) cst.getObject(j + inputparameter));
//		
//System.out.println("Time " + procedurename + " Exec ",
//		    String.valueOf(System.currentTimeMillis() - start));
	
		return MySQLassignValuesFromResultSet(ol, outobject);
		
		}	
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
		finally {
			
					closeConnection(null, cst, connection);
				}
		
		
	}

	
public static void main(String[] args) {
	List list = null;
	String procedurename = null;
	ProcedureCallApi pi =new ProcedureCallApi();
	//pi.GetProcedureCall(list, procedurename);
}
	
}
