package ga.model.comparator;

import ga.model.schedule.Discipline;

import java.util.Comparator;

public class DisciplineComparator implements Comparator<Discipline> {

    @Override
    public int compare(Discipline fst, Discipline snd) {
        return Integer.compare(fst.getId(), snd.getId());
    }
}
