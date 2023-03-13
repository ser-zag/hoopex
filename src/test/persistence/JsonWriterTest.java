package persistence;

import model.Workout;
import model.WorkoutHistory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            WorkoutHistory wr = new WorkoutHistory();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            WorkoutHistory wr = new WorkoutHistory();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            wr = reader.read();
            assertEquals(0, wr.getWorkouts().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            WorkoutHistory wr = new WorkoutHistory();
            wr.addWorkout(new Workout("zorin", LocalDate.parse("2000-01-01"), 100));
            wr.addWorkout(new Workout("zorin1", LocalDate.parse("2001-01-01"), 101));;
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            wr = reader.read();
            List<Workout> workouts = wr.getWorkouts();
            assertEquals(2, workouts.size());
            checkWorkout("zorin", LocalDate.parse("2000-01-01"), 100, workouts.get(0));
            checkWorkout("zorin1", LocalDate.parse("2001-01-01"), 101, workouts.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
