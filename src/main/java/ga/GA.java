package ga;

import ga.core.FitnessHandler;
import ga.core.impl.FitnessHandlerImpl;
import ga.core.model.AlgorithmConfig;
import ga.model.config.ScheduleConfig;

public class GA {

	public static int solve(ScheduleConfig scheduleConfig, int id) {
		AlgorithmConfig algConfig = new AlgorithmConfig(5000, 50);
		FitnessHandler handler = new FitnessHandlerImpl();
		Algorithm algorithm = new Algorithm(algConfig, scheduleConfig, handler, id);
		new Thread(algorithm).start();
		return id;
	}

}
