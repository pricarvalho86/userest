package br.com.concrete.identity.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED, reason="Usuário e/ou senha inválidos")
public class AuthenticationException extends RuntimeException {

	private static final long serialVersionUID = -6693827697144913041L;

}
