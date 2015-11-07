package ga;

import ga.core.Algorithm;
import ga.core.FitnessHandler;
import ga.core.config.AlgorithmConfig;
import ga.core.impl.AlgorithmImpl;
import ga.core.impl.FitnessHandlerImpl;
import ga.model.config.ScheduleConfig;

import java.util.concurrent.ThreadLocalRandom;

public class GA {

    public static int solve(ScheduleConfig scheduleConfig) {
        AlgorithmConfig algConfig = new AlgorithmConfig(3000, 50);
        FitnessHandler handler = new FitnessHandlerImpl();
        int algorithmId = ThreadLocalRandom.current().nextInt();
        Algorithm algorithm = new AlgorithmImpl(algConfig, scheduleConfig, handler, algorithmId);
        algorithm.run();
        return algorithmId;
    }

}
