package br.com.concrete.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.bind.annotation.RestController;

import br.com.concrete.user.domain.User;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="/user", method=PUT)
	public ResponseEntity<User> create(@RequestBody User user) {
		if(userService.exist(user)) throw new UserException();
		userService.save(user);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

}
