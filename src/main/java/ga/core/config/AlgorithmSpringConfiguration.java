package ga.core.config;

import ga.core.model.AlgorithmConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import web.model.Status;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wannabe on 15.04.16.
 */
@Configuration
@ComponentScan("ga")
public class AlgorithmSpringConfiguration {
	public final static int CATACLYSM_LIMIT = 20;
	public final static double CATACLYSM_PART = 0.3;

	@Bean
	public AlgorithmConfig algorithmConfig() {
		return new AlgorithmConfig(500, 50);
	}

	@Bean
	public Map<String, Status> algorithmStatuses() {
		return new ConcurrentHashMap<>();
	}
}
