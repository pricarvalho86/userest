package br.com.concrete.identity.user;

import java.util.Optional;

import br.com.concrete.identity.user.domain.User;

public interface Users {

	void save(User user);

	Optional<User> findByEmail(String email);

}
