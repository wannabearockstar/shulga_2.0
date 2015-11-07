package ga.model.bound;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class BoundCollection {

    @JsonProperty("auditoria_times")
    private Map<Integer, TimeBound> auditoriaTimes = new HashMap<>();

    @JsonProperty("discipline_times")
    private Map<Integer, TimeBound> disciplineTimes = new HashMap<>();

    @JsonProperty("discipline_auditories")
    private Map<Integer, ValueBound> disciplineAuditories = new HashMap<>();

    public static BoundCollection random() {
        BoundCollection collection = new BoundCollection();
        collection.auditoriaTimes = new HashMap<>();
        collection.disciplineTimes = new HashMap<>();
        collection.disciplineAuditories = new HashMap<>();
        return collection;
    }

    public Map<Integer, TimeBound> getAuditoriaTimes() {
        return auditoriaTimes;
    }

    public Map<Integer, TimeBound> getDisciplineTimes() {
        return disciplineTimes;
    }

    public Map<Integer, ValueBound> getDisciplineAuditories() {
        return disciplineAuditories;
    }

}
