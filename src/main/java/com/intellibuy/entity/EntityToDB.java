package com.intellibuy.entity;

import java.lang.reflect.Field;
import java.util.Arrays;

public class EntityToDB {
	
	private Integer id;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	//Default create table sql statement.
	public String createTableSql() {
		String sql = "CREATE TABLE IF NOT EXISTS "
				+ this.tableName() + " ("
				+ "id int not null auto_increment, "
				+ "primary key (id)";
		Field[] fields = this.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().equalsIgnoreCase("id")) { continue; }
			if (getSqlType(fields[i]) == null) { continue;}
			sql = sql + ", "
					+ camelToSqlStyle(fields[i].getName()) + " "
					+ getSqlType(fields[i]) +" ";
		}
		sql = sql + ")";
		return sql;
	}
	
	public String tableName() {
		return this.getClass().getName().replace(".", "_");
	}
	
	public String tableValidation() {
		return null;
	}
	
	/**
	 * Get an value array of all variables in this class.
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public Object[] fieldValueNoId() {
		Field[] fields = this.getClass().getDeclaredFields();
		Object[] values = new Object[fields.length-1];
		boolean passId = false;
		int unused = 1;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().equalsIgnoreCase("id")) { passId = true; continue;}
			if (getSqlType(fields[i]) == null) { unused++; continue;}
			try {
				fields[i].setAccessible(true);
				if (passId) {
					values[i-1] = fields[i].get(this);
				} else {
					values[i] = fields[i].get(this);					
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return Arrays.copyOf(values, fields.length-unused);
	}


	/**
	 * Get a SQL-style variable name: "user_name"
	 * @param camelStyle A string like "userName"
	 * @return String
	 */
	public String camelToSqlStyle( String camelStyle ) {
		StringBuffer sqlStyle = new StringBuffer();
		StringBuffer sbCamel = new StringBuffer(camelStyle);
		
		for (int i = 0; i < sbCamel.length(); i++) {
			if (Character.isUpperCase(sbCamel.charAt(i))) {
				sqlStyle.append('_');
				sqlStyle.append(Character.toLowerCase(sbCamel.charAt(i)));
			} else {
				sqlStyle.append(sbCamel.charAt(i));
			}
		}
		
		return sqlStyle.toString();
	}
	
	/**
	 * Get a camel-style variable name: "userName"
	 * @param sqlStyle A string like "user_name"
	 * @return String
	 */
	public String sqlStyleToCamel ( String sqlStyle ) {
		StringBuffer sbCamel = new StringBuffer();
		StringBuffer sbSql = new StringBuffer(sqlStyle);
		
		for (int i = 0; i < sbSql.length(); i++) {
			if (sbSql.charAt(i) == '_' ) {
				sbCamel.append(Character.toUpperCase(sbSql.charAt(++i)));
			} else {
				sbCamel.append(sbSql.charAt(i));
			}
		}
		return sbCamel.toString();
	}
	
	public String getSqlType(Field field) {
		switch (field.getType().getSimpleName()) {
		case "String":
			return "varchar(255)";
		case "Integer":
			return "int";
		case "int":
			return "int";
		case "Double":
			return "double";
		case "double":
			return "double";
		case "Boolean":
			return "boolean";
		case "boolean":
			return "boolean";
		case "LocalDate":
			return "date";
		case "LocalTime":
			return "time";
		case "LocalDateTime":
			return "datetime";
		default:
			return null;
		}
	}

}
