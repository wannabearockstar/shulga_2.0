package ga.model.schedule.time;

import ga.model.config.ScheduleConfig;

import java.util.concurrent.ThreadLocalRandom;

public class TimeMark {
    
    private DayTime dayTime;
    private WeekDay weekDay;

    public TimeMark(WeekDay weekDay, DayTime dayTime) {
        this.weekDay = weekDay;
        this.dayTime = dayTime;
    }

    public static TimeMark random(ScheduleConfig config) {
        int dayIdx = ThreadLocalRandom.current().nextInt(0, config.getWeekDays().size());
        int timeIdx = ThreadLocalRandom.current().nextInt(0, config.getTimes().size());
        return new TimeMark(
                config.getWeekDays().get(dayIdx),
                config.getTimes().get(timeIdx)
        );
    }

    public DayTime getDayTime() {
        return dayTime;
    }

    public TimeMark setDayTime(DayTime dayTime) {
        this.dayTime = dayTime;
        return this;
    }

    public WeekDay getWeekDay() {
        return weekDay;
    }

    public TimeMark setWeekDay(WeekDay weekDay) {
        this.weekDay = weekDay;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeMark)) return false;

        TimeMark timeMark = (TimeMark) o;

        if (!dayTime.equals(timeMark.dayTime)) return false;
        return weekDay == timeMark.weekDay;
    }

    @Override
    public int hashCode() {
        int result = dayTime.hashCode();
        result = 31 * result + weekDay.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return weekDay + " " + dayTime;
    }
}
