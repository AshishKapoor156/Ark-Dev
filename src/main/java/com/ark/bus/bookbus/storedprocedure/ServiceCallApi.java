package com.ark.bus.bookbus.storedprocedure;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ark.bus.bookbus.Model.ListCity;
@Service
public class ServiceCallApi {

	@Autowired
	DatabaseProcCallApi Procapi;
	
	public Object ListCityDetails(Object AlisaPrefix)
	{
	 List<Object> list = new ArrayList<Object>();
	 list.add(AlisaPrefix);
	
		
	 String procedurename = StoredProcedureInfo.LIST_CITY_NAME;
	 
		List<Class<?>> classlist = new ArrayList<Class<?>>();
		classlist.add(ListCity.class);
	List<Object> cityName = null;
	List<ListCity> listcity = null;
	try {
		cityName = getBean(list,procedurename,classlist);
		listcity = (List<ListCity>) cityName.get(0);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	return listcity;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> getBean(List<Object> inParams, String procedurename, List<Class<?>> outobject) throws Exception
	{
		return (List<Object>) Procapi.GetProcedureCall(inParams == null ? new ArrayList<Object>() : inParams, procedurename, outobject,outobject.size() );
	}
}
