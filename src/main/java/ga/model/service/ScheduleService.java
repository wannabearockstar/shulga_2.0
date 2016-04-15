package ga.model.service;

import ga.core.FitnessHandler;
import ga.model.config.ScheduleConfig;
import ga.model.repository.ScheduleRepository;
import ga.model.schedule.Auditory;
import ga.model.schedule.Schedule;
import ga.model.schedule.time.TimeMark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wannabe on 15.04.16.
 */
@Service
public class ScheduleService {
	@Autowired
	private AuditoryService auditoryService;
	@Autowired
	private FitnessHandler fitnessHandler;

	@Autowired
	private ScheduleRepository scheduleRepository;

	public Schedule random(final ScheduleConfig config, FitnessHandler fitnessHandler) {
		Schedule schedule = new Schedule(config.getCurriculum().size());
		TimeMark[] timeMarks = new TimeMark[config.getCurriculum().size()];
		Auditory[] auditories = new Auditory[config.getCurriculum().size()];

		for (int i = 0; i < config.getCurriculum().size(); i++) {
			timeMarks[i] = TimeMark.random(config);
			auditories[i] = auditoryService.random(config);
		}

		schedule.setAuditories(auditories);
		schedule.setTimeMarks(timeMarks);

		return schedule;
	}

	public Double getFitness(Schedule schedule) {
		Double fitness = schedule.getFitness();
		if (fitness == null) {
			schedule.setFitness(fitnessHandler.computeFitness(schedule));
		}
		return schedule.getFitness();
	}

	public boolean hasCollisions(Schedule schedule) {
		return fitnessHandler.hasCollisions(schedule);
	}
}
