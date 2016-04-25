package br.com.concrete.identity.user.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import br.com.concrete.identity.user.domain.Address;
import br.com.concrete.identity.user.domain.User;

public class UserAddressRequest {

	@NotNull
	private String logradouro;
	
	@NotNull
	private String numero;
	
	@NotNull
	private String bairro;
	
	@NotNull
	private String cidade;
	
	@NotNull
	@Size(max=2)
	private String estado;
	
	@NotNull
	@Pattern(regexp="^[0-9]{5}-[0-9]{3}$")
	private String cep;
	
	public Address toAddress(User user) {
		return new Address(logradouro, numero, bairro, cidade, estado, cep, user);
	}

}
