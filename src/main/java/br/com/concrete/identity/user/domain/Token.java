package br.com.concrete.identity.user.domain;

import java.util.Date;
import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.auth0.jwt.JWTSigner;

@Entity
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String code;
	private Date expiration;
	
	@Transient
	private String hashCode;
	/**
	 * @deprecated: Hibernate Eyes Only
	 */
	@Deprecated
	Token() {}
	
	public Long getId() {
		return id;
	}
	
	private Token(String code, Date expiration, String hashCode) {
		this.code = code;
		this.expiration = expiration;
		this.hashCode = hashCode;
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
	
	public static Token generate(String userId) {
		Date expirationDate = new Date();
		String token = new JWTSigner(userId+expirationDate).sign(new HashMap<>()).toString();
		String hash = hashGenerate(token);
		return new Token(token, expirationDate, hash);
	}
	
	private static String hashGenerate(String token) {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		return bcrypt.encode(token);
	}
	
	@Override
	public String toString() {
		return hashCode;
	}
}
