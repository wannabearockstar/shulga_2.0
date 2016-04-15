package ga.core.impl;

import ga.core.FitnessHandler;
import ga.model.bound.TimeBound;
import ga.model.bound.ValueBound;
import ga.model.comparator.TimeMarkComparator;
import ga.model.config.CurriculumUnit;
import ga.model.config.ScheduleConfig;
import ga.model.schedule.Auditory;
import ga.model.schedule.Group;
import ga.model.schedule.Professor;
import ga.model.schedule.Schedule;
import ga.model.schedule.time.DayTime;
import ga.model.schedule.time.TimeMark;
import ga.model.schedule.time.WeekDay;
import ga.model.service.AuditoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class FitnessHandlerImpl implements FitnessHandler {
	private final static int FIRST_DAY_TIME = 1;
	// common penalties
	public static int COLLISION_PENALTY = 350;
	public static int AUDITORIA_TIMES_PENALTY = 130;
	public static int DISCIPLINE_TIMES_PENALTY = 130;
	public static int DISCIPLINE_AUDITORIES_PENALTY = 130;
	// penalties for groups
	public static int ONE_WINDOW_GROUPS_PENALTY = 10;
	public static int TWO_WINDOWS_GROUPS_PENALTY = 20;
	public static int EMPTY_START_TIME_PENALTY = 15;
	// penalties for professors
	public static int WEEKEND_TIME_PENALTY = 4;
	public static int TWO_WINDOWS_PROFESSORS_PENALTY = 10;
	// distance multiplier
	public static double ONE_TIME_DISTANCE_MULTIPLIER = 2;
	public static double TWO_TIMES_DISTANCE_MULTIPLIER = 1;
	@Autowired
	private AuditoryService auditoryService;

	@Override
	public double computeFitness(Schedule schedule) {
		int res = 0;

		res += checkCollisions(schedule);
		res += checkGroupsWindows(schedule);
		res += checkProfessorsWindows(schedule);
		res += checkGroupsDistance(schedule);
		res += checkProfessorsDistance(schedule);
		res += checkBounds(schedule);

		return res;
	}

	@Override
	public boolean hasCollisions(Schedule schedule) {
		return checkCollisions(schedule) != 0;
	}

	private int checkCollisions(Schedule schedule) {
		ScheduleConfig config = schedule.getConfig();
		int res = 0;

		for (WeekDay day : config.getWeekDays()) {
			for (DayTime time : config.getTimes()) {

				TimeMark mark = new TimeMark(day, time);

				List<Integer> positions = IntStream.range(0, schedule.getTimeMarks().length)
					.filter(x -> Objects.equals(schedule.getTimeMarks()[x], mark))
					.boxed()
					.collect(Collectors.toList());

				if (positions.isEmpty())
					continue;

				List<Integer> groups = new ArrayList<>();
				List<Integer> professors = new ArrayList<>();
				List<Auditory> auditories = new ArrayList<>();

				for (int position : positions) {
					CurriculumUnit unit = config.getCurriculum().get(position);
					Auditory auditory = schedule.getAuditories()[position];

					// check group collisions
					res += checkCollision(groups, unit.getGroupId());

					// check professor collisions
					res += checkCollision(professors, unit.getProfessorId());

					// check auditory collisions
					res += checkCollision(auditories, auditory);

					// todo: implement checking combined lessons
				}

			}
		}

		return res;
	}

	private <T> int checkCollision(List<T> collisions, T entity) {
		if (collisions.contains(entity)) {
			collisions.add(entity);
			return (collisions.size() - 1) * COLLISION_PENALTY;
		}
		collisions.add(entity);
		return 0;
	}

	private int checkGroupsWindows(Schedule schedule) {
		return schedule.getConfig().getGroups()
			.stream()
			.mapToInt(x -> checkGroupWindows(schedule, x))
			.sum();
	}

	private int checkGroupWindows(Schedule schedule, Group group) {
		int res = 0;

		// time marks for exact group
		TimeMark[] groupTimes = IntStream.range(0, schedule.getTimeMarks().length)
			.filter(x -> schedule.getConfig().getCurriculum().get(x).getGroupId() == group.getId())
			.boxed()
			.map(x -> schedule.getTimeMarks()[x])
			.sorted(new TimeMarkComparator())
			.toArray(TimeMark[]::new);

		TimeMark last = null;

		for (TimeMark mark : groupTimes) {

			if (last == null || last.getWeekDay() != mark.getWeekDay()) {
				// first lesson of the day
				int lessonOrder = mark.getDayTime().getId() - FIRST_DAY_TIME;
				res += mark.getDayTime().getId() == FIRST_DAY_TIME ? 0 : lessonOrder * EMPTY_START_TIME_PENALTY;
				last = mark;
				continue;
			}

			int diff = mark.getDayTime().getId() - last.getDayTime().getId();
			if (diff > 1) {
				res += ONE_WINDOW_GROUPS_PENALTY;
			} else if (diff > 2) {
				res += diff * TWO_WINDOWS_GROUPS_PENALTY;
			}

			last = mark;
		}
		return res;
	}

	private int checkProfessorsWindows(Schedule schedule) {
		return schedule.getConfig().getProfessors()
			.stream()
			.mapToInt(x -> checkProfessorWindows(schedule, x))
			.sum();
	}

	private int checkProfessorWindows(Schedule schedule, Professor professor) {
		int res = 0;

		// time marks for exact professor
		TimeMark[] professorTimes = IntStream.range(0, schedule.getTimeMarks().length)
			.filter(x -> schedule.getConfig().getCurriculum().get(x).getProfessorId() == professor.getId())
			.boxed()
			.map(x -> schedule.getTimeMarks()[x])
			.sorted(new TimeMarkComparator())
			.toArray(TimeMark[]::new);

		TimeMark last = null;

		for (TimeMark mark : professorTimes) {
			if (mark.getWeekDay().isWeekend()) {
				res += WEEKEND_TIME_PENALTY;
			}

			if (last == null || last.getWeekDay() != mark.getWeekDay()) {
				// first lesson of the day
				last = mark;
				continue;
			}

			int diff = mark.getDayTime().getId() - last.getDayTime().getId();
			if (diff > 2) {
				res += diff * TWO_WINDOWS_PROFESSORS_PENALTY;
			}

			last = mark;
		}
		return res;
	}

	private int checkGroupsDistance(Schedule schedule) {
		return schedule.getConfig().getGroups()
			.stream()
			.mapToInt(x -> checkGroupDistance(schedule, x))
			.sum();
	}

	private int checkProfessorsDistance(Schedule schedule) {
		return schedule.getConfig().getProfessors()
			.stream()
			.mapToInt(x -> checkProfessorsDistance(schedule, x))
			.sum();
	}

	private int checkProfessorsDistance(Schedule schedule, Professor proffestor) {
		int res = 0;

		// positions sorted by time marks for exact group
		Integer[] positions = IntStream.range(0, schedule.getTimeMarks().length)
			.filter(x -> schedule.getConfig().getCurriculum().get(x).getProfessorId() == proffestor.getId())
			.boxed()
			.sorted((fst, snd) -> compare(fst, snd, schedule))
			.toArray(Integer[]::new);

		TimeMark lastMark = null;
		Auditory lastAuditory = null;

		for (Integer position : positions) {
			TimeMark mark = schedule.getTimeMarks()[position];
			Auditory auditory = schedule.getAuditories()[position];

			if (lastMark == null || lastMark.getWeekDay() != mark.getWeekDay()) {
				// first lesson of the day
				lastMark = mark;
				lastAuditory = auditory;
				continue;
			}

			int diff = mark.getDayTime().getId() - lastMark.getDayTime().getId();
			if (diff == 1) {
				res += checkDistance(auditory, lastAuditory, ONE_TIME_DISTANCE_MULTIPLIER);
			} else if (diff >= 2) {
				res += checkDistance(auditory, lastAuditory, diff * TWO_TIMES_DISTANCE_MULTIPLIER);
			}

			lastMark = mark;
			lastAuditory = auditory;
		}
		return res;
	}


	private int checkGroupDistance(Schedule schedule, Group group) {
		int res = 0;

		// positions sorted by time marks for exact group
		Integer[] positions = IntStream.range(0, schedule.getTimeMarks().length)
			.filter(x -> schedule.getConfig().getCurriculum().get(x).getGroupId() == group.getId())
			.boxed()
			.sorted((fst, snd) -> compare(fst, snd, schedule))
			.toArray(Integer[]::new);

		TimeMark lastMark = null;
		Auditory lastAuditory = null;

		for (Integer position : positions) {
			TimeMark mark = schedule.getTimeMarks()[position];
			Auditory auditory = schedule.getAuditories()[position];

			if (lastMark == null || lastMark.getWeekDay() != mark.getWeekDay()) {
				// first lesson of the day
				lastMark = mark;
				lastAuditory = auditory;
				continue;
			}

			int diff = mark.getDayTime().getId() - lastMark.getDayTime().getId();
			if (diff == 1) {
				res += checkDistance(auditory, lastAuditory, ONE_TIME_DISTANCE_MULTIPLIER);
			} else if (diff >= 2) {
				res += checkDistance(auditory, lastAuditory, diff * TWO_TIMES_DISTANCE_MULTIPLIER);
			}

			lastMark = mark;
			lastAuditory = auditory;
		}
		return res;
	}

	private int compare(int fst, int snd, Schedule schedule) {
		return new TimeMarkComparator().compare(
			schedule.getTimeMarks()[fst],
			schedule.getTimeMarks()[snd]
		);
	}

	private double checkDistance(Auditory fst, Auditory snd, double multiplier) {
		return auditoryService.getDistance(fst, snd) * multiplier;
	}

	private int checkBounds(Schedule schedule) {
		ScheduleConfig config = schedule.getConfig();
		int res = 0;

		for (int i = 0; i < schedule.size(); ++i) {

			CurriculumUnit unit = config.getCurriculum().get(i);
			TimeMark mark = schedule.getTimeMarks()[i];
			Auditory auditory = schedule.getAuditories()[i];

			// check bound "auditoria_times"
			TimeBound auditoriaTimes = config
				.getBounds()
				.getAuditoryTimes()
				.get(auditory.getId());

			res += auditoriaTimes == null || auditoriaTimes.validate(mark)
				? 0 : AUDITORIA_TIMES_PENALTY;

			// check bound "discipline_times"
			TimeBound disciplineTimes = config
				.getBounds()
				.getDisciplineTimes()
				.get(unit.getDisciplineId());

			res += disciplineTimes == null || disciplineTimes.validate(mark)
				? 0 : DISCIPLINE_TIMES_PENALTY;

			// check bound "discipline_auditories"
			ValueBound disciplineAuditories = config
				.getBounds()
				.getDisciplineAuditories()
				.get(unit.getDisciplineId());

			res += disciplineAuditories == null || disciplineAuditories.validate(auditory.getId())
				? 0 : DISCIPLINE_AUDITORIES_PENALTY;
		}

		return res;
	}

}
