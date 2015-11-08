package ga;

import ga.core.Algorithm;
import ga.core.FitnessHandler;
import ga.core.config.AlgorithmConfig;
import ga.core.impl.AlgorithmImpl;
import ga.core.impl.FitnessHandlerImpl;
import ga.model.config.ScheduleConfig;

public class GA {

    public static int solve(ScheduleConfig scheduleConfig, int id) {
        AlgorithmConfig algConfig = new AlgorithmConfig(5000, 50);
        FitnessHandler handler = new FitnessHandlerImpl();
        Algorithm algorithm = new AlgorithmImpl(algConfig, scheduleConfig, handler, id);
        new Thread(algorithm).start();
        return id;
    }

}
