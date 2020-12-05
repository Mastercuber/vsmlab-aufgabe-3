package de.hska.vsmlab.microservice.user.perstistence.model;


import javax.persistence.*;

/**
 * This class contains details about roles.
 */
@Entity
@Table
public class Role implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private long id;

	@Column
	private String type;


	@Column
	private int level;

	public Role() {
	}

	public Role(String type, int level) {
		this.type = type;
		this.level = level;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
