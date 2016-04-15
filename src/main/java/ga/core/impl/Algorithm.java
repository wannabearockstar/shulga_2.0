package ga.core.impl;

import ga.core.FitnessHandler;
import ga.core.config.AlgorithmConfig;
import ga.core.model.Population;
import ga.core.utils.mutation.Mutation;
import ga.core.utils.mutation.ReplaceMutation;
import ga.core.utils.selection.Selection;
import ga.core.utils.selection.TournamentSelection;
import ga.model.config.ScheduleConfig;
import ga.model.schedule.Auditory;
import ga.model.schedule.Schedule;
import ga.model.schedule.time.TimeMark;
import ga.model.service.PopulationService;
import ga.model.service.ScheduleService;
import mapper.ScheduleConfigLoader;
import org.springframework.beans.factory.annotation.Autowired;
import web.model.Status;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Algorithm implements Runnable {
	public static Map<Integer, Status> algorithmStatuses = new ConcurrentHashMap<>();
	public static int CATACLYSM_LIMIT = 20;
	public static double CATACLYSM_PART = 0.3;
	public static double MUTATION_RATE = 0.02;
	public static double MUTATION_STEP = 0.01;
	public static int TOURNAMENT_SIZE = 10;
	private final int algorithmId;
	@Autowired
	private PopulationService populationService;
	@Autowired
	private ScheduleService scheduleService;
	private AlgorithmConfig algConfig;
	private ScheduleConfig scheduleConfig;
	private FitnessHandler fitnessHandler;
	private Selection selection;
	private Mutation mutation;

	public Algorithm(AlgorithmConfig algConfig, ScheduleConfig scheduleConfig, FitnessHandler handler, int algorithmId) {
		this.algConfig = algConfig;
		this.scheduleConfig = scheduleConfig;
		this.fitnessHandler = handler;
		this.algorithmId = algorithmId;
		this.mutation = new ReplaceMutation(MUTATION_RATE, MUTATION_STEP, scheduleConfig);
		this.selection = new TournamentSelection(TOURNAMENT_SIZE);
	}

	@Override
	public void run() {
		Population population = populationService.create(algConfig.getPopulationSize(), scheduleConfig, fitnessHandler);
		int cataclysmCounter = 0;
		double lastFitness = scheduleService.getFitness(populationService.getFittestSchedule(population));
		Double maxFitness = null;
		double roundTime = 0;
		long startTime = 0;
		Status status;
		double remaningTime = 0;
		long currentTime;

		for (int i = 0; i < algConfig.getRoundNumber(); i++) {

			startTime = System.currentTimeMillis();
			population = evolve(population);

			if (scheduleService.getFitness(populationService.getFittestSchedule(population)) == lastFitness) {
				cataclysmCounter++;
			} else {
				cataclysmCounter = 0;
				lastFitness = scheduleService.getFitness(populationService.getFittestSchedule(population));
			}

			if (cataclysmCounter >= CATACLYSM_LIMIT) {
				cataclysmCounter = 0;
				population = populationService.cataclysm(population, CATACLYSM_PART, scheduleConfig, fitnessHandler);
				System.out.println("Cataclysm...");
			}
			currentTime = System.currentTimeMillis();
			roundTime += currentTime - startTime;
			System.out.println(scheduleService.getFitness(populationService.getFittestSchedule(population)) + ". Round " + i);
			if (i == 0 || (i >= 1000 && i % 1000 == 0)) {
				remaningTime = (algConfig.getRoundNumber() - i) * (roundTime * 1.0 / (i * 1000));
			}
			if (lastFitness / scheduleService.getFitness(populationService.getFittestSchedule(population)) > 3.5) {
				algorithmStatuses.put(algorithmId, new Status(scheduleService.getFitness(populationService.getFittestSchedule(population)), null, i * 1.0 / algConfig.getRoundNumber(), remaningTime));
			} else {
				if (maxFitness == null) {
					maxFitness = lastFitness;
				}
				algorithmStatuses.put(algorithmId, new Status(scheduleService.getFitness(populationService.getFittestSchedule(population)), maxFitness, i * 1.0 / algConfig.getRoundNumber(), remaningTime));
			}

			if (scheduleService.getFitness(populationService.getFittestSchedule(population)) == 0) {
				break;
			}
		}
		try {
			ScheduleConfigLoader.saveToLocal(populationService.getFirstScheduleWithoutCollisions(population), String.format("schedule_result_%d.json", algorithmId));
			algorithmStatuses.remove(algorithmId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected Population evolve(Population population) {
		Population newPopulation = recombination(population);
		mutation.mutate(newPopulation);

		// insert elite schedule
		newPopulation.setSchedule(0, populationService.getFittestSchedule(population));

		return newPopulation;
	}

	private Population recombination(Population population) {
		Population newPopulation = new Population(population.size());
		for (int i = 0; i < population.size(); i++) {
			Schedule child = crossover(selection.get(population, 2));
			newPopulation.setSchedule(i, child);
		}
		return newPopulation;
	}

	private Schedule crossover(Population population) {
		Schedule newSchedule = new Schedule(scheduleConfig.getCurriculum().size());

		TimeMark[] marks = new TimeMark[newSchedule.size()];
		for (int i = 0; i < marks.length; i++) {
			int index = ThreadLocalRandom.current().nextInt(0, population.size());
			marks[i] = population.getSchedules()[index].getTimeMarks()[i];
		}
		newSchedule.setTimeMarks(marks);

		Auditory[] auditories = new Auditory[newSchedule.size()];
		for (int i = 0; i < auditories.length; i++) {
			int index = ThreadLocalRandom.current().nextInt(0, population.size());
			auditories[i] = population.getSchedules()[index].getAuditories()[i];
		}
		newSchedule.setAuditories(auditories);

		return newSchedule;
	}
}
