package mapper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ga.model.bound.BoundCollection;
import ga.model.comparator.*;
import ga.model.config.CurriculumUnit;
import ga.model.config.ScheduleConfig;
import ga.model.schedule.*;
import ga.model.schedule.time.DayTime;
import ga.model.schedule.time.TimeMark;
import ga.model.schedule.time.WeekDay;

import java.util.*;
import java.util.stream.Collectors;

public class GroupInfo {

	@JsonProperty("auditories")
	private List<Auditory> auditories;

	@JsonProperty("disciplines")
	private List<Discipline> disciplines;

	@JsonProperty("lesson_types")
	private List<LessonType> lessonTypes;

	@JsonProperty("professors")
	private List<Professor> professors;

	@JsonProperty("times")
	private List<DayTime> times;

	@JsonProperty("schedule")
	private List<ScheduleInfo> schedule;

	@JsonIgnore
	private Group group;

	/**
	 * Convert GroupInfo object list to schedule for many groups
	 */
	public static Schedule toSchedule(List<GroupInfo> data) {
		ScheduleConfig config = toScheduleConfig(data);

		List<ScheduleInfo> schedule = data.stream()
			.map(GroupInfo::getSchedule)
			.flatMap(Collection::stream)
			.collect(Collectors.toList());

		Auditory[] auditories = schedule.stream()
			.map(x -> findAuditory(config, x))
			.toArray(Auditory[]::new);

		TimeMark[] marks = schedule.stream()
			.map(x -> new TimeMark(
				findWeekDay(config, x),
				findDayTime(config, x)
			))
			.toArray(TimeMark[]::new);

		return new Schedule(config.getCurriculum().size(), config)
			.setAuditories(auditories)
			.setTimeMarks(marks);
	}

	/**
	 * Convert GroupInfo object list to schedule config
	 */
	public static ScheduleConfig toScheduleConfig(List<GroupInfo> data) {
		List<Group> groups = data.stream()
			.map(GroupInfo::getGroup)
			.collect(Collectors.toList());

		return new ScheduleConfig()
			.setGroups(groups)
			.setAuditories(aggregateAuditories(data))
			.setDisciplines(aggregateDisciplines(data))
			.setProfessors(aggregateProfessors(data))
			.setTimes(aggregateTimes(data))
			.setWeekDays(aggregateWeekDays(data))
			.setLessonTypes(aggregateLessonTypes(data))
			.setCurriculum(aggregateCurriculum(data))
			.setBounds(BoundCollection.random());
	}

	/**
	 * Aggregate auditories from all groups data
	 */
	private static List<Auditory> aggregateAuditories(List<GroupInfo> data) {
		return data.stream()
			.map(GroupInfo::getAuditories)
			.flatMap(Collection::stream)
			.distinct()
			.sorted(new AuditoryComparator())
			.collect(Collectors.toList());
	}

	/**
	 * Aggregate disciplines from all groups data
	 */
	private static List<Discipline> aggregateDisciplines(List<GroupInfo> data) {
		return data.stream()
			.map(GroupInfo::getDisciplines)
			.flatMap(Collection::stream)
			.distinct()
			.sorted(new DisciplineComparator())
			.collect(Collectors.toList());
	}

	/**
	 * Aggregate professors from all groups data
	 */
	private static List<Professor> aggregateProfessors(List<GroupInfo> data) {
		return data.stream()
			.map(GroupInfo::getProfessors)
			.flatMap(Collection::stream)
			.distinct()
			.sorted(new ProfessorComparator())
			.collect(Collectors.toList());
	}

	/**
	 * Aggregate times from all groups data
	 */
	private static List<DayTime> aggregateTimes(List<GroupInfo> data) {
		return data.stream()
			.map(GroupInfo::getTimes)
			.flatMap(Collection::stream)
			.distinct()
			.sorted(new DayTimeComparator())
			.collect(Collectors.toList());
	}

	/**
	 * Aggregate week days from all groups data
	 */
	private static List<WeekDay> aggregateWeekDays(List<GroupInfo> data) {
		return Arrays.asList(
			WeekDay.MONDAY,
			WeekDay.TUESDAY,
			WeekDay.WEDNESDAY,
			WeekDay.THURSDAY,
			WeekDay.FRIDAY,
			WeekDay.SATURDAY
		);
	}

	/**
	 * Aggregate lesson types from all groups data
	 */
	private static List<LessonType> aggregateLessonTypes(List<GroupInfo> data) {
		return data.stream()
			.map(GroupInfo::getLessonTypes)
			.flatMap(Collection::stream)
			.distinct()
			.sorted(new LessonTypeComparator())
			.collect(Collectors.toList());
	}

	/**
	 * Aggregate curriculum from all groups data
	 */
	private static List<CurriculumUnit> aggregateCurriculum(List<GroupInfo> data) {
		return data.stream()
			.map(GroupInfo::getSchedule)
			.flatMap(Collection::stream)
			.map(ScheduleInfo::toCurriculumUnit)
			.sorted(new CurriculumComparator())
			.collect(Collectors.toList());
	}

	private static Auditory findAuditory(ScheduleConfig config, ScheduleInfo info) {
		return config.getAuditories()
			.stream()
			.filter(x -> x.getId() == info.getAuditoryId())
			.findFirst()
			.orElse(null);
	}

	private static DayTime findDayTime(ScheduleConfig config, ScheduleInfo info) {
		return config.getTimes()
			.stream()
			.filter(x -> x.getId() == info.getTimeId())
			.findFirst()
			.orElse(null);
	}

	private static WeekDay findWeekDay(ScheduleConfig config, ScheduleInfo info) {
		return config.getWeekDays()
			.stream()
			.filter(x -> x.getDay() == info.getWeekDayId() - 1)
			.findFirst()
			.orElse(null);
	}

	public List<Auditory> getAuditories() {
		return auditories;
	}

	public GroupInfo setAuditories(List<Auditory> auditories) {
		this.auditories = auditories;
		return this;
	}

	public List<Discipline> getDisciplines() {
		return disciplines;
	}

	public GroupInfo setDisciplines(List<Discipline> disciplines) {
		this.disciplines = disciplines;
		return this;
	}

	public List<LessonType> getLessonTypes() {
		return lessonTypes;
	}

	public GroupInfo setLessonTypes(List<LessonType> lessonTypes) {
		this.lessonTypes = lessonTypes;
		return this;
	}

	public List<Professor> getProfessors() {
		return professors;
	}

	public GroupInfo setProfessors(List<Professor> professors) {
		this.professors = professors;
		return this;
	}

	public List<DayTime> getTimes() {
		return times;
	}

	public GroupInfo setTimes(List<DayTime> times) {
		this.times = times;
		return this;
	}

	public List<ScheduleInfo> getSchedule() {
		return schedule;
	}

	public GroupInfo setSchedule(List<ScheduleInfo> schedule) {
		this.schedule = schedule;
		return this;
	}

	public Group getGroup() {
		return group;
	}

	public GroupInfo setGroup(Group group) {
		this.group = group;
		return this;
	}

	/**
	 * Convert GroupInfo object to schedule for a one group
	 */
	public Schedule toSchedule() {
		List<GroupInfo> data = Collections.singletonList(this);
		return toSchedule(data);
	}

	/**
	 * Convert GroupInfo object to schedule config
	 */
	public ScheduleConfig toScheduleConfig() {
		List<GroupInfo> data = Collections.singletonList(this);
		return toScheduleConfig(data);
	}

	/**
	 * Filter GroupInfo data by date between begin and end date
	 */
	public GroupInfo filter(Date begin, Date end) {
		schedule = schedule.stream()
			.filter(x -> x.getDate().after(begin) && x.getDate().before(end))
			.collect(Collectors.toList());
		return this;
	}
}
