package br.com.concrete.identity.user.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	
	public Date getExpiration() {
		return expiration;
	}
	
	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
	
	public static Token generate() {
		return new Token("blablablabalblabla", new Date());
	}
	
	@Override
	public String toString() {
		return this.code;
	}
}
