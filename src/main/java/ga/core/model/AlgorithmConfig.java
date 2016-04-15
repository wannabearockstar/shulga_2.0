package ga.core.model;

public class AlgorithmConfig {

	private int populationSize;
	private int roundNumber;

	public AlgorithmConfig(int roundNumber, int populationSize) {
		this.roundNumber = roundNumber;
		this.populationSize = populationSize;
	}

	public int getPopulationSize() {
		return populationSize;
	}

	public int getRoundNumber() {
		return roundNumber;
	}
}
