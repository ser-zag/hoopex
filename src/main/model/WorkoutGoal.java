package model;

import java.time.Duration;

// Represents a goal for a user's basketball workout history.
public class WorkoutGoal {
    private Duration targetDuration;

    public WorkoutGoal(Duration targetDuration) {
        // Requires: targetDuration is not null.
        // Modifies: this object.
        // Effects: creates a new WorkoutGoal object with the specified target duration.
        this.targetDuration = targetDuration;
    }

    public Duration getTargetDuration() {
        // Effects: returns the target duration of this goal.
        return targetDuration;
    }

    public Duration getRemainingDuration(WorkoutHistory workoutHistory) {
        // Requires: workoutHistory is not null.
        // Effects: returns the amount of workout time remaining to reach this goal.
        Duration totalDuration = workoutHistory.getTotalDuration();
        if (totalDuration.compareTo(targetDuration) >= 0) {
            return Duration.ZERO;
        } else {
            return targetDuration.minus(totalDuration);
        }
    }
}
