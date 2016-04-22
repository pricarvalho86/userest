package br.com.concrete.user;

import br.com.concrete.user.domain.User;

public interface UserService {

	boolean exist(User user);

	void save(User user);

}
