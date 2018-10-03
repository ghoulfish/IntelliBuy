package com.intellibuy.service;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.http.Cookie;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellibuy.entity.CustomerLoginInfo;

@Service
public class JsonParser {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private byte[] Base64ToJson(String base64) {
		return Base64ToJson(base64.getBytes());
	}
	private byte[] Base64ToJson(byte[] base64) {
		return Base64.getDecoder().decode(base64);
	}
	
	private byte[] JsonToBase64(String json) {
		return JsonToBase64(json.getBytes());
	}
	
	private byte[] JsonToBase64(byte[] json) {
		return Base64.getEncoder().encode(json);
	}

	public CustomerLoginInfo getCustomerLoginInfo(Cookie loginCookie) throws IOException {
		String cookieValue = loginCookie.getValue();
		return objectMapper.readValue(Base64ToJson(cookieValue), CustomerLoginInfo.class);
	}

	public Cookie getCustomerLoginCookie(CustomerLoginInfo custLogin) throws IOException {
		String name = "login";
		String value = new String(JsonToBase64(objectMapper.writeValueAsBytes(custLogin)));
		return new Cookie(name, value);
	}

}
