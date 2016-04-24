package br.com.concrete.identity.user.domain;

import java.util.Collections;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.joda.time.DateTime;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.auth0.jwt.JWTSigner;

@Entity
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String code;
	private Date expiration = new DateTime().plusMinutes(30).toDate();;
	
	@Transient
	private String value;
	/**
	 * @deprecated: Hibernate Eyes Only
	 */
	@Deprecated
	Token() {}
	
	public Long getId() {
		return id;
	}
	
	private Token(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getExpiration() {
		return expiration.toString();
	}
	
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	
	public static Token generate(String userMail) {
		String token = new JWTSigner(userMail).sign(Collections.emptyMap()).toString();
		String hash = new BCryptPasswordEncoder().encode(token);
		return new Token(hash, token);
	}
	
	@Override
	public String toString() {
		return this.value;
	}
}
