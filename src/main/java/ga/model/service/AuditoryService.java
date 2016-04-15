package ga.model.service;

import ga.model.config.ScheduleConfig;
import ga.model.repository.AuditoryRepository;
import ga.model.schedule.Auditory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by wannabe on 15.04.16.
 */
@Service
public class AuditoryService {

	@Autowired
	private AuditoryRepository auditoryRepository;

	public static Auditory random(final ScheduleConfig config) {
		int idx = ThreadLocalRandom.current().nextInt(0, config.getAuditories().size());
		return config.getAuditories().get(idx);
	}
}
