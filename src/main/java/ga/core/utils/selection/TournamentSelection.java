package ga.core.utils.selection;

import ga.core.model.Population;
import ga.model.schedule.Schedule;
import ga.model.service.PopulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class TournamentSelection implements Selection {
	private final int tournamentSize;
	@Autowired
	private PopulationService populationService;

	public TournamentSelection(int tournamentSize) {
		this.tournamentSize = tournamentSize;
	}

	@Override
	public Population get(Population population, int selectionSize) {
		Population newPopulation = new Population(selectionSize);
		List<Schedule> scheduleList = Arrays.asList(population.getSchedules());

		for (int i = 0; i < selectionSize; i++) {
			Collections.shuffle(scheduleList);

			List<Schedule> subList = scheduleList.subList(0, tournamentSize);
			Schedule fittest = populationService.getFittestSchedule(populationService.create(subList));

			newPopulation.setSchedule(i, fittest);
		}

		return newPopulation;
	}
}
