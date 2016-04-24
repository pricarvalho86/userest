package br.com.concrete.identity.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RestController;

import br.com.concrete.identity.user.domain.User;
import br.com.concrete.identity.user.dto.UserCreationRequest;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="/user", method=POST)
	public ResponseEntity<User> create(@RequestBody @Valid UserCreationRequest userCreate) {
		User user = userService.create(userCreate);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

}
