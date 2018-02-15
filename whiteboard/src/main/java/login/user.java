package login;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name = "Person")

public class User {
	
	@NotEmpty
		@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String name;
	
	private String email;
	public String username;
	public String password;
	
	public user() {

	}
	
			public Integer getId() {
			return id;
		}
		
		public void setId(Integer id) {
			this.id = id;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getEmail( ) {
			return email;
		}
		
		public void setEmail(String email) {
			this.email = email;
		}
	
	
	public user(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}