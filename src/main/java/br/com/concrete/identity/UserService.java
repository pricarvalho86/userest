package br.com.concrete.identity;

import br.com.concrete.identity.model.User;

public interface UserService {

	boolean exist(User user);

	void save(User user);

}
