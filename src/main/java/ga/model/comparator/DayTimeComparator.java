package ga.model.comparator;

import ga.model.schedule.time.DayTime;

import java.util.Comparator;

public class DayTimeComparator implements Comparator<DayTime> {

	@Override
	public int compare(DayTime fst, DayTime snd) {
		return Integer.compare(fst.getId(), snd.getId());
	}
}
