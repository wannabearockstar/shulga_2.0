package ga.core.config;

import ga.core.model.AlgorithmConfig;
import ga.core.utils.mutation.Mutation;
import ga.core.utils.selection.Selection;
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
	public final static double MUTATION_RATE = 0.02;
	public final static double MUTATION_STEP = 0.01;
	public final static int TOURNAMENT_SIZE = 10;

	@Bean
	public Mutation mutation() {
		return new Mutation(MUTATION_RATE, MUTATION_STEP);
	}

	@Bean
	public Selection selection() {
		return new Selection(TOURNAMENT_SIZE);
	}

	@Bean
	public AlgorithmConfig algorithmConfig() {
		return new AlgorithmConfig(5000, 50);
	}

	@Bean
	public Map<Integer, Status> algorithmStatuses() {
		return new ConcurrentHashMap<>();
	}
}
