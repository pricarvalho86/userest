package br.com.concrete.identity.auth;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.concrete.identity.user.domain.User;

@RestController
@RequestMapping(produces=APPLICATION_JSON_VALUE, consumes=APPLICATION_JSON_VALUE)
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService authService;

	@RequestMapping(value="/login", method=POST)
	public ResponseEntity<User> login(@RequestBody @Valid AuthenticationRequest login, HttpServletResponse response) {
		User user = authService.authenticate(login);
		response.setHeader("x-auth-token", user.getToken());
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value="/logout", method=POST)
	public HttpStatus logout(HttpServletResponse response) {
		response.setHeader("x-auth-token", null);
		return HttpStatus.OK;
	}

}
