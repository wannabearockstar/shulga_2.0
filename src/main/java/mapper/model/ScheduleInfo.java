package mapper.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ga.model.config.CurriculumUnit;
import mapper.serializer.json.UnixTimestampDeserializer;

import java.util.Calendar;
import java.util.Date;

public class ScheduleInfo {

	@JsonProperty("group_id")
	private int groupId;

	@JsonProperty("discipline_id")
	private int disciplineId;

	@JsonProperty("professor_id")
	private int professorId;

	@JsonProperty("lesson_type_id")
	private int lessonTypeId;

	@JsonProperty("auditory_id")
	private int auditoryId;

	@JsonProperty("time_id")
	private int timeId;

	@JsonProperty("date")
	@JsonDeserialize(using = UnixTimestampDeserializer.class)
	private Date date;

	public int getGroupId() {
		return groupId;
	}

	public ScheduleInfo setGroupId(int groupId) {
		this.groupId = groupId;
		return this;
	}

	public int getDisciplineId() {
		return disciplineId;
	}

	public ScheduleInfo setDisciplineId(int disciplineId) {
		this.disciplineId = disciplineId;
		return this;
	}

	public int getProfessorId() {
		return professorId;
	}

	public ScheduleInfo setProfessorId(int professorId) {
		this.professorId = professorId;
		return this;
	}

	public int getLessonTypeId() {
		return lessonTypeId;
	}

	public ScheduleInfo setLessonTypeId(int lessonTypeId) {
		this.lessonTypeId = lessonTypeId;
		return this;
	}

	public int getAuditoryId() {
		return auditoryId;
	}

	public ScheduleInfo setAuditoryId(int auditoryId) {
		this.auditoryId = auditoryId;
		return this;
	}

	public int getTimeId() {
		return timeId;
	}

	public ScheduleInfo setTimeId(int timeId) {
		this.timeId = timeId;
		return this;
	}

	public Date getDate() {
		return date;
	}

	public ScheduleInfo setDate(Date date) {
		this.date = date;
		return this;
	}

	public int getWeekDayId() {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_WEEK);
	}

	public CurriculumUnit toCurriculumUnit() {
		return new CurriculumUnit()
			.setGroupId(groupId)
			.setDisciplineId(disciplineId)
			.setProfessorId(professorId)
			.setLessonTypeId(lessonTypeId);
	}
}
