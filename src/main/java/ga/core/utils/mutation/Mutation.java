package ga.core.utils.mutation;

import ga.core.model.Population;
import ga.model.config.ScheduleConfig;
import ga.model.schedule.Schedule;
import ga.model.schedule.time.TimeMark;
import ga.model.service.AuditoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mutation {
	public final double mutationRate;
	public final double mutationStep;
	@Autowired
	private AuditoryService auditoryService;

	public Mutation(double mutationRate, double mutationStep) {
		this.mutationRate = mutationRate;
		this.mutationStep = mutationStep;
	}

	public void mutate(Population population, ScheduleConfig config) {
		for (Schedule schedule : population.getSchedules()) {
			mutate(schedule, config);
		}
	}

	public void mutate(Schedule schedule, ScheduleConfig config) {
		boolean mutation;
		for (int i = 0; i < schedule.size(); i++) {
			mutation = false;

			if (Math.random() < mutationRate) {
				mutation = true;
				schedule.getTimeMarks()[i] = TimeMark.random(config);
				schedule.refreshFitness();
			}

			if (Math.random() < (mutation ? mutationRate : mutationRate + mutationStep)) {
				mutation = true;
				schedule.getAuditories()[i] = auditoryService.random(config);
				schedule.refreshFitness();
			}
		}
	}
}
