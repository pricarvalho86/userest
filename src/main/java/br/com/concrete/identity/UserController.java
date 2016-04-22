package br.com.concrete.identity;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@RequestMapping(value ="/user", method = RequestMethod.POST)
	public User user(@RequestBody User user) {
		return user;
	}

}
