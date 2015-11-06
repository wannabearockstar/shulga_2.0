package ga.model.comparator;

import ga.model.schedule.Professor;

import java.util.Comparator;

public class ProfessorComparator implements Comparator<Professor> {

    @Override
    public int compare(Professor fst, Professor snd) {
        return Integer.compare(fst.getId(), snd.getId());
    }
}
