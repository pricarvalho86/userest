package br.com.concrete.identity.user;

import java.util.Optional;

import br.com.concrete.identity.user.domain.Token;

public interface Tokens {
	
	void save(Token token);

	Optional<Token> findByCode(String tokenCode);

}