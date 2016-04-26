package br.com.concrete.identity.user;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.jayway.restassured.response.Response;

import br.com.concrete.RestAssuredConfigTest;
import br.com.concrete.identity.user.domain.Phone;
import br.com.concrete.identity.user.domain.User;
import br.com.concrete.json.model.JsonException;

public class CreateUserTest extends RestAssuredConfigTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void deveRetornarErroAoTentarCadastrarUmUsuarioEnviandoContentTypeDiferenteDeApplicationJson() {
		List<Phone> phones = Arrays.asList(new Phone(011, "1234-5678"));
		User user = new User("Fulano Teste", "teste@teste.com.br", "fulano", phones);
		given().contentType("text/plain").body(user).post("/user");
	}
	
	@Test
	public void deveCadastrarUmUsuarioERetornarJsonComTokenGerado() {
		List<Phone> phones = Arrays.asList(new Phone(011, "1234-5678"));
		User user = new User("Fulano Teste", "teste@teste.com.br", "fulano", phones);
		Response response = given().contentType("application/json").body(user).post("/user");
		User userCreated = response.getBody().as(User.class);
		
		assertNotNull(userCreated);
		assertNotNull(userCreated.getId());
		assertNotNull(userCreated.getToken());
		assertNotNull(response.getHeader("x-auth-token"));
		assertEquals(userCreated.getToken(), response.getHeader("x-auth-token"));
		assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
	}
	
	@Test
	public void deveCadastrarUmUsuarioJaExistenteERetornarStatus401() {
		List<Phone> phones = Arrays.asList(new Phone(011, "1234-5678"));
		User user = new User("Fulano Teste", "jacadastrado@teste.com.br", "fulano", phones);
		given().contentType("application/json").body(user).post("/user");
		Response response = given().contentType("application/json").body(user).post("/user");
		JsonException error = response.getBody().as(JsonException.class);
		
		assertEquals("E-mail já existente", error.getMessage());
		assertEquals(UserAlreadyCreatedException.class.getName(), error.getException());
		assertNull(response.getHeader("x-auth-token"));
		assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCode());
	}
	
	@Test
	public void deveRetornaErroDeEmailInvalido() {
		List<Phone> phones = Arrays.asList(new Phone(011, "1234-5678"));
		User user = new User("Fulano Teste", "teste.com.br", "fulano", phones);
		Response response = given().contentType("application/json").body(user).post("/user");
		
		assertNull(response.getHeader("x-auth-token"));
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
	}

}
