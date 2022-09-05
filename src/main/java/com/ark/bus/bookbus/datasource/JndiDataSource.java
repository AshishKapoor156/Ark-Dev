package com.ark.bus.bookbus.datasource;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jndi.JndiTemplate;
import org.springframework.stereotype.Component;

/**
 * An object to connect the jndi.
 * 
 * @author Priyan
 */
@Component
public class JndiDataSource {

	@Autowired
	private JndiTemplate jndiTemplate;

	public DataSource getDataSource(String jndiName) throws NamingException {
		return jndiTemplate.lookup(jndiName, DataSource.class);
	}

	@Bean(name = "jndiTemplate")
	private JndiTemplate jndi() {
		return new JndiTemplate();
	}

}
