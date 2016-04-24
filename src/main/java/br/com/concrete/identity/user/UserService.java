package br.com.concrete.identity.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.concrete.identity.user.domain.User;
import br.com.concrete.identity.user.dto.UserCreationRequest;

@Service("userService")
public class UserService {

	@Autowired
	private Users users;
	
	public User create(UserCreationRequest userCreation) {
		User user = userCreation.toUser();
		users.save(user);
		return user;
	}

}
