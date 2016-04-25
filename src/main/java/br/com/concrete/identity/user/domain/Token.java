package br.com.concrete.identity.user.domain;

import java.util.HashMap;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.joda.time.DateTime;

import com.auth0.jwt.JWTSigner;

@Entity
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String code;
	
	@OneToOne(cascade=CascadeType.ALL)
	private User user;
	
	/**
	 * @deprecated: Hibernate Eyes Only
	 */
	@Deprecated
	Token() {}
	
	public Long getId() {
		return id;
	}
	
	private Token(String code, User user) {
		this.code = code;
		this.user = user;
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
	
	public static Token generate(User user) {
		String token = new JWTSigner(user.getPassword()).sign(new HashMap<>()).toString();
		return new Token(token, user);
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return this.code;
	}

	public boolean isExpirated() {
		DateTime currenteDate = new DateTime();
		DateTime expirationDate = new DateTime(user.getLastLogin()).plusMinutes(30);
		return  currenteDate.isAfter(expirationDate);
	}
}
