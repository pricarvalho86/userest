package br.com.concrete.identity.user.dao;

import java.util.Optional;

import br.com.concrete.identity.user.domain.User;

public interface Users {

	void save(User user);

	Optional<User> findByEmail(String email);

	User update(User user);

	Optional<User> findByToken(String token);

}
