package br.com.concrete.identity.auth;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.jayway.restassured.response.Response;

import br.com.concrete.RestAssuredConfigTest;
import br.com.concrete.identity.user.domain.User;
import br.com.concrete.identity.user.dto.PhoneCreationRequest;
import br.com.concrete.identity.user.dto.UserCreationRequest;
import br.com.concrete.json.model.JsonException;

public class AuthenticationControllerTest extends RestAssuredConfigTest {
	
	@Test
	public void deveRetornarUmUsuarioLogado() {
		List<PhoneCreationRequest> phones = Arrays.asList(new PhoneCreationRequest(011, "1234-5678"));
		UserCreationRequest user = new UserCreationRequest("Fulano Teste", "fulano@fulano.com.br", "minhaSenha", phones);
		Response responseUserCreated = given().contentType("application/json").body(user).post("/user");
		responseUserCreated.getBody().as(User.class);
		
		AuthenticationRequest authUser = new AuthenticationRequest("fulano@fulano.com.br", "minhaSenha");
		Response response = given().contentType("application/json").body(authUser).post("/login");
		User userLogged = response.getBody().as(User.class);
		
		Assert.assertNotNull(userLogged);
		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	}
	
	@Test
	public void deveRetornarJsonDeErroParaUsuarioQueInformaSenhaErrada() {
		List<PhoneCreationRequest> phones = Arrays.asList(new PhoneCreationRequest(011, "1234-5678"));
		UserCreationRequest user = new UserCreationRequest("Fulano Teste", "fulano2@fulano.com.br", "minhaSenha", phones);
		Response responseUserCreated = given().contentType("application/json").body(user).post("/user");
		responseUserCreated.getBody().as(User.class);
		
		AuthenticationRequest authUser = new AuthenticationRequest("fulano2@fulano.com.br", "minhaSenhaErrada");
		Response response = given().contentType("application/json").body(authUser).post("/login");
		JsonException error = response.getBody().as(JsonException.class);
		
		Assert.assertNotNull(error);
		Assert.assertEquals("Usuário e/ou senha inválidos", error.getMessage());
		Assert.assertEquals(AuthenticationException.class.getName(), error.getException());
		assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode());
	}
	
	@Test
	public void deveRetornarJsonDeErroParaUsuarioQueInformaUsuarioErrada() {
		List<PhoneCreationRequest> phones = Arrays.asList(new PhoneCreationRequest(011, "1234-5678"));
		UserCreationRequest user = new UserCreationRequest("Fulano Teste", "fulano3@fulano.com.br", "minhaSenha", phones);
		Response responseUserCreated = given().contentType("application/json").body(user).post("/user");
		responseUserCreated.getBody().as(User.class);
		
		AuthenticationRequest authUser = new AuthenticationRequest("fulanoErrado@fulano.com.br", "minhaSenha");
		Response response = given().contentType("application/json").body(authUser).post("/login");
		JsonException error = response.getBody().as(JsonException.class);
		
		Assert.assertNotNull(error);
		Assert.assertEquals("Usuário e/ou senha inválidos", error.getMessage());
		Assert.assertEquals(AuthenticationException.class.getName(), error.getException());
		assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode());
	}
	
	@Test
	public void deveRetornaErroDeLoginInvalido() {
		AuthenticationRequest authUser = new AuthenticationRequest("fulano.com.br", "minhaSenha");
		Response response = given().contentType("application/json").body(authUser).post("/login");
		
		assertNull(response.getHeader("x-auth-token"));
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
	}

}
