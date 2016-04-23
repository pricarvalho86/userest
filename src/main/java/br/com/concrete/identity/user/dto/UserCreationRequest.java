package br.com.concrete.identity.user.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.concrete.identity.user.domain.Phone;
import br.com.concrete.identity.user.domain.User;

public class UserCreationRequest {
	
	private String name;
	private String email;
	private String password;
	
	private List<PhoneCreationRequest> phones;
	
	public User toUser() {
		return new User(name, email, password, getPhonesList());
	}

	private List<Phone> getPhonesList() {
		List<Phone> phones = new ArrayList<>();
		for (PhoneCreationRequest phoneCreation : this.phones) {
			phones.add(phoneCreation.toPhone());
		}
		return phones;
	}
	
	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public List<PhoneCreationRequest> getPhones() {
		return phones;
	}

}
