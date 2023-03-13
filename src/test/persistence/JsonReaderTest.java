package persistence;


import model.Workout;
import model.WorkoutHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            WorkoutHistory wr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkoutHistory() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkoutHistory.json");
        try {
            WorkoutHistory wr = reader.read();
            assertEquals(0, wr.getWorkouts().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkoutHistory() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkoutHistory.json");
        try {
            WorkoutHistory wr = reader.read();
            List<Workout> workouts = wr.getWorkouts();
            assertEquals(2, workouts.size());
            checkWorkout("zorin", LocalDate.parse("2000-01-01"), 100, workouts.get(0));
            checkWorkout("zorin1", LocalDate.parse("2001-01-01"), 101, workouts.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
