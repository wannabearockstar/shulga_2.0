package mapper.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import ga.model.schedule.Auditory;

import java.util.List;

/**
 * Created by wannabe on 06.11.15.
 */
public class AuditoryCollection {
    @JsonProperty("auditories")
    private List<Auditory> auditories;

    public AuditoryCollection(List<Auditory> auditories) {
        this.auditories = auditories;
    }

    public AuditoryCollection() {
    }

    public List<Auditory> getAuditories() {
        return auditories;
    }

    public AuditoryCollection setAuditories(List<Auditory> auditories) {
        this.auditories = auditories;
        return this;
    }
}
