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
	private ScheduleRepository scheduleRepository;

	public static Schedule random(final ScheduleConfig config, FitnessHandler fitnessHandler) {
		Schedule schedule = new Schedule(config, fitnessHandler);
		TimeMark[] timeMarks = new TimeMark[config.getCurriculum().size()];
		Auditory[] auditories = new Auditory[config.getCurriculum().size()];

		for (int i = 0; i < config.getCurriculum().size(); i++) {
			timeMarks[i] = TimeMark.random(config);
			auditories[i] = AuditoryService.random(config);
		}

		schedule.setAuditories(auditories);
		schedule.setTimeMarks(timeMarks);

		return schedule;
	}
}
