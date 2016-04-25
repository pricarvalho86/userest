package br.com.concrete.identity.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED, reason="Não autorizado")
public class UnauthorizedException extends RuntimeException {

	private static final long serialVersionUID = -6693827697144913041L;

}
