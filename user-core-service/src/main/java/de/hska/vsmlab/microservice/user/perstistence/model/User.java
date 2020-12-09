package de.hska.vsmlab.microservice.user.perstistence.model;


import javax.persistence.*;

/**
 * This class contains the users of the webshop.
 */
@Entity
@Table
public class User implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private long id;


	@Column(name = "email", unique = true, nullable = false)
	private String email;


	@Column(name = "name", nullable = false)
	private String firstname;


	@Column(name = "lastname", nullable = false)
	private String lastname;


	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "role")
    private long roleId;

	@Column
    private boolean active;

	public User() {
	}

	public User(String email, String firstname, String lastname,
                String password, long roleId) {
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.roleId = roleId;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
