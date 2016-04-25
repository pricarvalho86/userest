package br.com.concrete.identity.user.dto;

import javax.validation.constraints.NotNull;

import br.com.concrete.identity.user.domain.Phone;

public class PhoneCreationRequest {

	@NotNull
	private String ddd;
	
	@NotNull
	private String number;
	
	public Phone toPhone() {
		return new Phone(ddd, number);
	}
	
	public String getDdd() {
		return ddd;
	}
	
	public String getNumber() {
		return number;
	}
	
	
}
