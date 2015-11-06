package ga.core;

import ga.model.schedule.Schedule;

public interface FitnessHandler {
    /**
     * Calculate schedule fitness
     */
    double computeFitness(Schedule schedule);

    /**
     * Check schedule collisions
     */
    boolean hasCollisions(Schedule schedule);
}
