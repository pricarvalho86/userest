package br.com.concrete.identity.user;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;
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
import br.com.concrete.identity.user.domain.User;
import br.com.concrete.identity.user.dto.AddressRequest;
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
	public ResponseEntity<Address> createAddress(@RequestBody @Valid AddressRequest userAdress, HttpServletRequest request, HttpServletResponse response) {
		String token = request.getHeader("x-auth-token");
		Optional<User> user = userService.findByToken(token);
		Address adressUser = userService.createUserAdress(userAdress.toAddress(user.get()));
		response.setHeader("x-auth-token" , token);
		return new ResponseEntity<Address>(adressUser, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/user/address", method=GET)
	public ResponseEntity<List<Address>> addressess(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getHeader("x-auth-token");
		Optional<User> user = userService.findByToken(token);
		List<Address> adresses = userService.listAddressesBy(user.get());
		response.setHeader("x-auth-token" , token);
		return new ResponseEntity<List<Address>>(adresses, HttpStatus.OK);
	}

}
