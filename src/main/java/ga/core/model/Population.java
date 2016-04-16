package ga.core.model;

import ga.model.schedule.Schedule;

import java.util.List;

public class Population {

	private Schedule[] schedules;

	public Population(int capacity) {
		this.schedules = new Schedule[capacity];
	}

	public Population(List<Schedule> schedules) {
		this.schedules = new Schedule[schedules.size()];
		this.schedules = schedules.toArray(this.schedules);
	}

	public Population(Schedule[] schedules) {
		this.schedules = schedules;
	}


	public Population setSchedule(int index, Schedule schedule) {
		this.schedules[index] = schedule;
		return this;
	}

	public int size() {
		return schedules.length;
	}

	public Schedule[] getSchedules() {
		return schedules;
	}

}
