package com.ark.bus.bookbus.storedprocedure;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to identify the {@link StoredProcedureApi} object from the
 * factory. The only identification of the stored procedure object to get
 * registered in the factory.
 * 
 * @author Priyan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface StoredProcedure {

	String value();

}
