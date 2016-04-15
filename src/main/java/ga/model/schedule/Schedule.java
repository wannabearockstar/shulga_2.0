package ga.model.schedule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ga.core.FitnessHandler;
import ga.core.impl.FitnessHandlerImpl;
import ga.model.config.ScheduleConfig;
import ga.model.schedule.time.TimeMark;

public class Schedule {
    private final ScheduleConfig config;
    @JsonIgnore
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

    public Schedule(TimeMark[] timeMarks, Auditory[] auditories, Double fitness) {
        this.timeMarks = timeMarks;
        this.auditories = auditories;
        this.fitness = fitness;
        config = null;
        fitnessHandler = null;
    }

    public Schedule(ScheduleConfig config, TimeMark[] timeMarks, Auditory[] auditories, Double fitness) {
        this.config = config;
        this.timeMarks = timeMarks;
        this.auditories = auditories;
        this.fitness = fitness;
        fitnessHandler = null;
    }

    public Schedule() {
        config = null;
        fitnessHandler = null;
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
