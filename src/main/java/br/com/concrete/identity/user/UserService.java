package br.com.concrete.identity.user;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.concrete.identity.auth.UnauthorizedException;
import br.com.concrete.identity.user.dao.Addresses;
import br.com.concrete.identity.user.dao.Users;
import br.com.concrete.identity.user.domain.Address;
import br.com.concrete.identity.user.domain.User;
import br.com.concrete.identity.user.dto.UserCreationRequest;

@Service("userService")
public class UserService {

	@Autowired
	private Users users;
	
	@Autowired
	private Addresses addresses;
	
	public User create(UserCreationRequest userCreation) {
		User user = userCreation.toUser(users);
		users.save(user);
		return user;
	}
	
	public User createToken(User user) {
		user.generateToken();
		return users.update(user);
	}
	
	public boolean isTokenValid(String tokenCode) {
		Optional<User> user = this.findByToken(tokenCode);
		if (!user.isPresent() || user.get().tokenExpirated()) {
			throw new UnauthorizedException();
		}
		return true;
	}

	public Optional<User> findByToken(String tokenCode) {
		if (tokenCode == null) throw new UnauthorizedException();
		return users.findByToken(tokenCode);
	}

	public Address createUserAdress(Address address) {
		address.getUser().setModified(new Date());
		addresses.save(address);
		return address;
	}

	public List<Address> listAddressesBy(User user) {
		return addresses.listAllBy(user);
	}


}
