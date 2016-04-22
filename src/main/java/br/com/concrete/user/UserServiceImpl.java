package br.com.concrete.user;

import org.springframework.stereotype.Service;

import br.com.concrete.user.domain.User;

@Service("userService")
public class UserServiceImpl implements UserService {

	public boolean exist(User user)  {
		return user.getEmail().equals("teste@teste.com.br");
	}

	public void save(User user) {
		
	}

}
