package com.ark.bus.bookbus.storedprocedure;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import com.ark.bus.bookbus.DB.MsSqlDatabaseApi;
//import com.fss.pos.base.commons.constants.Constants;

//@StoredProcedure(Constants.DB_TYPE_MSSQL)
public class MsSqlStoredProcedureApi extends MsSqlDatabaseApi implements
		StoredProcedureApi {

	@Override
	@SuppressWarnings("unchecked")
	public List<Object> getBean(List<Object> params, String procedureName,
			List<Class<?>> classList, String mspAcr) throws Exception {
		return (List<Object>) executeStoredProcedure(mspAcr, procedureName,
				classList.size(), params == null ? new ArrayList<Object>()
						: params, classList);
	}

	@Override
	public String getString(List<Object> params, String procedureName,
			String mspAcr) throws Exception {
		return (String) executeStoredProcedure(procedureName,
				params == null ? new ArrayList<Object>() : params, mspAcr);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Object> assignValuesFromResultSet(Object resultSetObject,
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
}
