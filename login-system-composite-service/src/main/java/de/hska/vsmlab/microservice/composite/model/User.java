package de.hska.vsmlab.microservice.composite.model;


public class User {

	private long id;
	private String email;
	private String firstname;
	private String lastname;
	private String password;
    private long roleId;
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

	public void setEmail(String username) {
		this.email = username;
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
