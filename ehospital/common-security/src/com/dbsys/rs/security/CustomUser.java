package com.dbsys.rs.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.dbsys.rs.lib.entity.Operator;

/**
 * Principal untuk spring security.
 * 
 * @author Deddy Christoper Kakunsi
 *
 */
public class CustomUser extends User {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Operator yang login.
	 */
	private Operator operator;

	/**
	 * Membuat object user.
	 * 
	 * @param username
	 * @param password
	 * @param operatorEntity
	 * @param authorities
	 */
	public CustomUser(Operator operator, Collection<? extends GrantedAuthority> authorities) {
		this(operator, operator.getPassword(), authorities);
	}

	/**
	 * Membuat object user dengan password tertentu.
	 * 
	 * @param operatorEntity
	 * @param password
	 * @param authorities
	 */
	public CustomUser(Operator operator, String password, Collection<? extends GrantedAuthority> authorities) {
		super(operator.getUsername(), password, authorities);
		this.operator = operator;
	}

	/**
	 * Mengambil operatorEntity yang login.
	 * 
	 * @return operatorEntity yang login
	 */
	public Operator getOperator() {
		return operator;
	}

	/**
	 * Mengatur operatorEntity yang login
	 * 
	 * @param operatorEntity
	 */
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
}
