package br.com.concrete.identity.user;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.concrete.identity.user.domain.User;
import br.com.concrete.identity.user.dto.UserCreationRequest;


@RestController
@RequestMapping(consumes=APPLICATION_JSON_VALUE, produces=APPLICATION_JSON_VALUE)
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="/user", method=PUT)
	public ResponseEntity<User> create(@RequestBody UserCreationRequest userCreate) {
		User user = userService.create(userCreate);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

}
