package ga.model.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LessonType {

    @JsonProperty("id")
    private int id;

    @JsonProperty("alias")
    private String name;

    public LessonType() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LessonType)) return false;

        LessonType that = (LessonType) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
