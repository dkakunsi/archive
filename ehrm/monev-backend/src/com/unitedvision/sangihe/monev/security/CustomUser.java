package com.unitedvision.sangihe.monev.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.unitedvision.sangihe.monev.entity.Operator;

public class CustomUser extends User {
	private static final long serialVersionUID = 1L;
	private Operator operator;

	public CustomUser(String username, String password, Operator operator, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.operator = operator;
	}
	
	public Operator getOperator() {
		return operator;
	}
	
	public String getUsername() {
		return operator.getUsername();
	}
}
