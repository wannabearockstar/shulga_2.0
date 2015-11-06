package mapper.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import ga.model.schedule.Group;

import java.util.List;

public class GroupCollection {

    @JsonProperty("groups")
    private List<Group> groups;

    public GroupCollection(List<Group> groups) {
        this.groups = groups;
    }

    public GroupCollection() {
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
