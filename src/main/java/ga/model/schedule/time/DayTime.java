package ga.model.schedule.time;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DayTime {

	@JsonProperty("id")
	private int id;

	@JsonProperty("alias")
	private String name;

	public DayTime() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof DayTime)) return false;

		DayTime dayTime = (DayTime) o;

		return id == dayTime.id;
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public String toString() {
		return Stream.of(name.split(" ")).collect(Collectors.joining("&nbsp;"));
	}
}
