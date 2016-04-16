package ga.model.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Auditory {

	@JsonProperty("id")
	private int id;

	@JsonProperty("alias")
	private String name;

	@JsonProperty("lat")
	private double lat;

	@JsonProperty("lon")
	private double lon;

	@JsonProperty("level")
	private int level;

	@JsonProperty("building_id")
	private int buildingId;

	public Auditory() {
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Auditory setName(String name) {
		this.name = name;
		return this;
	}

	public double getLat() {
		return lat;
	}

	public Auditory setLat(double lat) {
		this.lat = lat;
		return this;
	}

	public double getLon() {
		return lon;
	}

	public Auditory setLon(double lon) {
		this.lon = lon;
		return this;
	}

	public int getLevel() {
		return level;
	}

	public Auditory setLevel(int level) {
		this.level = level;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Auditory)) return false;

		Auditory auditory = (Auditory) o;

		if (name.equals(auditory.name)) return true;
		return (id == auditory.id);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return name;
	}
}
