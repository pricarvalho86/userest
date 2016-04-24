package br.com.concrete.identity.auth;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.concrete.identity.user.domain.User;

@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService authService;

	@RequestMapping(value="/login", method=POST)
	public ResponseEntity<User> create(@RequestBody AuthenticationRequest login) {
		User user = authService.authenticate(login);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

}
