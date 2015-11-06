package ga;

import ga.core.Algorithm;
import ga.core.FitnessHandler;
import ga.core.config.AlgorithmConfig;
import ga.core.impl.AlgorithmImpl;
import ga.core.impl.FitnessHandlerImpl;
import ga.model.config.ScheduleConfig;
import ga.model.schedule.Schedule;

public class GA {

    public static Schedule solve(ScheduleConfig scheduleConfig) {
        AlgorithmConfig algConfig = new AlgorithmConfig(3000, 50);
        FitnessHandler handler = new FitnessHandlerImpl();
        Algorithm algorithm = new AlgorithmImpl(algConfig, scheduleConfig, handler);

        return algorithm.run();
    }

}
