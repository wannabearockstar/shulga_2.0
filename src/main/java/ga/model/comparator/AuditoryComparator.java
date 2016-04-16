package ga.model.comparator;

import ga.model.schedule.Auditory;

import java.util.Comparator;

public class AuditoryComparator implements Comparator<Auditory> {

	@Override
	public int compare(Auditory fst, Auditory snd) {
		return Integer.compare(fst.getId(), snd.getId());
	}
}
