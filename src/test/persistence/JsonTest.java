package persistence;

import model.Workout;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkWorkout(String name, LocalDate date, int duration, Workout workout) {
        assertEquals(name, workout.getName());
        assertEquals(date, workout.getDate());
        assertEquals(duration, workout.getDuration());
    }
}