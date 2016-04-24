package br.com.concrete.identity.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.concrete.identity.user.Users;
import br.com.concrete.identity.user.domain.User;

@Service("authenticationService")
public class AuthenticationService {

	@Autowired
	private Users users;
	
	public User authenticate(AuthenticationRequest userAuth) {
		Optional<User> user = users.findByEmail(userAuth.getEmail());
		
		if (!user.isPresent() || 
				!user.get().isValidPassword(userAuth)) {
			throw new AuthenticationException();
		}
		
		return user.get();
	}

}
