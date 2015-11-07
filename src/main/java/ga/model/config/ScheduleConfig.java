package ga.model.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import ga.model.bound.BoundCollection;
import ga.model.schedule.*;
import ga.model.schedule.time.DayTime;
import ga.model.schedule.time.WeekDay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleConfig {
    public static Map<Integer, Auditory> allAuditories = new HashMap<>();
    public static Map<Integer, Group> allGroups = new HashMap<>();
    public static Map<Integer, Professor> allProfessors = new HashMap<>();
    public static Map<Integer, LessonType> allLessonTypes = new HashMap<>();
    public static Map<Integer, DayTime> allDayTimes = new HashMap<>();
    public static Map<Integer, Discipline> allDisciplines = new HashMap<>();

    @JsonProperty("auditories")
    private List<Auditory> auditories;

    @JsonProperty("disciplines")
    private List<Discipline> disciplines;

    @JsonProperty("lesson_types")
    private List<LessonType> lessonTypes;

    @JsonProperty("professors")
    private List<Professor> professors;

    @JsonProperty("groups")
    private List<Group> groups;

    @JsonProperty("times")
    private List<DayTime> times;

    @JsonProperty("week_days")
    private List<WeekDay> weekDays;

    @JsonProperty("curriculum")
    private List<CurriculumUnit> curriculum;

    @JsonProperty("bounds")
    private BoundCollection bounds;

    public List<Auditory> getAuditories() {
        return auditories;
    }

    public ScheduleConfig setAuditories(List<Auditory> auditories) {
        this.auditories = auditories;
        return this;
    }

    public List<Discipline> getDisciplines() {
        return disciplines;
    }

    public ScheduleConfig setDisciplines(List<Discipline> disciplines) {
        this.disciplines = disciplines;
        return this;
    }

    public List<LessonType> getLessonTypes() {
        return lessonTypes;
    }

    public ScheduleConfig setLessonTypes(List<LessonType> lessonTypes) {
        this.lessonTypes = lessonTypes;
        return this;
    }

    public List<Professor> getProfessors() {
        return professors;
    }

    public ScheduleConfig setProfessors(List<Professor> professors) {
        this.professors = professors;
        return this;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public ScheduleConfig setGroups(List<Group> groups) {
        this.groups = groups;
        return this;
    }

    public List<DayTime> getTimes() {
        return times;
    }

    public ScheduleConfig setTimes(List<DayTime> times) {
        this.times = times;
        return this;
    }

    public List<WeekDay> getWeekDays() {
        return weekDays;
    }

    public ScheduleConfig setWeekDays(List<WeekDay> weekDays) {
        this.weekDays = weekDays;
        return this;
    }

    public List<CurriculumUnit> getCurriculum() {
        return curriculum;
    }

    public ScheduleConfig setCurriculum(List<CurriculumUnit> curriculum) {
        this.curriculum = curriculum;
        return this;
    }

    public BoundCollection getBounds() {
        return bounds;
    }

    public ScheduleConfig setBounds(BoundCollection bounds) {
        this.bounds = bounds;
        return this;
    }
}
