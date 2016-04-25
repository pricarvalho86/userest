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
	
	private Integer ddd;
	private String number;

	/**
	 * @deprecated: Hibernate Eyes Only
	 */
	@Deprecated
	Phone() {}
	
	public Phone(Integer ddd, String number) {
		this.ddd = ddd;
		this.number = number;
	}

	public Integer getDdd() {
		return ddd;
	}
	
	public void setDdd(Integer ddd) {
		this.ddd = ddd;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
}
