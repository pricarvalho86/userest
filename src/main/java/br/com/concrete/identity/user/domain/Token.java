package br.com.concrete.identity.user.domain;

import java.util.Date;
import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.auth0.jwt.JWTSigner;

@Entity
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String code;
	private Date expiration;
	
	/**
	 * @deprecated: Hibernate Eyes Only
	 */
	@Deprecated
	Token() {}
	
	public Long getId() {
		return id;
	}
	
	private Token(String code, Date expiration) {
		this.code = code;
		this.expiration = expiration;
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
	
	public static Token generate(String password) {
		Date expirationDate = new Date();
		String token = new JWTSigner(password+expirationDate).sign(new HashMap<>()).toString();
		return new Token(token, expirationDate);
	}
	
	@Override
	public String toString() {
		return this.code;
	}
}
