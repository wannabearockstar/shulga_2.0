package web.model;

/**
 * Created by wannabe on 07.11.15.
 */
public class Status {

	private double currentFitness;
	private double progress;
	private boolean finished = false;
	private Double maxFitness = null;
	private Double remaningTime = null;


	public Status() {
	}

	public Status(double progress, double currentFitness, boolean finished, Double maxFitness) {
		this.progress = progress;
		this.currentFitness = currentFitness;
		this.finished = finished;
		this.maxFitness = maxFitness;
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

	public Status(double currentFitness, double progress, Double maxFitness) {
		this.currentFitness = currentFitness;
		this.progress = progress;
		this.maxFitness = maxFitness;
	}

	public Status(Double fitness, double currentFitness, Double maxFitness, double remaningTime) {
		this.currentFitness = currentFitness;
		this.maxFitness = maxFitness;
		this.remaningTime = remaningTime;
		this.currentFitness = fitness;
	}

	public Status(Double fitness, Double maxFitness, double progress, double remaningTime) {
		this.currentFitness = fitness;
		this.progress = progress;
		this.maxFitness = maxFitness;
		this.remaningTime = remaningTime;
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

	public Double getMaxFitness() {
		return maxFitness;
	}

	public Status setMaxFitness(Double maxFitness) {
		this.maxFitness = maxFitness;
		return this;
	}

	public Double getRemaningTime() {
		return remaningTime;
	}

	public Status setRemaningTime(Double remaningTime) {
		this.remaningTime = remaningTime;
		return this;
	}
}
