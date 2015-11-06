package ga.model.comparator;

import ga.model.schedule.time.WeekDay;

import java.util.Comparator;

public class WeekDayComparator implements Comparator<WeekDay> {

    @Override
    public int compare(WeekDay fst, WeekDay snd) {
        return Integer.compare(fst.getDay(), snd.getDay());
    }
}