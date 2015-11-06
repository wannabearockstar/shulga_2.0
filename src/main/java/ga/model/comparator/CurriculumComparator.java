package ga.model.comparator;

import ga.model.config.CurriculumUnit;

import java.util.Comparator;

public class CurriculumComparator implements Comparator<CurriculumUnit> {

    @Override
    public int compare(CurriculumUnit fst, CurriculumUnit snd) {
        return Integer.compare(fst.getGroupId(), snd.getGroupId());
    }
}
