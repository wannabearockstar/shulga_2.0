package ga.model.bound;

import com.fasterxml.jackson.annotation.JsonProperty;
import ga.model.schedule.time.TimeMark;

import java.util.List;

public class TimeBound {

    @JsonProperty("days")
    private List<Integer> days;

    @JsonProperty("times")
    private List<Integer> times;

    public List<Integer> getDays() {
        return days;
    }

    public void setDays(List<Integer> days) {
        this.days = days;
    }

    public List<Integer> getTimes() {
        return times;
    }

    public void setTimes(List<Integer> times) {
        this.times = times;
    }

    public boolean validate(TimeMark mark) {
        if (!days.isEmpty() && days.indexOf(mark.getWeekDay().getDay()) == -1)
            return false;

        if (!times.isEmpty() && times.indexOf(mark.getDayTime().getId()) == -1)
            return false;

        return true;
    }
}
