package br.com.concrete.identity.user.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Phone {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String ddd;
	private String number;

	/**
	 * @deprecated: Hibernate Eyes Only
	 */
	@Deprecated
	Phone() {}
	
	public Phone(String ddd, String number) {
		this.ddd = ddd;
		this.number = number;
	}

	public String getDdd() {
		return ddd;
	}
	
	public void setDdd(String ddd) {
		this.ddd = ddd;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
}
