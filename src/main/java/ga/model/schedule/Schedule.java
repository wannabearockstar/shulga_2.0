package ga.model.schedule;

import ga.model.schedule.time.TimeMark;

public class Schedule {

	private TimeMark[] timeMarks;
	private Auditory[] auditories;
	private Double fitness;

	public Schedule(int curriculumSize) {
		this.timeMarks = new TimeMark[curriculumSize];
		this.auditories = new Auditory[curriculumSize];
	}

	public Schedule(TimeMark[] timeMarks, Auditory[] auditories, Double fitness) {
		this.timeMarks = timeMarks;
		this.auditories = auditories;
		this.fitness = fitness;
	}

	public Schedule() {
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
			throw new IllegalStateException("Compute fitness through ScheduleService::getFitness");
		}
		return fitness;
	}

	public void setFitness(Double fitness) {
		this.fitness = fitness;
	}

	public int size() {
		return auditories.length;
	}

	public void refreshFitness() {
		fitness = null;
	}
}
