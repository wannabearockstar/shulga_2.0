package web.service.data;

import ga.model.config.CurriculumUnit;
import ga.model.config.ScheduleConfig;
import ga.model.schedule.Auditory;
import ga.model.schedule.LessonType;
import ga.model.schedule.time.DayTime;
import ga.model.schedule.time.WeekDay;
import mapper.ScheduleConfigLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Created by wannabe on 07.11.15.
 */
@Service
public class DataService {
    public Integer createScheduleConfig(CurriculumUnit[] curriculums) throws IOException {
        ScheduleConfig config = new ScheduleConfig();

        List<Auditory> auditories = new ArrayList<>();
        auditories.addAll(ScheduleConfig.allAuditories.values());
        config.setAuditories(auditories);

        config.setDisciplines(Arrays.asList(curriculums).stream()
                .map(curriculumUnit -> ScheduleConfig.allDisciplines.get(curriculumUnit.getDisciplineId()))
                .distinct()
                .collect(Collectors.toList()));

        List<LessonType> lessonTypes = new ArrayList<>();
        lessonTypes.addAll(ScheduleConfig.allLessonTypes.values());
        config.setLessonTypes(lessonTypes);

        config.setProfessors(Arrays.asList(curriculums).stream()
                .map(curriculumUnit -> ScheduleConfig.allProfessors.get(curriculumUnit.getProfessorId()))
                .distinct()
                .collect(Collectors.toList()));

        config.setGroups(Arrays.asList(curriculums).stream()
                .map(curriculumUnit -> ScheduleConfig.allGroups.get(curriculumUnit.getGroupId()))
                .collect(Collectors.toList()));

        List<DayTime> dayTimes = new ArrayList<>();
        dayTimes.addAll(ScheduleConfig.allDayTimes.values());
        config.setTimes(dayTimes);

        config.setWeekDays(Arrays.asList(WeekDay.values()));

        config.setCurriculum(Arrays.asList(curriculums));

        int randomIndex = ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE);
        ScheduleConfigLoader.saveToLocal(config, String.format("schedule_config_%d.json", randomIndex));

        return randomIndex;
    }

    public ScheduleConfig getScheduleConfig(int id) {
        try {
            return ScheduleConfigLoader.fromLocal(String.format("schedule_config_%d.json", id));
        } catch (IOException e) {
            return null;
        }
    }
}
