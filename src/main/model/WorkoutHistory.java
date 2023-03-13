package model;

import java.time.Duration;
import java.util.List;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

/**
 * Represents a history of completed workouts.
 */
public class WorkoutHistory implements Writable {

    private List<Workout> workouts;

    /**
     * Creates a new workout history with an empty list of workouts.
     *
     * REQUIRES: none
     * MODIFIES: this
     * EFFECTS: creates a new workout history with an empty list of workouts
     */
    public WorkoutHistory() {
        workouts = new ArrayList<>();
    }

    /**
     * Adds a completed workout to this history.
     *
     * REQUIRES: workout is not null
     * MODIFIES: this
     * EFFECTS: adds the given workout to this history
     */
    public void addWorkout(Workout workout) {
        workouts.add(workout);
    }

    /**
     * Returns the list of completed workouts in this history.
     *
     * REQUIRES: none
     * MODIFIES: none
     * EFFECTS: returns the list of completed workouts in this history
     */
    public List<Workout> getWorkouts() {
        return workouts;
    }

    /**
     * Returns the total duration of all completed workouts in this history.
     *
     * REQUIRES: none
     * MODIFIES: none
     * EFFECTS: returns the total duration of all completed workouts in this history
     */
    public int getTotalDuration() {
        int totalDuration = 0;

        for (Workout workout : workouts) {
            totalDuration += workout.getDuration();
        }

        return totalDuration;
    }
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("workouts", workoutsToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray workoutsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Workout t : workouts) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}


