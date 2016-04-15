package ga.model.service;

import ga.core.impl.FitnessHandler;
import ga.core.model.Population;
import ga.model.config.ScheduleConfig;
import ga.model.schedule.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by wannabe on 15.04.16.
 */
@Service
public class PopulationService {

	@Autowired
	private ScheduleService scheduleService;

	public PopulationService() {
	}

	public Population create(int populationSize, ScheduleConfig config, FitnessHandler fitnessHandler) {
		List<Schedule> schedules = IntStream.range(0, populationSize)
			.boxed()
			.map(i -> scheduleService.random(config, fitnessHandler))
			.collect(Collectors.toList());
		return new Population(schedules);
	}

	public Population cataclysm(Population population, double part, ScheduleConfig config, FitnessHandler handler) {
		Schedule[] schedules = population.getSchedules();
		for (int i = 0; i < schedules.length; i++) {
			if (i >= schedules.length * part) {
				schedules[i] = scheduleService.random(config, handler);
			}
		}
		return new Population(schedules);
	}

	public Schedule getFirstScheduleWithoutCollisions(Population population) {
		Schedule[] schedules = population.getSchedules();
		List<Schedule> sorted = Stream.of(schedules)
			.sorted(Comparator.comparingDouble(scheduleService::getFitness))
			.collect(Collectors.toList());

		for (Schedule schedule : sorted) {
			if (!scheduleService.hasCollisions(schedule))
				return schedule;
		}

		return sorted.get(0);
	}

	public Schedule getFittestSchedule(Population population) {
		Schedule[] schedules = population.getSchedules();
		return Stream.of(schedules)
			.min(Comparator.comparingDouble(scheduleService::getFitness))
			.get();
	}

	public Population create(List<Schedule> schedules) {
		return new Population(schedules);
	}
}
