package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;




import model.Workout;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class WorkoutTest {
    private Workout workout;

    @BeforeEach
    public void setUp() {
        workout = new Workout("workout1", LocalDate.of(2022, 3, 1), 30);
    }

    @Test
    public void testGetName() {
        assertEquals("workout1", workout.getName());
    }


    @Test
    public void testGetDate() {
        assertEquals(LocalDate.of(2022, 3, 1), workout.getDate());
    }



    @Test
    public void testGetDuration() {
        assertEquals(30, workout.getDuration());
    }


    @Test
    public void testToString() {
        String expectedString = "workout1 (2022-03-01): 30 minutes";
        assertEquals(expectedString, workout.toString());
    }
}