package br.com.concrete.identity.user.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.concrete.identity.auth.AuthenticationRequest;

@Entity
public class User {
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	private String name;
	private String email;
	
	@Embedded
	private Password password;
	
	private Date created;
	private Date modified;
	private Date lastLogin;
	
	@OneToMany(cascade=CascadeType.PERSIST)
	private List<Phone> phones;
	
	@Transient
	private String token;

	/**
	 * @deprecated: Hibernate Eyes Only
	 */
	@Deprecated
	User() {}
	
	public User(String name, String email, String password, List<Phone> phones) {
		this.name = name;
		this.email = email.toLowerCase();
		this.password = Password.generate(password);
		this.phones = phones;
		Date currentDate = new Date();
		this.created = currentDate;
		this.modified = currentDate;
		this.lastLogin = currentDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password.toString();
	}
	
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}
	
	@Embeddable
	public static class Password {
		
		private String password;

		private static final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		
		/**
		 * @deprecated: Hibernate Eyes Only
		 */
		@Deprecated
		Password() {}
		
		private Password(String password) {
			this.password = password;
		}

		public static Password generate(String password) {
			String hashPassword = hashGenerate(password);
			return new Password(hashPassword);
		}
		
		private static String hashGenerate(String password) {
			return bcrypt.encode(password);
		}
		
		@Override
		public String toString() {
			return password.toString();
		}

		public boolean isValid(String password) {
			return bcrypt.matches(password, this.password);
		}
		
	}

	public boolean isValidPassword(AuthenticationRequest userAuth) {
		return this.password.isValid(userAuth.getPassword());
	}
	
}
