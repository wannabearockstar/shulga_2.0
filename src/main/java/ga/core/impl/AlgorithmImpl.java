package ga.core.impl;

import ga.core.Algorithm;
import ga.core.FitnessHandler;
import ga.core.config.AlgorithmConfig;
import ga.core.model.Population;
import ga.core.utils.mutation.Mutation;
import ga.core.utils.mutation.ReplaceMutation;
import ga.core.utils.selection.Selection;
import ga.core.utils.selection.TournamentSelection;
import ga.model.config.ScheduleConfig;
import ga.model.schedule.Auditory;
import ga.model.schedule.Schedule;
import ga.model.schedule.time.TimeMark;
import mapper.ScheduleConfigLoader;
import web.model.Status;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class AlgorithmImpl implements Algorithm {
    public static Map<Integer, Status> algorithmStatuses = new ConcurrentHashMap<>();

    public static int CATACLYSM_LIMIT = 20;
    public static double CATACLYSM_PART = 0.3;

    public static double MUTATION_RATE = 0.02;
    public static double MUTATION_STEP = 0.01;

    public static int TOURNAMENT_SIZE = 10;
    private final int algorithmId;
    private AlgorithmConfig algConfig;
    private ScheduleConfig scheduleConfig;
    private FitnessHandler fitnessHandler;
    private Selection selection;
    private Mutation mutation;

    public AlgorithmImpl(AlgorithmConfig algConfig, ScheduleConfig scheduleConfig, FitnessHandler handler, int algorithmId) {
        this.algConfig = algConfig;
        this.scheduleConfig = scheduleConfig;
        this.fitnessHandler = handler;
        this.algorithmId = algorithmId;
        this.mutation = new ReplaceMutation(MUTATION_RATE, MUTATION_STEP, scheduleConfig);
        this.selection = new TournamentSelection(TOURNAMENT_SIZE);
    }

    @Override
    public void run() {
        Population population = new Population(algConfig.getPopulationSize(), true, scheduleConfig, fitnessHandler);

        int cataclysmCounter = 0;
        double lastFitness = population.getFittest().getFitness();
        Double maxFitness = null;
        double roundTime = 0;
        long startTime = 0;
        Status status;
        double remaningTime = 0;
        long currentTime;

        for (int i = 0; i < algConfig.getRoundNumber(); i++) {

            startTime = System.currentTimeMillis();
            population = evolve(population);

            if (population.getFittest().getFitness() == lastFitness) {
                cataclysmCounter++;
            } else {
                cataclysmCounter = 0;
                lastFitness = population.getFittest().getFitness();
            }

            if (cataclysmCounter >= CATACLYSM_LIMIT) {
                cataclysmCounter = 0;
                population.cataclysm(CATACLYSM_PART, scheduleConfig, fitnessHandler);
                System.out.println("Cataclysm...");
            }
            currentTime = System.currentTimeMillis();
            roundTime += currentTime - startTime;
            System.out.println(population.getFittest().getFitness());
            System.out.println("ROund number " + i);
            if (i == 0 || (i >= 1000 && i % 1000 == 0)) {
                remaningTime = (algConfig.getRoundNumber() - i) * (roundTime * 1.0 / (i * 1000));
            }
            if (lastFitness / population.getFittest().getFitness() > 3.5) {
                algorithmStatuses.put(algorithmId, new Status(population.getFittest().getFitness(), null, i * 1.0 / algConfig.getRoundNumber(), remaningTime));
            } else {
                if (maxFitness == null) {
                    maxFitness = lastFitness;
                }
                algorithmStatuses.put(algorithmId, new Status(population.getFittest().getFitness(), maxFitness, i * 1.0 / algConfig.getRoundNumber(), remaningTime));
            }

            if (population.getFittest().getFitness() == 0) {
                break;
            }
        }
        try {
            ScheduleConfigLoader.saveToLocal(population.getWithoutCollisions(), String.format("schedule_result_%d.json", algorithmId));
            algorithmStatuses.remove(algorithmId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Population evolve(Population population) {
        Population newPopulation = recombination(population);
        mutation.mutate(newPopulation);

        // insert elite schedule
        newPopulation.setSchedule(0, population.getFittest());

        return newPopulation;
    }

    private Population recombination(Population population) {
        Population newPopulation = new Population(population.size());
        for (int i = 0; i < population.size(); i++) {
            Schedule child = crossover(selection.get(population, 2));
            newPopulation.setSchedule(i, child);
        }
        return newPopulation;
    }

    private Schedule crossover(Population population) {
        Schedule newSchedule = new Schedule(scheduleConfig, fitnessHandler);

        TimeMark[] marks = new TimeMark[newSchedule.size()];
        for (int i = 0; i < marks.length; i++) {
            int index = ThreadLocalRandom.current().nextInt(0, population.size());
            marks[i] = population.getSchedules()[index].getTimeMarks()[i];
        }
        newSchedule.setTimeMarks(marks);

        Auditory[] auditories = new Auditory[newSchedule.size()];
        for (int i = 0; i < auditories.length; i++) {
            int index = ThreadLocalRandom.current().nextInt(0, population.size());
            auditories[i] = population.getSchedules()[index].getAuditories()[i];
        }
        newSchedule.setAuditories(auditories);

        return newSchedule;
    }
}
