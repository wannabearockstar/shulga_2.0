package ga.core.model;

import ga.core.FitnessHandler;
import ga.model.config.ScheduleConfig;
import ga.model.schedule.Schedule;
import ga.model.service.ScheduleService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Population {
    private Schedule[] schedules;

    public Population(Schedule[] schedules) {
        this.schedules = schedules;
    }

    public Population(int capacity) {
        this.schedules = new Schedule[capacity];
    }

    public Population(List<Schedule> schedules) {
        this.schedules = new Schedule[schedules.size()];
        this.schedules = schedules.toArray(this.schedules);
    }

    public Population(int populationSize, boolean init, ScheduleConfig config, FitnessHandler fitnessHandler) {
        schedules = new Schedule[populationSize];
        if (init) {
            for (int i = 0; i < schedules.length; i++) {
                schedules[i] = ScheduleService.random(config, fitnessHandler);
            }
        }
    }

    public Schedule getFittest() {
        return Stream.of(schedules)
                .min(Comparator.comparingDouble(Schedule::getFitness))
                .get();
    }

    public Schedule getWithoutCollisions() {
        List<Schedule> sorted = Stream.of(schedules)
                .sorted(Comparator.comparingDouble(Schedule::getFitness))
                .collect(Collectors.toList());

        for (Schedule schedule : sorted) {
            if (!schedule.hasCollisions())
                return schedule;
        }

        return sorted.get(0);
    }

    public Population setSchedule(int index, Schedule schedule) {
        this.schedules[index] = schedule;
        return this;
    }

    public int size() {
        return schedules.length;
    }

    public Schedule[] getSchedules() {
        return schedules;
    }

    public Population setSchedules(Schedule[] schedules) {
        this.schedules = schedules;
        return this;
    }

    public Population cataclysm(double part, ScheduleConfig config, FitnessHandler handler) {
        for (int i = 0; i < schedules.length; i++) {
            if (i >= schedules.length * part) {
                schedules[i] = ScheduleService.random(config, handler);
            }
        }
        return this;
    }
}
