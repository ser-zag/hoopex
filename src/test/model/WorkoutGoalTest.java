package model;

// The main entry point for the application.
public class WorkoutGoalTest {
    public static void main(String[] args) {
        // Effects: displays the main user interface for the application.
        WorkoutHistory workoutHistory = new WorkoutHistory();
        WorkoutGoal workoutGoal = new WorkoutGoal(Duration.ofHours(100));
        WorkoutTrackerUI workoutTrackerUI = new WorkoutTrackerUI(workoutHistory, workoutGoal);
        workoutTrackerUI.display();
    }
}
