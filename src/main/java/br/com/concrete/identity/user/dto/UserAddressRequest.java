package br.com.concrete.identity.user.dto;

import br.com.concrete.identity.user.domain.Address;
import br.com.concrete.identity.user.domain.User;

public class UserAddressRequest {

	private String logradouro;
	
	private String numero;
	
	private String bairro;
	
	private String cidade;
	
	private String estado;
	
	private String cep;
	
	public Address toAddress(User user) {
		return new Address(logradouro, numero, bairro, cidade, estado, cep, user);
	}

}
