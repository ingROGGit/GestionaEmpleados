package com.gestion.empleados.Dto;

import java.io.Serializable;

public class LoginDto implements Serializable{
	private static final long serialVersionUID = 1L;

	    private String username;
	    private String password;
	    public LoginDto(String username,String passeord) {
	    	this.username=username;
	    	this.password=passeord;
	    }
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
}
