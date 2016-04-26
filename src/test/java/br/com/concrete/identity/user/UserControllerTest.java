package br.com.concrete.identity.user;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import br.com.concrete.RestAssuredConfigTest;
import br.com.concrete.identity.auth.UnauthorizedException;
import br.com.concrete.identity.user.domain.Address;
import br.com.concrete.identity.user.domain.User;
import br.com.concrete.identity.user.dto.AddressRequest;
import br.com.concrete.identity.user.dto.PhoneCreationRequest;
import br.com.concrete.identity.user.dto.UserCreationRequest;
import br.com.concrete.json.model.JsonException;

public class UserControllerTest extends RestAssuredConfigTest  {
	
		@Test(expected=IllegalArgumentException.class)
		public void deveRetornarErroAoTentarCadastrarUmUsuarioEnviandoContentTypeDiferenteDeApplicationJson() {
			List<PhoneCreationRequest> phones = Arrays.asList(new PhoneCreationRequest(011, "1234-5678"));
			UserCreationRequest user = new UserCreationRequest("Fulano Teste", "jacadastrado1@teste.com.br", "fulano", phones);
			given().contentType("text/plain").body(user).post("/user");
		}
		
		@Test
		public void deveCadastrarUmUsuarioERetornarJsonComTokenGerado() {
			List<PhoneCreationRequest> phones = Arrays.asList(new PhoneCreationRequest(011, "1234-5678"));
			UserCreationRequest user = new UserCreationRequest("Fulano Teste", "jacadastrado2@teste.com.br", "fulano", phones);
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
			List<PhoneCreationRequest> phones = Arrays.asList(new PhoneCreationRequest(011, "1234-5678"));
			UserCreationRequest user = new UserCreationRequest("Fulano Teste", "jacadastrado3@teste.com.br", "fulano", phones);
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
			List<PhoneCreationRequest> phones = Arrays.asList(new PhoneCreationRequest(011, "1234-5678"));
			UserCreationRequest user = new UserCreationRequest("Fulano Teste", "teste.com.br", "fulano", phones);
			Response response = given().contentType("application/json").body(user).post("/user");
			
			assertNull(response.getHeader("x-auth-token"));
			assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
		}

		@Test
		public void deveCadastrarUmEnderecoParaUmUsuarioValido() throws InterruptedException {
			List<PhoneCreationRequest> phones = Arrays.asList(new PhoneCreationRequest(011, "1234-5678"));
			UserCreationRequest user = new UserCreationRequest("Fulano Teste", "testeAddress1@teste.com.br", "fulano", phones);
			Response userResponse = given().contentType("application/json").body(user).post("/user");
			User userCreated = userResponse.getBody().as(User.class);
			
			AddressRequest addressRequest = new AddressRequest("Rua do Fulano", "123", "Bairro", "Fulano", "FF", "00000-000");
			
			Response response = given()
					.contentType("application/json")
					.header("x-auth-token", userCreated.getToken())
					.body(addressRequest).post("/user/address");
			Address address = response.getBody().as(Address.class);
			
			assertNotNull(address);
			assertNotNull(address.getId());
			assertNotNull(address.getUser());
			assertNotNull(response.getHeader("x-auth-token"));
			assertEquals(address.getUser().getToken(), response.getHeader("x-auth-token"));
			assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
		}
		
		@Test
		public void deveRetornarUnauthorizedAoTentarCadastraEnderecoSemTokenValido() {
			AddressRequest addressRequest = new AddressRequest("Rua do Fulano", "123", "Bairro", "Fulano", "FF", "00000-000");
			
			Response response = given()
					.contentType("application/json")
					.header("x-auth-token", "token")
					.body(addressRequest).post("/user/address");
			JsonException error = response.getBody().as(JsonException.class);
			
			assertNotNull(error);
			assertEquals("Não autorizado", error.getMessage());
			assertEquals(UnauthorizedException.class.getName(), error.getException());
			assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode());
		}
		
		@Test
		public void deveRetornarBadRequestAoCadastrarUmEnderecoComZipcodeInvalido() {
			List<PhoneCreationRequest> phones = Arrays.asList(new PhoneCreationRequest(011, "1234-5678"));
			UserCreationRequest user = new UserCreationRequest("Fulano Teste", "testeAdress2@teste.com.br", "fulano", phones);
			Response userResponse = given().contentType("application/json").body(user).post("/user");
			User userCreated = userResponse.getBody().as(User.class);
			
			AddressRequest addressRequest = new AddressRequest("Rua do Fulano", "123", "Bairro", "Fulano", "FF", "000-000");
			
			Response response = given()
					.contentType("application/json")
					.header("x-auth-token", userCreated.getToken())
					.body(addressRequest).post("/user/address");
			JsonPath jsonPath = response.getBody().jsonPath();
			assertNotNull(jsonPath);
			assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
		}
		
		@Test
		public void deveRetornarBadRequestAoCadastrarUmEnderecoComStateInvalido() {
			List<PhoneCreationRequest> phones = Arrays.asList(new PhoneCreationRequest(011, "1234-5678"));
			UserCreationRequest user = new UserCreationRequest("Fulano Teste", "testeAdress3@teste.com.br", "fulano", phones);
			Response userResponse = given().contentType("application/json").body(user).post("/user");
			User userCreated = userResponse.getBody().as(User.class);
			
			AddressRequest addressRequest = new AddressRequest("Rua do Fulano", "123", "Bairro", "Fulano", "INVALIDO", "00000-000");
			
			Response response = given()
					.contentType("application/json")
					.header("x-auth-token", userCreated.getToken())
					.body(addressRequest).post("/user/address");
			JsonPath jsonPath = response.getBody().jsonPath();
			assertNotNull(jsonPath);
			assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
		}
		
		@Test
		public void deveRetornarBadRequestAoCadastrarUmEnderecoInvalido() {
			List<PhoneCreationRequest> phones = Arrays.asList(new PhoneCreationRequest(011, "1234-5678"));
			UserCreationRequest user = new UserCreationRequest("Fulano Teste", "testeAdress4@teste.com.br", "fulano", phones);
			Response userResponse = given().contentType("application/json").body(user).post("/user");
			User userCreated = userResponse.getBody().as(User.class);
			
			Response response = given()
					.contentType("application/json")
					.header("x-auth-token", userCreated.getToken())
					.body(new AddressRequest()).post("/user/address");
			JsonPath jsonPath = response.getBody().jsonPath();
			assertNotNull(jsonPath);
			assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
		}

}
