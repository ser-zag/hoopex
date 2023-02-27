package model;

import java.time.Duration;
import java.util.List;
import java.util.ArrayList;

// Represents a history of completed basketball workouts.
public class WorkoutHistory {
    private List<Workout> workouts;

    public WorkoutHistory() {
        // Modifies: this object.
        // Effects: creates a new, empty WorkoutHistory object.
        this.workouts = new ArrayList<>();
    }

    public void addWorkout(Workout workout) {
        // Requires: workout is not null.
        // Modifies: this object.
        // Effects: adds the specified workout to this workout history.
        workouts.add(workout);
    }

    public List<Workout> getWorkouts() {
        // Effects: returns a list of all workouts in this workout history.
        return workouts;
    }

    public Duration getTotalDuration() {
        // Effects: returns the total duration of all workouts in this workout history.
        return workouts.stream().map(Workout::getDuration).reduce(Duration.ZERO, Duration::plus);
    }

}
