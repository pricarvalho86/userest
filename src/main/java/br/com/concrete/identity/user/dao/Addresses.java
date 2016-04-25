package br.com.concrete.identity.user.dao;

import java.util.List;

import br.com.concrete.identity.user.domain.Address;
import br.com.concrete.identity.user.domain.User;

public interface Addresses {

	void save(Address address);

	List<Address> listAllBy(User user);

}
