package mapper.serializer;

import ga.model.config.CurriculumUnit;
import ga.model.schedule.*;
import ga.model.schedule.time.DayTime;
import ga.model.schedule.time.TimeMark;
import ga.model.schedule.time.WeekDay;
import ga.model.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class ScheduleCsvSerializer implements Serializer<Schedule> {
	private static final String CSV_SEPARATOR = ",";
	private static final String LINE_SEPARATOR = System.lineSeparator();
	@Autowired
	private ScheduleService scheduleService;

	private static String escape(Object obj) {
		if (obj == null)
			return null;

		return obj.toString().replace(CSV_SEPARATOR, "");
	}

	public String serialize(Schedule schedule) throws IOException {
		List<Group> groups = schedule.getConfig().getGroups();
		StringBuilder sb = new StringBuilder();

		makeHeader(sb, schedule, groups);
		makeBody(sb, schedule, groups);

		return sb.toString();
	}

	public void serialize(Schedule schedule, String to) throws IOException {
		List<Group> groups = schedule.getConfig().getGroups();
		Path path = Paths.get(to);

		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			makeHeader(writer, schedule, groups);
			makeBody(writer, schedule, groups);
			makeBottom(writer, schedule, groups);
		}
	}

	public void serialize(Schedule schedule, Writer writer) throws IOException {
		List<Group> groups = schedule.getConfig().getGroups();

		makeHeader(writer, schedule, groups);
		makeBody(writer, schedule, groups);
	}

	public Schedule deserialize(String data) throws IOException {
		return null;
	}

	public Schedule deserialize(Reader reader) throws IOException {
		return null;
	}

	private void makeHeader(Appendable writer, Schedule schedule, List<Group> groups) throws IOException {
		writer.append("").append(CSV_SEPARATOR);
		writer.append("Пара").append(CSV_SEPARATOR);

		String names = groups.stream()
			.map(ScheduleCsvSerializer::escape)
			.collect(Collectors.joining(CSV_SEPARATOR));

		writer.append(names);
		writer.append(LINE_SEPARATOR);
	}

	private void makeBody(Appendable writer, Schedule schedule, List<Group> groups) throws IOException {
		for (WeekDay day : schedule.getConfig().getWeekDays()) {
			for (DayTime time : schedule.getConfig().getTimes()) {

				if (time.getId() == 1) {
					writer.append(escape(WeekDay.locale.get(day)));
				}

				writer.append(CSV_SEPARATOR);
				writer.append(escape(time)).append(CSV_SEPARATOR);

				TimeMark mark = new TimeMark(day, time);

				List<Integer> positions = IntStream.range(0, schedule.getTimeMarks().length)
					.filter(x -> Objects.equals(schedule.getTimeMarks()[x], mark))
					.boxed()
					.collect(Collectors.toList());

				if (positions.isEmpty()) {
					makeEmptyLine(writer, groups.size());
				} else {
					makeTimeLine(writer, schedule, groups, positions);
				}
			}

			makeEmptyLine(writer, groups.size() + 2);
		}
	}

	private void makeBottom(Appendable writer, Schedule schedule, List<Group> groups) throws IOException {
		writer.append("Fitness:").append(CSV_SEPARATOR);
		writer.append(escape(scheduleService.getFitness(schedule))).append(CSV_SEPARATOR);
		makeEmptyLine(writer, groups.size() - 2);
	}

	private void makeTimeLine(Appendable writer, Schedule schedule, List<Group> groups, List<Integer> positions)
		throws IOException {

		for (Group group : groups) {

			// todo: think about how to display collisions
			int position = positions.stream()
				.filter(pos -> schedule.getConfig().getCurriculum()
					.get(pos)
					.getGroupId() == group.getId())
				.findFirst()
				.orElse(-1);

			if (position == -1) {
				// fill empty schedule values for that time
				writer.append(CSV_SEPARATOR);
				continue;
			}

			CurriculumUnit unit = schedule.getConfig().getCurriculum().get(position);
			Auditory auditory = schedule.getAuditories()[position];

			Discipline discipline = schedule.getConfig()
				.getDisciplines()
				.stream()
				.filter(x -> x.getId() == unit.getDisciplineId())
				.findFirst().get();

			Professor professor = schedule.getConfig()
				.getProfessors()
				.stream()
				.filter(x -> x.getId() == unit.getProfessorId())
				.findFirst().get();

			writer.append(escape(auditory)).append(" | ");
			writer.append(escape(discipline)).append(" | ");
			writer.append(escape(professor)).append(CSV_SEPARATOR);
		}

		writer.append(LINE_SEPARATOR);
	}

	private void makeEmptyLine(Appendable writer, int len) throws IOException {
		for (int i = 0; i < len; ++i) {
			writer.append(CSV_SEPARATOR);
		}
		writer.append(LINE_SEPARATOR);
	}

}
