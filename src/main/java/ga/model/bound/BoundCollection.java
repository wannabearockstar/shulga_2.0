package ga.model.bound;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class BoundCollection {

    @JsonProperty("auditory_times")
    private Map<Integer, TimeBound> auditoryTimes;

    @JsonProperty("discipline_times")
    private Map<Integer, TimeBound> disciplineTimes;

    @JsonProperty("discipline_auditories")
    private Map<Integer, ValueBound> disciplineAuditories;

    public static BoundCollection random() {
        BoundCollection collection = new BoundCollection();
        collection.auditoryTimes = new HashMap<>();
        collection.disciplineTimes = new HashMap<>();
        collection.disciplineAuditories = new HashMap<>();
        return collection;
    }

    public Map<Integer, TimeBound> getAuditoryTimes() {
        return auditoryTimes;
    }

    public Map<Integer, TimeBound> getDisciplineTimes() {
        return disciplineTimes;
    }

    public Map<Integer, ValueBound> getDisciplineAuditories() {
        return disciplineAuditories;
    }

}
