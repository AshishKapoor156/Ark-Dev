package com.ark.bus.bookbus.DB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.OracleTypes;

import com.ark.bus.bookbus.commons.util.Log;
import com.ark.bus.bookbus.commons.util.PosException;
import com.ark.bus.bookbus.commons.util.Util;

public abstract class OracleDataBaseApi extends AbstractDatabaseApi {

	@Override
	protected String getDriver() {
		return "oracle.jdbc.driver.OracleDriver";
	}

	@Override
	protected String getTestQuery() {
		return "SELECT 1 FROM DUAL";
	}

	@Override
	public Object executeStoredProcedure(String procedureName,
			List<Object> inParams, String instId) throws SQLException,
			PosException {
		// Log.debug("thread name", Thread.currentThread().getName());
		// if (Util.amIRightThread("posEventThread"))
		Log.debug("executing Procedure " + procedureName + "  Oracle schema "
				+ instId, inParams.toString());

		int inCount = inParams.size();
		Connection con = null;
		CallableStatement cst = null;
		boolean commaFalg = false;
		StringBuilder query = new StringBuilder("{call " + procedureName);
		try {
			con = getConnection(instId);
			query.append("(");
			for (int i = 0; i < inCount + 1; i++) {
				if (commaFalg)
					query.append(",");
				query.append("?");
				commaFalg = true;
			}
			query.append(")}");
			cst = con.prepareCall(query.toString());
			for (int i = 0; i < inParams.size(); i++)
				cst.setObject(i + 1, inParams.get(i));
			cst.registerOutParameter(inCount + 1, Types.VARCHAR);
			long start = System.currentTimeMillis();
			cst.execute();
			Log.debug("Time " + procedureName + " Exec ",
					String.valueOf(System.currentTimeMillis() - start));
			return cst.getObject(inCount + 1);
		} finally {
			closeConnection(null, cst, con);
		}
	}

	@Override
	public Object executeStoredProcedure(String instId, String procedureName,
			int outCount, List<Object> inParams, Object object)
			throws Exception {
		Log.debug("inside OracleStoredProcedureApi : ","inside executeStoredProcedure");
		if (inParams != null && Util.amIRightThread("posEventThread")
				&& !procedureName.equals("PSP_GETTRANSACTIONTIMERDATA"))
			Log.debug("thread name", Thread.currentThread().getName());
		if (Util.amIRightThread("posEventThread"))
			Log.debug("executing Procedure " + procedureName
					+ "  Oracle schema " + instId, inParams.toString());
		int inCount = inParams.size();
		Connection con = null;
		CallableStatement cst = null;
		boolean commaFalg = false;
	//	Log.debug("inside OracleStoredProcedureApi calling: ",procedureName);
		StringBuilder query = new StringBuilder("{ call " + procedureName);
		try {
			con = getConnection(instId);
		//	Log.debug("db connection inst: ",instId);
		//	Log.debug("db connection: ",con.toString());
			query.append("(");
			for (int i = 0; i < inCount + outCount; i++) {
				if (commaFalg)
					query.append(",");
				query.append("?");
				commaFalg = true;
			}
			query.append(") }");
		//	Log.debug("db connection query: ",query.toString());
			cst = con.prepareCall(query.toString());
		//	Log.debug("db connection query: ","after prepare call");
			for (int i = 0; i < inParams.size(); i++)
				cst.setObject(i + 1, inParams.get(i));

			for (int j = 1; j < outCount + 1; j++)
				cst.registerOutParameter(j + inCount, OracleTypes.CURSOR);

			 long start = System.currentTimeMillis();
			cst.execute();
			 Log.debug("Time " + procedureName + " Exec ",
		    String.valueOf(System.currentTimeMillis() - start));

			List<ResultSet> rsList = new ArrayList<ResultSet>();
			for (int j = 1; j < outCount + 1; j++)
				rsList.add((ResultSet) cst.getObject(j + inCount));
	//		Log.debug("inside OracleStoredProcedureApi calling: ","calling assignValuesFromResultSet");
			return assignValuesFromResultSet(rsList, object);

		} finally {
	//		Log.debug("db connection ","connection closing");
			closeConnection(null, cst, con);
		}
	}

	protected abstract Object assignValuesFromResultSet(Object resultSetObject,
			Object object) throws Exception;

}
