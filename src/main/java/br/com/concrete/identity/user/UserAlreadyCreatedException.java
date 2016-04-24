package br.com.concrete.identity.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason="E-mail já existente")
public class UserAlreadyCreatedException extends RuntimeException {

	private static final long serialVersionUID = -6693827697144913041L;

}
