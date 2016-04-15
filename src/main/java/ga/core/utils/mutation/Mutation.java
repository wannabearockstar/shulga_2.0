package ga.core.utils.mutation;

import ga.core.model.Population;
import ga.model.schedule.Schedule;

public abstract class Mutation {

	abstract void mutate(Schedule schedules);

	public void mutate(Population population) {
		for (Schedule schedule : population.getSchedules()) {
			mutate(schedule);
		}
	}
}
