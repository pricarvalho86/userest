package br.com.concrete.identity.user.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import br.com.concrete.identity.user.domain.Phone;

public class PhoneCreationRequest {

	@NotNull
	private Integer ddd;
	
	@NotNull
	@Pattern(regexp = "(\\d{3,5})-?(\\d{4})$", message="{phone.format.error}")
	private String number;
	
	public PhoneCreationRequest() {}
	
	public PhoneCreationRequest(int ddd, String number) {
		this.ddd = ddd;
		this.number = number;
	}

	public Phone toPhone() {
		return new Phone(ddd, number);
	}
	
	public Integer getDdd() {
		return ddd;
	}
	
	public String getNumber() {
		return number;
	}
	
	
}
