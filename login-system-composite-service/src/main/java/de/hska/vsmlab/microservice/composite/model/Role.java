package de.hska.vsmlab.microservice.composite.model;


public class Role {

	private long id;
	private String type;
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
