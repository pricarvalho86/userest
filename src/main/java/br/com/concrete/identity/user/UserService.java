package br.com.concrete.identity.user;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.concrete.identity.auth.UnauthorizedException;
import br.com.concrete.identity.user.dao.Addresses;
import br.com.concrete.identity.user.dao.Tokens;
import br.com.concrete.identity.user.dao.Users;
import br.com.concrete.identity.user.domain.Address;
import br.com.concrete.identity.user.domain.Token;
import br.com.concrete.identity.user.domain.User;
import br.com.concrete.identity.user.dto.UserCreationRequest;

@Service("userService")
public class UserService {

	@Autowired
	private Users users;
	
	@Autowired
	private Tokens tokens;
	
	@Autowired
	private Addresses addresses;
	
	public User create(UserCreationRequest userCreation) {
		User user = userCreation.toUser(users);
		users.save(user);
		createToken(user);
		return user;
	}
	
	public Token createToken(User user) {
		Token token = Token.generate(user);
		user.setToken(token.toString());
		user.setLastLogin(new Date());
		tokens.save(token);
		return token;
	}
	
	public boolean isTokenValid(String tokenCode) {
		Optional<Token> token = this.findByToken(tokenCode);
		if (!token.isPresent() || token.get().isExpirated()) {
			throw new UnauthorizedException();
		}
		return true;
	}

	public Optional<Token> findByToken(String tokenCode) {
		if (tokenCode == null) throw new UnauthorizedException();
		return tokens.findByCode(tokenCode);
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
