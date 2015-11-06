package ga.core.utils.selection;

import ga.core.model.Population;
import ga.model.schedule.Schedule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TournamentSelection implements Selection {
    private final int tournamentSize;

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
            Schedule fittest = new Population(subList).getFittest();

            newPopulation.setSchedule(i, fittest);
        }

        return newPopulation;
    }
}
