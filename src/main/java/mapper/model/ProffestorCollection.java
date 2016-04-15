package mapper.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import ga.model.schedule.Professor;

import java.util.List;

/**
 * Created by wannabe on 06.11.15.
 */
public class ProffestorCollection {

	@JsonProperty("professors")
	private List<Professor> professors;

	public ProffestorCollection() {
	}

	public ProffestorCollection(List<Professor> professors) {
		this.professors = professors;
	}

	public List<Professor> getProfessors() {
		return professors;
	}

	public ProffestorCollection setProfessors(List<Professor> professors) {
		this.professors = professors;
		return this;
	}
}
