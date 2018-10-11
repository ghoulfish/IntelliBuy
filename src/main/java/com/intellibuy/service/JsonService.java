package com.intellibuy.service;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellibuy.entity.ProductInCart;

@Service
public class JsonService {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public byte[] cookieValueToJson(String base64) {
		return cookieValueToJson(base64.getBytes());
	}
	public byte[] cookieValueToJson(byte[] base64) {
		return Base64.getDecoder().decode(base64);
	}
	
	public byte[] jsonToCookieValue(String json) {
		return jsonToCookieValue(json.getBytes());
	}
	
	public byte[] jsonToCookieValue(byte[] json) {
		return Base64.getEncoder().encode(json);
	}
	
	public <T> T cookieValueToObject(String cookieValue, Class<T> tClass) {
		try {
			return objectMapper.readValue(cookieValueToJson(cookieValue), tClass);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public <T> String objectToCookieValue(T obj) throws JsonProcessingException {
		return new String(jsonToCookieValue(objectMapper.writeValueAsBytes(obj)));
	}
	
	public Map<Integer, ProductInCart> getCartProductMap(String cartCookieValue)  {
		try {
			return objectMapper.readValue(cookieValueToJson(cartCookieValue), new TypeReference<Map<Integer, ProductInCart>>(){});
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	

}
