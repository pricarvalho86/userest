package br.com.concrete.identity.auth;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.concrete.identity.user.UserService;
import br.com.concrete.identity.user.dao.Users;
import br.com.concrete.identity.user.domain.User;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {
	
	private static final String PASSWORD = "somePassword";
	private static final String EMAIL = "some.email@someservice.com";
	
	@Mock
	private Users users;
	
	@Mock
	private UserService userService;

	private AuthenticationService subject;
	
	@Before
	public void setup() {
		subject = new AuthenticationService(users, userService);
	}

	@Test
	public void deveAtualizarUltimoLoginDeUsuarioValido() {
		User user = Mockito.mock(User.class);
		when(users.findByEmail(EMAIL)).thenReturn(Optional.of(user));
		when(user.isPasswordValid(PASSWORD)).thenReturn(true);
		
		subject.authenticate(new AuthenticationRequest(EMAIL, PASSWORD));
		verify(user).setLastLogin(Mockito.any(Date.class));
		verify(users).update(user);
	}
	
	@Test(expected=AuthenticationException.class)
	public void deveRetornarExceptionAoInformarEmailInvalido() {
		when(users.findByEmail(EMAIL)).thenReturn(Optional.<User>empty());
		subject.authenticate(new AuthenticationRequest(EMAIL, PASSWORD));
	}
	
	@Test(expected=AuthenticationException.class)
	public void deveRetornarExceptionAoInformarSenhaInvalida() {
		User user = Mockito.mock(User.class);
		when(users.findByEmail(EMAIL)).thenReturn(Optional.of(user));
		when(user.isPasswordValid(PASSWORD)).thenReturn(false);
		
		subject.authenticate(new AuthenticationRequest(EMAIL, PASSWORD));
	}

}
