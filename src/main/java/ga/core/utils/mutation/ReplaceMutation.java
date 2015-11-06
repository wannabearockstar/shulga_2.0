package ga.core.utils.mutation;

import ga.model.config.ScheduleConfig;
import ga.model.schedule.Auditory;
import ga.model.schedule.Schedule;
import ga.model.schedule.time.TimeMark;

public class ReplaceMutation extends Mutation {
    public final double mutationRate;
    public final double mutationStep;
    public final ScheduleConfig config;

    public ReplaceMutation(double mutationRate, double mutationStep, ScheduleConfig config) {
        this.mutationRate = mutationRate;
        this.mutationStep = mutationStep;
        this.config = config;
    }

    @Override
    public void mutate(Schedule schedule) {
        boolean mutation;
        for (int i = 0; i < schedule.size(); i++) {
            mutation = false;

            if (Math.random() < mutationRate) {
                mutation = true;
                schedule.getTimeMarks()[i] = TimeMark.random(config);
                schedule.refreshFitness();
            }

            if (Math.random() < (mutation ? mutationRate : mutationRate + mutationStep)) {
                mutation = true;
                schedule.getAuditories()[i] = Auditory.random(config);
                schedule.refreshFitness();
            }
        }
    }
}
