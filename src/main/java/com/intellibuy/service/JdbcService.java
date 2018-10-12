package com.intellibuy.service;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;

import com.intellibuy.entity.Customer;
import com.intellibuy.entity.EntityRowMapper;
import com.intellibuy.entity.EntityToDB;
import com.intellibuy.entity.Order;
import com.intellibuy.entity.Product;
import com.intellibuy.entity.ProductInCart;

@Service
public class JdbcService {
	
	private AuthDataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public JdbcService() {
		super();
		this.dataSource = new AuthDataSource();
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public <T extends EntityToDB> boolean checkTableValidation(T entity) {
		createTable(entity);
		return true;
	}

	public <T extends EntityToDB> void addToDatabase(T entity) {
		jdbcTemplate.update(getSqlInsertStatement(entity), getPropertyValueArray(entity));
		System.out.println("Add information successfully.");
	}
	
	public <T extends EntityToDB> void addToDatabase(T entity, GeneratedKeyHolder generatedKeyHolder) {
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(getSqlInsertStatement(entity), new String[] {"id"});
				Object[] values = getPropertyValueArray(entity);
				for (int i = 0; i < values.length; i++) {
					ps.setObject(i+1, values[i]);
				}
				return ps;
			}
		}, generatedKeyHolder);
		System.out.println("Add information successfully. Last_Insert_ID is: " + generatedKeyHolder.getKey().intValue());
	}
	
	public <T extends EntityToDB> void updateDatabase(T entity, String identifierName, String identifier) {
		jdbcTemplate.update(getSqlUpdateStatement(entity, identifierName, identifier), getPropertyValueArray(entity));
		System.out.println("Update information successfully.");
	}	
	
	public <T extends EntityToDB> void deleteDatabase(T entity, String identifierName, String identifier) {
		jdbcTemplate.execute(getSqlDeleteStatement(entity, identifierName, identifier));
		System.out.println("Delete information successfully.");
	}
	
	public <T extends EntityToDB> List<T> find(T entity, String identifierName, String identifier) {
		String sql = "SELECT * FROM "
				+ entity.tableName()
				+ " WHERE " 
				+ camelToSqlStyle(identifierName) 
				+ " = ?";
		List<T> query = jdbcTemplate.query(sql, new Object[] {identifier}, new EntityRowMapper<T>((Class<T>) entity.getClass()));
		return query;
	}
	
	public <T extends EntityToDB> List<T> findAll(T entity) {
		String sql = "SELECT * FROM "
				+ entity.tableName();
		List<T> query = jdbcTemplate.query(sql, new EntityRowMapper<T>((Class<T>) entity.getClass()));
		return query;
	}
	/**
	 * Get a SQL preparedStatement: INSERT INTO tableName (var1, var2,...) value (?, ?,...);
	 * @param tableName 
	 * @return String 
	 */
	public <T extends EntityToDB> String getSqlInsertStatement( T entity ) {
		Field[] fields = entity.getClass().getDeclaredFields();
		String tableName = entity.tableName();
		String sql = "INSERT INTO " + tableName + " (";
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().equalsIgnoreCase("id")) { continue; }
			if (entity.getSqlType(fields[i]) == null) { continue;}
			sql = sql + camelToSqlStyle(fields[i].getName()) + ",";
		}
		sql = sql.substring(0, sql.length()-1) + ") value (";
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().equalsIgnoreCase("id")) { continue; }
			if (entity.getSqlType(fields[i]) == null) { continue;}
			sql = sql + "?,";
		}
		sql = sql.substring(0, sql.length()-1) + ")";
		return sql;
	}
	
	public <T extends EntityToDB> String getSqlUpdateStatement( T entity, String identifierName, String identifier ) {
		Field[] fields = entity.getClass().getDeclaredFields();
		String tableName = entity.tableName();
		String sql = "UPDATE " + tableName + " SET ";
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().equalsIgnoreCase("id") || fields[i].getName().equalsIgnoreCase(identifierName)) { continue; }
			if (entity.getSqlType(fields[i]) == null) { continue;}
			sql = sql + camelToSqlStyle(fields[i].getName()) + "=?,";
		}
		sql = sql.substring(0, sql.length() - 1) + " WHERE " + identifierName + " = " + identifier;
		return sql;
	}	
	
	public <T extends EntityToDB> String getSqlDeleteStatement( T entity, String identifierName, String identifier ) {
		String tableName = entity.tableName();
		String sql = "DELETE FROM " + tableName + " WHERE " + identifierName + "=" + identifier;
		return sql;
	}
	
	public <T extends EntityToDB> Object[] getPropertyValueArray(T entity){
		return entity.fieldValueNoId();
	}
	
	/**
	 * Get a SQL-style variable name: "user_name"
	 * @param camelStyle A string like "userName"
	 * @return String
	 */
	private String camelToSqlStyle( String camelStyle ) {
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

	public Customer findByUsername(String username) {
		List<Customer> custList = find(new Customer(), "username", username);
		if (custList.isEmpty()) { return null;}
		return custList.get(0);
	}

	public List<Customer> findAllUser() {
		return findAll(new Customer());
	}

	public Product findProductByName(String name) {
		List<Product> prodList = find(new Product(), "name",  name);
		if (prodList.isEmpty()) { return null;}
		return prodList.get(0);
	}

	public void init() {
		createTable(new Customer());
		createTable(new Product());
		createTable(new Order());
		createTable(new ProductInCart());
	}
	
	public void destroy() {
		deleteTable(new Customer());
		deleteTable(new Product());
		deleteTable(new Order());
		deleteTable(new ProductInCart());
		
	}

	private <T extends EntityToDB> void createTable(T entity) {
		jdbcTemplate.execute(entity.createTableSql());
		System.out.println("Create table "+ entity.tableName() + " successfully.");
	}
	
	private <T extends EntityToDB> void deleteTable(T entity) {
		jdbcTemplate.execute("drop table " + entity.tableName());
		System.out.println("Drop table " + entity.tableName() + " successfully.");
	}

	public void addProduct(Product product) {
		product.setCreateSince(LocalDateTime.now());
		product.setUpdateSince(LocalDateTime.now());
		System.out.println("Add " + product + " to database.");;
		addToDatabase(product, new GeneratedKeyHolder());
	}

	public Product findProductById(Integer id) {
		List<Product> productList = find(new Product(), "id", id.toString());
		if (productList.isEmpty()) {
			return null;
		} else {
			return productList.get(0);
		}
	}

	public void updateProduct(Product productNew) {
		Product productOld = findProductById(productNew.getId());
		productNew.setCreateSince(productOld.getCreateSince());
		productNew.setUpdateSince(LocalDateTime.now());
		updateDatabase(productNew, "id", productNew.getId().toString());
	}

	public void deleteProductById(Integer id) {
		deleteDatabase(new Product(), "id", id.toString());
	}

	public void addOrder(Order order, GeneratedKeyHolder keyHolder) {
		order.setCreateSince(LocalDateTime.now());
		addToDatabase(order, keyHolder);
	}

	public void addOrderlines(ProductInCart prod) {
		addToDatabase(prod);
	}

	public Order findOrderById(Integer id) {
		List<Order> orderList = find(new Order(), "id", id.toString());
		if (orderList.isEmpty()) {
			return null;
		} else {
			return orderList.get(0);
		}
	}
	
	public List<Order> findOrderByCustomerId(Integer customerId) {
		List<Order> orders = find(new Order(), "customer_id", customerId.toString());
		for (Order order:orders) {
			order.setProducts(findOrderlineByOrderId(order.getId()));
		}
		return orders;
	}

	public void updateOrderPayment(Integer id, boolean paid) {
		Order order = findOrderById(id);
		order.setPaid(paid);
		updateDatabase(order, "id", id.toString());
	}

	public boolean hasEnoughStock(Order order) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean checkProductStock(ProductInCart prod) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void removeNoStock(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	public Object findAllOrder() {
		List<Order> orders = findAll(new Order());
		for (Order order:orders) {
			order.setProducts(findOrderlineByOrderId(order.getId()));
		}
		return orders;
	}

	private List<ProductInCart> findOrderlineByOrderId(Integer id) {
		return find(new ProductInCart(), "orderId", id.toString());
	}

	public void decreaseProductStock(Integer orderId) {
		for (ProductInCart prod: find(new ProductInCart(), "orderId", orderId.toString())) {
			Product p = findProductById(prod.getProductId());
			p.setStock(p.getStock()-prod.getNumber());
			updateProduct(p);
		}
	}

	public String getPassword(String username) {
		List<Customer> customers = find(new Customer(), "username", username);
		if (customers.isEmpty()) {
			return null;			
		} else {
			return customers.get(0).getPassword();
		}
	}

}
