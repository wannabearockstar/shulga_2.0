package web.service.algorithm;

import ga.GA;
import ga.core.impl.AlgorithmImpl;
import ga.model.config.ScheduleConfig;
import mapper.ScheduleConfigLoader;
import org.springframework.stereotype.Service;

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
            return GA.solve(config);
        } catch (IOException e) {
            return 0;
        }
    }
}
