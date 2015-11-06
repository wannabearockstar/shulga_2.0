package ga.model.comparator;

import ga.model.schedule.time.TimeMark;

import java.util.Comparator;

public class TimeMarkComparator implements Comparator<TimeMark> {

    @Override
    public int compare(TimeMark fst, TimeMark snd) {
        int res = new WeekDayComparator().compare(fst.getWeekDay(), snd.getWeekDay());

        if (res == 0)
            res = new DayTimeComparator().compare(fst.getDayTime(), snd.getDayTime());

        return res;
    }
}