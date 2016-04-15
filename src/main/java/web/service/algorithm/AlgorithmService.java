package web.service.algorithm;

import ga.GA;
import ga.model.config.ScheduleConfig;
import ga.model.schedule.Schedule;
import ga.model.service.ScheduleConfigService;
import ga.model.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.Status;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by wannabe on 07.11.15.
 */
@Service
public class AlgorithmService {
	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private ScheduleConfigService scheduleConfigService;
	@Resource
	private Map<Integer, Status> algorithmStatuses;
	@Autowired
	private GA ga;

	public int runAlgorithm(int id) {
		if (algorithmStatuses.containsKey(id)) {
			return 0;
		}
		ScheduleConfig config = scheduleConfigService.findOne(id);
		return ga.solve(config, id);
	}

	public Status getStatus(int id) {
		Schedule schedule = scheduleService.findOne(id);
		if (schedule == null) {
			return algorithmStatuses.get(id);
		}
		return new Status(1, scheduleService.getFitness(schedule), true);
	}

}
