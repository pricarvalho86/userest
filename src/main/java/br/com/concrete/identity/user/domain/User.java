package br.com.concrete.identity.user.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import org.joda.time.DateTime;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.auth0.jwt.JWTSigner;

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
	
	@Embedded
	private Token token;

	@Transient
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
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
		this.token = Token.generate(this);
	}

	public boolean isPasswordValid(String password) {
		return this.password.isValid(password);
	}

	public boolean tokenExpirated() {
		return this.token.isExpirated(this.lastLogin);
	}

	public void generateToken() {
		this.token = Token.generate(this);
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
	
	public String getCreated() {
		return dateFormat.format(created);
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getModified() {
		return dateFormat.format(modified);
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getLastLogin() {
		return dateFormat.format(lastLogin);
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	public String getToken() {
		return this.token.toString();
	}
	
	public void setToken(Token token) {
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
			String hashPassword = bcrypt.encode(password);
			return new Password(hashPassword);
		}
		
		public boolean isValid(String password) {
			return bcrypt.matches(password, this.password);
		}

		@Override
		public String toString() {
			return password.toString();
		}
		
	}
	
	@Embeddable
	public static class Token {

		private String token;
		
		/**
		 * @deprecated: Hibernate Eyes Only
		 */
		@Deprecated
		Token() {}
		
		private Token(String token) {
			this.token = token;
		}

		public static Token generate(User user) {
			String token = new JWTSigner(user.email+user.lastLogin).sign(new HashMap<>()).toString();
			return new Token(token);
		}

		public boolean isExpirated(Date lastLogin) {
			DateTime currenteDate = new DateTime();
			DateTime expirationDate = new DateTime(lastLogin).plusMinutes(30);
			return  currenteDate.isAfter(expirationDate);
		}
		
		@Override
		public String toString() {
			return this.token;
		}
	}
	
}
