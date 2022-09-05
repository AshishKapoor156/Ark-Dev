package com.ark.bus.bookbus.storedprocedure;

import java.lang.annotation.Annotation;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ark.bus.bookbus.commons.util.Config;
import com.ark.bus.bookbus.commons.util.Log;
//import com.fss.pos.base.factory.AbstractFactory;
//import com.fss.pos.base.factory.Factories;

/**
 * To initialize and register the {@link StoredProcedureApi} objects in spring
 * context.
 * 
 * @author Priyan
 */
@Component
public class StoredProcedureApiFactory {
	/*
	 * 
	 * @Autowired private Config config;
	 * 
	 *//**
		 * Prevent the StoredProcedureApiFactory class from being instantiated.
		 */
	/*
	 * private StoredProcedureApiFactory() {
	 * 
	 * }
	 * 
	 * @PostConstruct public void initApiCache() { Log.debug(
	 * "\n/////////////////////////////////////////// STARTING SERVER ///////////////////////////////////////////"
	 * , "\n"); Log.info(
	 * "\n/////////////////////////////////////////// STARTING SERVER ///////////////////////////////////////////"
	 * , "\n"); init(); }
	 * 
	 * @Override protected Factories getPrefix() { return Factories.STOREDPROCEDURE;
	 * }
	 * 
	 * @Override protected String getKey(Object object) { return
	 * object.getClass().getAnnotation(StoredProcedure.class).value(); }
	 * 
	 * @Override protected Class<? extends Annotation> getAnnotatedClass() { return
	 * StoredProcedure.class; }
	 * 
	 * @Override protected boolean isValid(Object key) { return
	 * config.getDbType().equals(key); }
	 * 
	 * @Override protected Class<?> getInterface() { return
	 * StoredProcedureApi.class; }
	 * 
	 */}
