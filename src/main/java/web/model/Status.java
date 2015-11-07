package web.model;

/**
 * Created by wannabe on 07.11.15.
 */
public class Status {
    private double currentFitness;
    private double progress;

    public Status() {
    }

    public Status(double progress, double currentFitness) {
        this.progress = progress;
        this.currentFitness = currentFitness;
    }

    public double getCurrentFitness() {
        return currentFitness;
    }

    public Status setCurrentFitness(double currentFitness) {
        this.currentFitness = currentFitness;
        return this;
    }

    public double getProgress() {
        return progress;
    }

    public Status setProgress(double progress) {
        this.progress = progress;
        return this;
    }
}
