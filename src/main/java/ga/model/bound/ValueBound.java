package ga.model.bound;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ValueBound {

	@JsonProperty("values")
	private List<Integer> values;

	public List<Integer> getValues() {
		return values;
	}

	public void setValues(List<Integer> values) {
		this.values = values;
	}

	public boolean validate(int value) {
		return values.isEmpty() || values.indexOf(value) != -1;
	}
}
