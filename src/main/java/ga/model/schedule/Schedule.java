package ga.model.schedule;

import ga.core.FitnessHandler;
import ga.core.impl.FitnessHandlerImpl;
import ga.model.config.ScheduleConfig;
import ga.model.schedule.time.TimeMark;

public class Schedule {

    private final ScheduleConfig config;
    private final FitnessHandler fitnessHandler;
    private TimeMark[] timeMarks;
    private Auditory[] auditories;
    private Double fitness;

    public Schedule(ScheduleConfig config) {
        this.config = config;
        this.fitnessHandler = new FitnessHandlerImpl();
        this.timeMarks = new TimeMark[config.getCurriculum().size()];
        this.auditories = new Auditory[config.getCurriculum().size()];
    }

    public Schedule(ScheduleConfig config, FitnessHandler fitnessHandler) {
        this.config = config;
        this.fitnessHandler = fitnessHandler;
        this.timeMarks = new TimeMark[config.getCurriculum().size()];
        this.auditories = new Auditory[config.getCurriculum().size()];
    }

    public static Schedule random(final ScheduleConfig config) {
        Schedule schedule = new Schedule(config);
        TimeMark[] timeMarks = new TimeMark[config.getCurriculum().size()];
        Auditory[] auditories = new Auditory[config.getCurriculum().size()];

        for (int i = 0; i < config.getCurriculum().size(); i++) {
            timeMarks[i] = TimeMark.random(config);
            auditories[i] = Auditory.random(config);
        }

        schedule.setAuditories(auditories);
        schedule.setTimeMarks(timeMarks);

        return schedule;
    }

    public static Schedule random(final ScheduleConfig config, FitnessHandler fitnessHandler) {
        Schedule schedule = new Schedule(config, fitnessHandler);
        TimeMark[] timeMarks = new TimeMark[config.getCurriculum().size()];
        Auditory[] auditories = new Auditory[config.getCurriculum().size()];

        for (int i = 0; i < config.getCurriculum().size(); i++) {
            timeMarks[i] = TimeMark.random(config);
            auditories[i] = Auditory.random(config);
        }

        schedule.setAuditories(auditories);
        schedule.setTimeMarks(timeMarks);

        return schedule;
    }

    public TimeMark[] getTimeMarks() {
        return timeMarks;
    }

    public Schedule setTimeMarks(TimeMark[] timeMarks) {
        this.timeMarks = timeMarks;
        return this;
    }

    public Auditory[] getAuditories() {
        return auditories;
    }

    public Schedule setAuditories(Auditory[] auditories) {
        this.auditories = auditories;
        return this;
    }

    public Double getFitness() {
        if (fitness == null) {
            fitness = fitnessHandler.computeFitness(this);
        }
        return fitness;
    }

    public ScheduleConfig getConfig() {
        return config;
    }

    public int size() {
        return auditories.length;
    }

    public void refreshFitness() {
        fitness = null;
    }

    public boolean hasCollisions() {
        return fitnessHandler.hasCollisions(this);
    }
}
