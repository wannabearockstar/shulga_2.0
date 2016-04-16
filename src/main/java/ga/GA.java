package ga;

import ga.core.impl.Algorithm;
import ga.model.config.ScheduleConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class GA {
	@Autowired
	private ApplicationContext applicationContext;

	public String solve(ScheduleConfig scheduleConfig, String id) {
		Algorithm algorithm = (Algorithm) applicationContext.getBean("algorithm");
		algorithm.setAlgorithmId(id);
		algorithm.setScheduleConfig(scheduleConfig);
		new Thread(algorithm).start();
		return id;
	}
}
