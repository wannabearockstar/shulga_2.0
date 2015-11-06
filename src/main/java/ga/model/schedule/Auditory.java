package ga.model.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import ga.model.config.ScheduleConfig;

import java.util.concurrent.ThreadLocalRandom;

public class Auditory {

    public static final double LEVEL_MULTIPLIER = 3;
    public static final double DEFAULT_DISTANCE = 10;

    @JsonProperty("id")
    private int id;

    @JsonProperty("alias")
    private String name;

    @JsonProperty("lat")
    private double lat;

    @JsonProperty("lon")
    private double lon;

    @JsonProperty("level")
    private int level;

    @JsonProperty("building_id")
    private int buildingId;

    public Auditory() {
    }

    public static Auditory random(final ScheduleConfig config) {
        int idx = ThreadLocalRandom.current().nextInt(0, config.getAuditories().size());
        return config.getAuditories().get(idx);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Auditory setName(String name) {
        this.name = name;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public Auditory setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public Auditory setLon(double lon) {
        this.lon = lon;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public Auditory setLevel(int level) {
        this.level = level;
        return this;
    }

    public boolean hasValidCoordinates() {
        return lat != 0.0 && lon != 0.0;
    }

    public double getDistance(Auditory other) {
        if (!hasValidCoordinates() || !other.hasValidCoordinates()) {
            // todo: implement calculation distance for non valid auditory
            return DEFAULT_DISTANCE;
        }

        double x = lat - other.getLat();
        double y = lon - other.getLon();
        double z = level - other.getLevel();
        return Math.sqrt(x * x + y * y) + LEVEL_MULTIPLIER * Math.abs(z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Auditory)) return false;

        Auditory auditory = (Auditory) o;

        return id == auditory.id;
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
