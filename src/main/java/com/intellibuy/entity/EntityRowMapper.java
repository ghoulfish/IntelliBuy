package com.intellibuy.entity;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

public class EntityRowMapper<T extends EntityToDB> extends BeanPropertyRowMapper<T>{
	
	public EntityRowMapper() {
		super();
	}
	
	public EntityRowMapper(Class<T> mappedClass) {
		super(mappedClass);
	}
	
}
