package br.com.concrete.identity.user.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import br.com.concrete.identity.user.domain.Phone;

public class PhoneCreationRequest {

	@NotNull
	@Size(min=3, max=3)
	private Integer ddd;
	
	@NotNull
	@Pattern(regexp = "(\\d{3,5})-?(\\d{4})$", message="The pattern of this field is ####-#### or #####-####")
	private String number;
	
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
