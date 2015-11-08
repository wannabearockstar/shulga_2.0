package mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ga.model.config.CurriculumUnit;
import ga.model.config.ScheduleConfig;
import ga.model.schedule.*;
import ga.model.schedule.time.DayTime;
import ga.model.schedule.time.WeekDay;
import mapper.model.GroupInfo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleConfigLoader {

    /**
     * Load ScheduleConfig from remote server
     */
    public static ScheduleConfig fromRemote(String url, List<Group> groups) throws IOException {
        List<GroupInfo> data = GroupInfoLoader.fromRemote(url, groups);
        return GroupInfo.toScheduleConfig(data);
    }

    /**
     * Load ScheduleConfig from file system
     */
    public static ScheduleConfig fromLocal(String filename) throws IOException {
        Path path = Paths.get(filename);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try (BufferedReader writer = Files.newBufferedReader(path)) {
            return mapper.readValue(writer, ScheduleConfig.class);
        }
    }

    public static ScheduleConfig fromCurriculum(CurriculumUnit[] curriculum) throws IOException {
        ScheduleConfig config = new ScheduleConfig();

        List<Auditory> auditories = new ArrayList<>();
        auditories.addAll(ScheduleConfig.allAuditories.values());
        config.setAuditories(auditories);

        config.setDisciplines(Arrays.asList(curriculum).stream()
                .map(curriculumUnit -> ScheduleConfig.allDisciplines.get(curriculumUnit.getDisciplineId()))
                .distinct()
                .collect(Collectors.toList()));

        List<LessonType> lessonTypes = new ArrayList<>();
        lessonTypes.addAll(ScheduleConfig.allLessonTypes.values());
        config.setLessonTypes(lessonTypes);

        config.setProfessors(Arrays.asList(curriculum).stream()
                .map(curriculumUnit -> ScheduleConfig.allProfessors.get(curriculumUnit.getProfessorId()))
                .distinct()
                .collect(Collectors.toList()));

        config.setGroups(Arrays.asList(curriculum).stream()
                .map(curriculumUnit -> ScheduleConfig.allGroups.get(curriculumUnit.getGroupId()))
                .distinct()
                .sorted(Comparator.comparingInt(Group::getId))
                .collect(Collectors.toList()));

        List<DayTime> dayTimes = new ArrayList<>();
        dayTimes.addAll(ScheduleConfig.allDayTimes.values());
        config.setTimes(dayTimes);

        config.setWeekDays(Arrays.asList(WeekDay.values()));

        config.setCurriculum(Arrays.asList(curriculum));

        return config;
    }

    public static Schedule fromLocalSchedule(String filename) throws IOException {
        Path path = Paths.get(filename);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try (BufferedReader writer = Files.newBufferedReader(path)) {
            return mapper.readValue(writer, Schedule.class);
        }
    }

    /**
     * Save ScheduleConfig to file system
     */
    public static void saveToLocal(ScheduleConfig config, String filename) throws IOException {
        Path path = Paths.get(filename);
        ObjectMapper mapper = new ObjectMapper();

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            mapper.writeValue(writer, config);
        }
    }

    public static void saveToLocal(Schedule schedule, String filename) throws IOException {
        Path path = Paths.get(filename);
        ObjectMapper mapper = new ObjectMapper();

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            mapper.writeValue(writer, schedule);
        }
    }



    public static void initAllEntitiesInMemory() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (BufferedReader br = Files.newBufferedReader(Paths.get("proffesors.json"))) {
            Professor[] professors = mapper.readValue(br, Professor[].class);
            for (Professor professor : professors) {
                ScheduleConfig.allProfessors.put(professor.getId(), professor);
            }
        }

        try (BufferedReader br = Files.newBufferedReader(Paths.get("groups_full.json"))) {
            Group[] groups = mapper.readValue(br, Group[].class);
            for (Group group : groups) {
                ScheduleConfig.allGroups.put(group.getId(), group);
            }
        }

        try (BufferedReader br = Files.newBufferedReader(Paths.get("auditories.json"))) {
            Auditory[] auditories = mapper.readValue(br, Auditory[].class);
            for (Auditory auditory : auditories) {
                ScheduleConfig.allAuditories.put(auditory.getId(), auditory);
            }
        }

        try (BufferedReader br = Files.newBufferedReader(Paths.get("lesson_types.json"))) {
            LessonType[] lessonTypes = mapper.readValue(br, LessonType[].class);
            for (LessonType lessonType : lessonTypes) {
                ScheduleConfig.allLessonTypes.put(lessonType.getId(), lessonType);
            }
        }

        try (BufferedReader br = Files.newBufferedReader(Paths.get("times.json"))) {
            DayTime[] dayTimes = mapper.readValue(br, DayTime[].class);
            for (DayTime dayTime : dayTimes) {
                ScheduleConfig.allDayTimes.put(dayTime.getId(), dayTime);
            }
        }

        try (BufferedReader br = Files.newBufferedReader(Paths.get("disciplines.json"))) {
            Discipline[] disciplines = mapper.readValue(br, Discipline[].class);
            for (Discipline discipline : disciplines) {
                ScheduleConfig.allDisciplines.put(discipline.getId(), discipline);
            }
        }
        System.out.println("All entities was init in memory");
    }

}
