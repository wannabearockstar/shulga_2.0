package ga.core.impl;

import ga.core.config.AlgorithmSpringConfiguration;
import ga.core.model.AlgorithmConfig;
import ga.core.model.Population;
import ga.core.utils.mutation.Mutation;
import ga.core.utils.selection.Selection;
import ga.model.config.ScheduleConfig;
import ga.model.schedule.Auditory;
import ga.model.schedule.Schedule;
import ga.model.schedule.time.TimeMark;
import ga.model.service.PopulationService;
import ga.model.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import web.model.Status;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Algorithm implements Runnable {
	@Resource
	private Map<Integer, Status> algorithmStatuses;
	private int algorithmId;
	@Autowired
	private PopulationService populationService;
	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private FitnessHandler fitnessHandler;
	@Autowired
	private Selection selection;
	@Autowired
	private Mutation mutation;
	@Autowired
	private AlgorithmConfig algConfig;


	private ScheduleConfig scheduleConfig;

	public Algorithm() {
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

			if (cataclysmCounter >= AlgorithmSpringConfiguration.CATACLYSM_LIMIT) {
				cataclysmCounter = 0;
				population = populationService.cataclysm(population, AlgorithmSpringConfiguration.CATACLYSM_PART, scheduleConfig, fitnessHandler);
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
		scheduleService.save(populationService.getFirstScheduleWithoutCollisions(population));
		algorithmStatuses.remove(algorithmId);
	}

	protected Population evolve(Population population) {
		Population newPopulation = recombination(population);
		mutation.mutate(newPopulation, scheduleConfig);

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

	public void setAlgorithmId(int algorithmId) {
		this.algorithmId = algorithmId;
	}

	public void setScheduleConfig(ScheduleConfig scheduleConfig) {
		this.scheduleConfig = scheduleConfig;
	}
}
