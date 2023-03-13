package model;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.json.JSONObject;
import persistence.Writable;

/**
 * Represents a workout with a name, date, and duration.
 */
public class Workout implements Writable {

    private String name;
    private LocalDate date;
    private int duration;

    /**
     * Creates a new workout with the given name, date, and duration.
     *
     * REQUIRES: duration is non-negative
     * MODIFIES: this
     * EFFECTS: name, date, and duration are set for the new workout
     */
    public Workout(String name, LocalDate date, int duration) {
        this.name = name;
        this.date = date;
        this.duration = duration;
    }

    /**
     * Returns the name of this workout.
     *
     * REQUIRES: none
     * MODIFIES: none
     * EFFECTS: returns the name of this workout
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the date of this workout.
     *
     * REQUIRES: none
     * MODIFIES: none
     * EFFECTS: returns the date of this workout
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns the duration of this workout.
     *
     * REQUIRES: none
     * MODIFIES: none
     * EFFECTS: returns the duration of this workout
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Returns a string representation of this workout.
     *
     * REQUIRES: none
     * MODIFIES: none
     * EFFECTS: returns a string representation of this workout
     */
    @Override
    public String toString() {
        return String.format("%s (%s): %d minutes", name, date, duration);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("date", date);
        json.put("duration", duration);
        return json;
    }
}