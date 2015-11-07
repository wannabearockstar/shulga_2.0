package web.model;

/**
 * Created by wannabe on 07.11.15.
 */
public class Status {
    private double currentFitness;
    private double progress;
    private boolean finished = false;

    public Status() {
    }

    public Status(double progress, double currentFitness, boolean finished) {
        this.progress = progress;
        this.currentFitness = currentFitness;
        this.finished = finished;
    }

    public Status(double currentFitness, double progress) {
        this.currentFitness = currentFitness;
        this.progress = progress;
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

    public boolean isFinished() {
        return finished;
    }

    public Status setFinished(boolean finished) {
        this.finished = finished;
        return this;
    }
}
