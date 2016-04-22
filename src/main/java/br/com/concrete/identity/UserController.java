package br.com.concrete.identity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.concrete.identity.model.User;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="/user", method=RequestMethod.POST)
	public ResponseEntity<User> cadastrar(@RequestBody User user) {
		if(userService.isExist(user)) throw new UserException();
		userService.save(user);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

}
