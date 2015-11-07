package web.service.algorithm;

import ga.GA;
import ga.core.impl.AlgorithmImpl;
import ga.model.config.ScheduleConfig;
import ga.model.schedule.Schedule;
import mapper.ScheduleConfigLoader;
import org.springframework.stereotype.Service;
import web.model.Status;

import java.io.IOException;

/**
 * Created by wannabe on 07.11.15.
 */
@Service
public class AlgorithmService {
    public int runAlgorithm(int id) {
        if (AlgorithmImpl.algorithmStatuses.containsKey(id)) {
            return 0;
        }
        try {
            ScheduleConfig config = ScheduleConfigLoader.fromLocal(String.format("schedule_config_%d.json", id));
            return GA.solve(config, id);
        } catch (IOException e) {
            return 0;
        }
    }

    public Status getStatus(int id) {
        try {
            Schedule schedule = ScheduleConfigLoader.fromLocalSchedule(String.format("schedule_result_%d.json", id));
            return new Status(100, schedule.getFitness(), true);
        } catch (IOException e) {
            return AlgorithmImpl.algorithmStatuses.get(id);
        }
    }
}
