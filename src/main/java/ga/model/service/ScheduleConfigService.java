package ga.model.service;

import ga.model.config.ScheduleConfig;
import ga.model.repository.ScheduleConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wannabe on 15.04.16.
 */
@Service
public class ScheduleConfigService {
	@Autowired
	private ScheduleConfigRepository scheduleConfigRepository;

	public ScheduleConfig findOne(String id) {
		return scheduleConfigRepository.findOne(id);
	}

	public ScheduleConfig save(ScheduleConfig config) {
		return scheduleConfigRepository.save(config);
	}
}
