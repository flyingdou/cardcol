package com.databaseConnection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class DataBaseConnection {
	private static String DATABASE_DRIVER;
	private static String DATABASE_URL;
	private static String DATABASE_USERNAME;
	private static String DATABASE_PASSWORD;

	private static Connection link(String errorInfo) {
		try {
			Class.forName(DATABASE_DRIVER);
			return (Connection) DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
		} catch (Exception e) {
			if (errorInfo != null) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Connection getConnection() {

		DATABASE_DRIVER = "com.mysql.jdbc.Driver";
		DATABASE_URL = "jdbc:mysql://123.56.146.107:3306/cardcolv4?characterEncoding=utf-8";
		DATABASE_USERNAME = "root";
		DATABASE_PASSWORD = "cj@zjy@2010.07.01";
		Connection conn = link(null);

		if (conn == null) {
			DATABASE_DRIVER = "com.mysql.jdbc.Driver";
			DATABASE_URL = "jdbc:mysql://192.168.3.14:3306/cardcolv4?characterEncoding=utf-8";
			DATABASE_USERNAME = "wuzhijian";
			DATABASE_PASSWORD = "wuzhijian";
			conn = link("");
		}

		return conn;
	}

	public static ResultSet getResultSet(String sql, Object[] obj) {
		Connection conn = getConnection();
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			preStmt = (PreparedStatement) conn.prepareStatement(sql);
			if (obj != null) {
				for (int i = 0; i < obj.length; i++) {
					preStmt.setObject(i + 1, obj[i]);
				}
			}
			rs = preStmt.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	@SuppressWarnings("unused")
	public static Object getOne(String sql, Object[] obj, Class<?> myClass) {
		Connection conn = getConnection();
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		ResultSetMetaData metaData = null;
		Object o = null;
		Method method = null;
		StringBuffer strBuff = null;
		StringBuffer[] strBuffs = null;
		String methodName = null;
		try {
			Field[] fields = myClass.getDeclaredFields();
			preStmt = (PreparedStatement) conn.prepareStatement(sql);
			if (obj != null) {
				for (int i = 0; i < obj.length; i++) {
					preStmt.setObject(i + 1, obj[i]);
				}
			}
			o = myClass.newInstance();
			rs = preStmt.executeQuery();
			metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					for (int j = 0; j < fields.length; j++) {
						if (metaData.getColumnName(i).replace("_", "").toUpperCase()
								.equals(fields[j].getName().replace("_", "").toUpperCase())) {
							strBuff = new StringBuffer(fields[j].getName());
							strBuff = new StringBuffer(strBuff.substring(0, 1).toUpperCase() + strBuff.substring(1));
							strBuff.insert(0, "set");
							method = myClass.getMethod(strBuff.toString(), new Class[] { fields[j].getType() });
							try {
								method.invoke(o, new Object[] { rs.getObject(metaData.getColumnName(i)) });
							} catch (Exception e) {
								method.invoke(o, new Object[] { null });
							}
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, preStmt, rs);
		}
		return o;
	}

	public static Map<String, Object> getOne(String sql, Object[] obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		Connection conn = getConnection();
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			preStmt = (PreparedStatement) conn.prepareStatement(sql);
			if (obj != null) {
				for (int i = 0; i < obj.length; i++) {
					preStmt.setObject(i + 1, obj[i]);
				}
			}
			rs = preStmt.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			int columeCount = meta.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columeCount; i++) {
					map.put(meta.getColumnName(i), rs.getObject(meta.getColumnName(i)));
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, preStmt, rs);
		}
		return map;
	}

	@SuppressWarnings("unused")
	public static Object load(String tableNameOrSql, Class<?> myClass, Object id) {
		Connection conn = getConnection();
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		ResultSetMetaData metaData = null;
		Object o = null;
		Method method = null;
		StringBuffer strBuff = null;
		StringBuffer[] strBuffs = null;
		String methodName = null;
		String querySql = null;
		if (tableNameOrSql.indexOf("select") != -1) {
			querySql = tableNameOrSql;
		} else {
			querySql = "select * from " + tableNameOrSql + " where id=" + id;
		}
		try {
			Field[] fields = myClass.getDeclaredFields();
			preStmt = (PreparedStatement) conn.prepareStatement(querySql);
			rs = preStmt.executeQuery();
			metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				o = myClass.newInstance();
				for (int i = 1; i <= columnCount; i++) {
					for (int j = 0; j < fields.length; j++) {
						if (metaData.getColumnName(i).replace("_", "").toUpperCase()
								.equals(fields[j].getName().replace("_", "").toUpperCase())) {
							strBuff = new StringBuffer(fields[j].getName());
							strBuff = new StringBuffer(strBuff.substring(0, 1).toUpperCase() + strBuff.substring(1));
							strBuff.insert(0, "set");
							method = myClass.getMethod(strBuff.toString(), new Class[] { fields[j].getType() });
							try {
								method.invoke(o, new Object[] { rs.getObject(metaData.getColumnName(i)) });
							} catch (Exception e) {
								method.invoke(o, new Object[] { null });
							}
							break;
						}
					}
				}
				method = myClass.getMethod("setId", new Class[] { Long.class });
				method.invoke(o, new Object[] { rs.getLong("id") });
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, preStmt, rs);
		}
		return o;
	}

	@SuppressWarnings("unused")
	public static List<?> loadList(String tableNameOrSql, Class<?> myClass) {
		List<Object> list = new ArrayList<Object>();
		Connection conn = getConnection();
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		ResultSetMetaData metaData = null;
		Object o = null;
		Method method = null;
		StringBuffer strBuff = null;
		StringBuffer[] strBuffs = null;
		String methodName = null;
		String querySql = null;
		if (tableNameOrSql.indexOf("select") != -1) {
			querySql = tableNameOrSql;
		} else {
			querySql = "select * from " + tableNameOrSql;
		}
		try {
			Field[] fields = myClass.getDeclaredFields();
			preStmt = (PreparedStatement) conn.prepareStatement(querySql);
			rs = preStmt.executeQuery();
			metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				o = myClass.newInstance();
				for (int i = 1; i <= columnCount; i++) {
					for (int j = 0; j < fields.length; j++) {
						if (metaData.getColumnName(i).replace("_", "").toUpperCase()
								.equals(fields[j].getName().replace("_", "").toUpperCase())) {
							strBuff = new StringBuffer(fields[j].getName());
							strBuff = new StringBuffer(strBuff.substring(0, 1).toUpperCase() + strBuff.substring(1));
							strBuff.insert(0, "set");
							method = myClass.getMethod(strBuff.toString(), new Class[] { fields[j].getType() });
							try {
								method.invoke(o, new Object[] { rs.getObject(metaData.getColumnName(i)) });
							} catch (Exception e) {
								method.invoke(o, new Object[] { null });
							}
							break;
						}
					}
				}
				method = myClass.getMethod("setId", new Class[] { Long.class });
				method.invoke(o, new Object[] { rs.getLong("id") });
				list.add(o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, preStmt, rs);
		}
		return list;
	}

	@SuppressWarnings("unused")
	public static List<?> getList(String sql, Object[] obj, Class<?> myClass) {
		List<Object> list = new ArrayList<Object>();
		Connection conn = getConnection();
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		ResultSetMetaData metaData = null;
		Object o = null;
		Method method = null;
		StringBuffer strBuff = null;
		StringBuffer[] strBuffs = null;
		String methodName = null;
		try {
			Field[] fields = myClass.getDeclaredFields();
			preStmt = (PreparedStatement) conn.prepareStatement(sql);
			if (obj != null) {
				for (int i = 0; i < obj.length; i++) {
					preStmt.setObject(i + 1, obj[i]);
				}
			}
			rs = preStmt.executeQuery();
			metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				o = myClass.newInstance();
				for (int i = 1; i <= columnCount; i++) {
					for (int j = 0; j < fields.length; j++) {
						if (metaData.getColumnName(i).replace("_", "").toUpperCase()
								.equals(fields[j].getName().replace("_", "").toUpperCase())) {
							strBuff = new StringBuffer(fields[j].getName());
							strBuff = new StringBuffer(strBuff.substring(0, 1).toUpperCase() + strBuff.substring(1));
							strBuff.insert(0, "set");
							method = myClass.getMethod(strBuff.toString(), new Class[] { fields[j].getType() });
							method.invoke(o, new Object[] { rs.getObject(metaData.getColumnName(i)) });
							break;
						}
					}
				}
				list.add(o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, preStmt, rs);
		}
		return list;
	}

	public static List<Map<String, Object>> getList(String sql, Object[] obj) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		Connection conn = getConnection();
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		try {
			preStmt = (PreparedStatement) conn.prepareStatement(sql);
			if (obj != null) {
				for (int i = 0; i < obj.length; i++) {
					preStmt.setObject(i + 1, obj[i]);
				}
			}
			rs = preStmt.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			int columeCount = meta.getColumnCount();
			while (rs.next()) {
				map = new HashMap<String, Object>();
				for (int i = 1; i <= columeCount; i++) {
					map.put(meta.getColumnName(i), rs.getObject(meta.getColumnName(i)));
				}
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, preStmt, rs);
		}
		return list;
	}

	public static int updateData(String sql, Object[] obj) {
		int count = -1;
		Connection conn = getConnection();
		PreparedStatement preStmt = null;
		try {
			preStmt = (PreparedStatement) conn.prepareStatement(sql);
			if (obj != null) {
				for (int i = 0; i < obj.length; i++) {
					preStmt.setObject(i + 1, obj[i]);
				}
			}
			count = preStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, preStmt, null);
		}
		return count;
	}

	private static void close(Connection conn, PreparedStatement preStmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (preStmt != null) {
				preStmt.close();
			}
			if (conn != null) {
				preStmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
