package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import model.Workout;
import model.WorkoutHistory;


public class WorkoutHistoryTest {
    private WorkoutHistory workoutHistory;
    private Workout workout1;
    private Workout workout2;

    @BeforeEach
    public void setUp() {
        workoutHistory = new WorkoutHistory();
        workout1 = new Workout("workout1", LocalDate.of(2022, 3, 1), 30);
        workout2 = new Workout("workout2", LocalDate.of(2022, 3, 2), 45);
    }

    @Test
    public void testAddWorkout() {
        workoutHistory.addWorkout(workout1);
        assertEquals(1, workoutHistory.getWorkouts().size());
        assertEquals(workout1, workoutHistory.getWorkouts().get(0));
    }

    @Test
    public void testGetTotalDuration() {
        workoutHistory.addWorkout(workout1);
        workoutHistory.addWorkout(workout2);
        assertEquals(75, workoutHistory.getTotalDuration());
    }

    @Test
    public void testGetWorkouts() {
        workoutHistory.addWorkout(workout1);
        workoutHistory.addWorkout(workout2);
        List<Workout> workouts = workoutHistory.getWorkouts();
        assertEquals(2, workouts.size());
        assertTrue(workouts.contains(workout1));
        assertTrue(workouts.contains(workout2));
    }
}
