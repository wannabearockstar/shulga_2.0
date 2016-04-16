package ga.model.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurriculumUnit {

	@JsonProperty("group_id")
	private int groupId;

	@JsonProperty("discipline_id")
	private int disciplineId;

	@JsonProperty("professor_id")
	private int professorId;

	@JsonProperty("lesson_type_id")
	private int lessonTypeId;

	public CurriculumUnit() {
	}

	public int getGroupId() {
		return groupId;
	}

	public CurriculumUnit setGroupId(int groupId) {
		this.groupId = groupId;
		return this;
	}

	public int getDisciplineId() {
		return disciplineId;
	}

	public CurriculumUnit setDisciplineId(int disciplineId) {
		this.disciplineId = disciplineId;
		return this;
	}

	public int getProfessorId() {
		return professorId;
	}

	public CurriculumUnit setProfessorId(int professorId) {
		this.professorId = professorId;
		return this;
	}

	public int getLessonTypeId() {
		return lessonTypeId;
	}

	public CurriculumUnit setLessonTypeId(int lessonTypeId) {
		this.lessonTypeId = lessonTypeId;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CurriculumUnit)) return false;

		CurriculumUnit that = (CurriculumUnit) o;

		if (groupId != that.groupId) return false;
		if (disciplineId != that.disciplineId) return false;
		if (professorId != that.professorId) return false;
		return lessonTypeId == that.lessonTypeId;
	}

	@Override
	public int hashCode() {
		int result = groupId;
		result = 31 * result + disciplineId;
		result = 31 * result + professorId;
		result = 31 * result + lessonTypeId;
		return result;
	}
}
