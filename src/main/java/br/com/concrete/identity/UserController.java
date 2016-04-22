package br.com.concrete.identity;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@RequestMapping("/user")
    public User user(@RequestParam(value="name", defaultValue="Priscila") String name,
    		@RequestParam(value="email", defaultValue="teste@teste.com.br") String email,
    		@RequestParam(value="password", defaultValue="1234qwer") String password) {
        return new User(name, email, password, new ArrayList<Phone>());    }

}
