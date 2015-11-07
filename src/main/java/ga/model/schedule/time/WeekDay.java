package ga.model.schedule.time;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum  WeekDay {

    UNKNOWN(0),

    MONDAY(1),

    TUESDAY(2),

    WEDNESDAY(3),

    THURSDAY(4),

    FRIDAY(5),

    SATURDAY(6),

    SUNDAY(7);

    public static final Map<WeekDay, String> locale;
    private static final Map<Integer, WeekDay> LOOKUP = new HashMap<>(values().length);

    static {
        for (WeekDay w : values()) {
            LOOKUP.put(w.getDay(), w);
        }

        locale = new HashMap<>();
        locale.put(UNKNOWN, "Неизвестно");
        locale.put(MONDAY, "Понедельник");
        locale.put(TUESDAY, "Вторник");
        locale.put(WEDNESDAY, "Среда");
        locale.put(THURSDAY, "Четверг");
        locale.put(FRIDAY, "Пятница");
        locale.put(SATURDAY, "Суббота");
        locale.put(SUNDAY, "Воскресенье");
    }

    private int day;

    WeekDay(int day) {
        this.day = day;
    }

    @JsonCreator
    public static WeekDay get(int day) {
        if (!LOOKUP.containsKey(day)) {
            return UNKNOWN;
        }

        return LOOKUP.get(day);
    }

    @JsonValue
    public int getDay() {
        return day;
    }

    public boolean isWeekend() {
        return this == SATURDAY || this == SUNDAY;
    }
}
