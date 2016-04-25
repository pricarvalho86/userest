package br.com.concrete.identity.user.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import br.com.concrete.identity.user.domain.Address;
import br.com.concrete.identity.user.domain.User;

public class AddressRequest {

	@NotNull
	private String street;
	
	@NotNull
	private String number;
	
	@NotNull
	private String neighborhood;
	
	@NotNull
	private String city;
	
	@NotNull
	@Size(min=2, max=2, message="{state.size.error}")
	private String state;
	
	@NotNull
	@Pattern(regexp="^[0-9]{5}-[0-9]{3}$", message="{zipcode.format.error}")
	private String zipcode;
	
	public Address toAddress(User user) {
		return new Address(street, number, neighborhood, city, state, zipcode, user);
	}

	public String getStreet() {
		return street;
	}

	public String getNumber() {
		return number;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZipcode() {
		return zipcode;
	}

}
