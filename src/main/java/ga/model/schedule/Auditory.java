package ga.model.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import ga.model.config.ScheduleConfig;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

public class Auditory {

    public static final double LEVEL_MULTIPLIER = 3;
    public static final double DEFAULT_DISTANCE = 10;
    public static final Pattern namePattern = Pattern.compile("^\\w\\d{3}$");

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

    public boolean isNameValidForCampus() {
        return namePattern.matcher(name).matches();
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
            if (isNameValidForCampus() && other.isNameValidForCampus()) {
                return getDistanceByName(other);
            }
            return DEFAULT_DISTANCE;
        }

        double x = (lat - other.getLat()) * 10000;
        double y = (lon - other.getLon()) * 10000;
        double z = level - other.getLevel() * 10;
        return Math.sqrt(x * x + y * y) + LEVEL_MULTIPLIER * Math.abs(z);
    }

    private double getDistanceByName(Auditory other) {

        return Math.abs(getLetterAlphabetOrder(name.charAt(0)) - other.getLetterAlphabetOrder(other.getName().charAt(0))) + Math.abs(Integer.parseInt(name.substring(1)) - Integer.parseInt(other.getName().substring(1)));
    }

    private int getLetterAlphabetOrder(char c) {
        int temp = (int) c;
        int temp_integer = 64; //for upper case
        if (temp <= 90 & temp >= 65) {
            return temp - temp_integer;
        }
        return 0;
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
