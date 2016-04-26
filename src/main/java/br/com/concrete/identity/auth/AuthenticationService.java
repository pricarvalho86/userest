package br.com.concrete.identity.auth;

import java.util.Date;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.concrete.identity.user.UserService;
import br.com.concrete.identity.user.dao.Users;
import br.com.concrete.identity.user.domain.User;

@Service("authenticationService")
public class AuthenticationService {

	@Inject
	private Users users;
	
	@Inject
	private UserService userService;
	
	AuthenticationService() { }
	
	public AuthenticationService(Users users, UserService userService) {
		this.users = users;
		this.userService = userService;
	}

	public User authenticate(AuthenticationRequest userAuth) {
		Optional<User> user = users.findByEmail(userAuth.getEmail());
		if (!user.isPresent() || !user.get().isPasswordValid(userAuth.getPassword())) {
			throw new AuthenticationException();
		}
		User userAuthenticated = user.get();
		userAuthenticated.setLastLogin(new Date());
		userService.createToken(userAuthenticated);
		return users.update(userAuthenticated);
	}

}
