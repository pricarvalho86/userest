package br.com.concrete;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jayway.restassured.RestAssured;

import br.com.concrete.conf.AppConfig;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes={Application.class, AppConfig.class})
@IntegrationTest("8080:0")
public abstract class RestAssuredConfigTest {
	
	@Before
	public void before() {
		RestAssured.port = 8080;
	}

}
