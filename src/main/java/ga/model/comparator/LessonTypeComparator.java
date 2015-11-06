package ga.model.comparator;

import ga.model.schedule.LessonType;

import java.util.Comparator;

public class LessonTypeComparator implements Comparator<LessonType> {

    @Override
    public int compare(LessonType fst, LessonType snd) {
        return Integer.compare(fst.getId(), snd.getId());
    }
}
