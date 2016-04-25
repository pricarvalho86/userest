package br.com.concrete.identity.user;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.concrete.identity.user.domain.Address;
import br.com.concrete.identity.user.domain.Token;
import br.com.concrete.identity.user.domain.User;
import br.com.concrete.identity.user.dto.UserAddressRequest;
import br.com.concrete.identity.user.dto.UserCreationRequest;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="/user", method=POST)
	public ResponseEntity<User> create(@RequestBody @Valid UserCreationRequest userCreate, HttpServletResponse response) {
		User user = userService.create(userCreate);
		response.setHeader("x-auth-token" , user.getToken());
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/user/address", method=POST)
	public ResponseEntity<Address> createAddress(@RequestBody @Valid UserAddressRequest userAdress, HttpServletRequest request, HttpServletResponse response) {
		String tokenCode = request.getHeader("x-auth-token");
		Optional<Token> token = userService.findByToken(tokenCode);
		User user = token.get().getUser();
		Address adressUser = userService.createUserAdress(userAdress.toAddress(user));
		response.setHeader("x-auth-token" , token.toString());
		return new ResponseEntity<Address>(adressUser, HttpStatus.CREATED);
	}

}
