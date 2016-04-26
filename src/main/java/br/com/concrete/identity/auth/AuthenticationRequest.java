package br.com.concrete.identity.auth;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

public class AuthenticationRequest {
	
	@NotNull
	@Email
	private String email;
	
	@NotNull
	private String password;
	
	public AuthenticationRequest() {}
	
	public AuthenticationRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}

}
