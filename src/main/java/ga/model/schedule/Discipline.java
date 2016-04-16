package ga.model.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Discipline {

	@JsonProperty("id")
	private int id;

	@JsonProperty("alias")
	private String name;

	public Discipline() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Discipline)) return false;

		Discipline that = (Discipline) o;

		return id == that.id;
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public String toString() {
		return name;
	}
}
