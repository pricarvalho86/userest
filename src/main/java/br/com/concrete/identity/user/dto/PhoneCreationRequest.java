package br.com.concrete.identity.user.dto;

import br.com.concrete.identity.user.domain.Phone;

public class PhoneCreationRequest {

	private String ddd;
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
