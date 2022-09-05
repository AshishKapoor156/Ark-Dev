package com.ark.bus.bookbus.DB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


public abstract class MsSqlDatabaseApi extends AbstractDatabaseApi {

	@Override
	protected String getDriver() {
		return "net.sourceforge.jtds.jdbc.Driver";
	}

	@Override
	protected String getTestQuery() {
		return "SELECT 1";
	}

	@Override
	public Object executeStoredProcedure(String procedureName,
			List<Object> inParams, String instId) throws SQLException,
			Exception {
		//if (inParams != null && Util.amIRightThread("posEventThread"))
			//Log.debug("executing Procedure " + procedureName + " MSSql schema "+ instId, inParams.toString());
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
			//Log.debug("Time " + procedureName + " Exec ",String.valueOf(System.currentTimeMillis() - start));
			return cst.getObject(inCount + 1);
		} finally {
			closeConnection(null, cst, con);
		}
	}

	@Override
	public Object executeStoredProcedure(String instId, String procedureName,
			int outCount, List<Object> inParams, Object object)
			throws Exception {
	//	if (Util.amIRightThread("posEventThread"))
			//Log.debug("executing Procedure " + procedureName + " MSSql Schema "+ instId, inParams == null ? null : inParams.toString());
		Connection con = null;
		CallableStatement cst = null;
		try {
			con = getConnection(instId);
			StringBuilder query = new StringBuilder("{call " + procedureName);
			query.append("(");
			boolean commaFalg = false;
			for (int i = 0; i < inParams.size(); i++) {
				if (commaFalg)
					query.append(",");
				query.append("?");
				commaFalg = true;
			}
			query.append(")}");
			cst = con.prepareCall(query.toString());
			for (int i = 0; i < inParams.size(); i++)
				cst.setObject(i + 1, inParams.get(i));
			boolean result = cst.execute();
			List<Object> ol = new ArrayList<Object>();
			ol.add(result);
			ol.add(cst);
			return assignValuesFromResultSet(ol, object);
		} finally {
			closeConnection(null, cst, con);
		}
	}

	protected abstract Object assignValuesFromResultSet(Object resultSetObject,
			Object object) throws Exception;

}
