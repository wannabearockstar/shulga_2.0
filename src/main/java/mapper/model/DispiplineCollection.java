package mapper.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import ga.model.schedule.Discipline;

import java.util.List;

/**
 * Created by wannabe on 07.11.15.
 */
public class DispiplineCollection {
    @JsonProperty("disciplines")
    private List<Discipline> disciplines;

    public DispiplineCollection() {
    }

    public DispiplineCollection(List<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    public List<Discipline> getDisciplines() {
        return disciplines;
    }

    public DispiplineCollection setDisciplines(List<Discipline> disciplines) {
        this.disciplines = disciplines;
        return this;
    }
}
