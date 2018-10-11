package com.intellibuy.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.util.Assert;

public abstract class AbstractAuthDataSource extends DriverManagerDataSource{
	
	public AbstractAuthDataSource() {
		super();
		setUrl(yourUrl());
		setUsername(yourUsername());
		setPassword(yourPassword());
		setDriverClassName(yourDriverClassName());
	}

	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	protected Connection getConnectionFromDriver(Properties props) throws SQLException {
		String url = getUrl();
		Assert.state(url != null, "'url' not set");
		if (logger.isDebugEnabled()) {
			logger.debug("Creating new JDBC DriverManager Connection to [" + url + "]");
		}
		return getConnectionFromDriverManager(url, props);
	}	
	
	/**
	 * Getting a Connection using the nasty static from DriverManager is extracted
	 * into a protected method to allow for easy unit testing.
	 * @see java.sql.DriverManager#getConnection(String, java.util.Properties)
	 */
	protected Connection getConnectionFromDriverManager(String url, Properties props) throws SQLException {
		return DriverManager.getConnection(url, props);
	}

	public abstract String yourUrl();
	
	public abstract String yourUsername();
	
	public abstract String yourPassword();
	
	public abstract String yourDriverClassName();
	
	
}
