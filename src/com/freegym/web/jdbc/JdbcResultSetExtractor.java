package com.freegym.web.jdbc;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class JdbcResultSetExtractor implements ResultSetExtractor<List<Object>> {

	protected Logger log = Logger.getLogger(this.getClass());

	private Class<?> cls;

	public JdbcResultSetExtractor(Class<?> cls) {
		this.cls = cls;
	}

	@Override
	public List<Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
		final List<Object> list = new ArrayList<Object>();
		final ResultSetMetaData md = rs.getMetaData();
		final int cnt = md.getColumnCount();
		try {
			while (rs.next()) {
				final Object obj = cls.newInstance();
				for (int i = 1; i <= cnt; i++) {
					String name = md.getColumnName(i);
					Object value = rs.getObject(i);
					setValue(obj, name, value);
				}
				list.add(obj);
			}
		} catch (InstantiationException e) {
			log.error("error", e);
			throw new SQLException(e);
		} catch (IllegalAccessException e) {
			log.error("error", e);
			throw new SQLException(e);
		}
		return list;
	}

	protected void setValue(Object obj, String name, Object value) {
		try {
			BeanUtils.setProperty(obj, name, value);
		} catch (IllegalAccessException e) {
			log.error("error", e);
		} catch (InvocationTargetException e) {
			log.error("error", e);
		} catch (ConversionException e) {
			log.error("error", e);
		}
	}

}
