package persistence;

import model.Workout;
import model.WorkoutHistory;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;

import org.json.*;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WorkoutHistory read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkoutHistory(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workout history from JSON object and returns it
    private WorkoutHistory parseWorkoutHistory(JSONObject jsonObject) {
        WorkoutHistory wo = new WorkoutHistory();
        addWorkouts(wo, jsonObject);
        return wo;
    }

    // MODIFIES: wo
    // EFFECTS: parses workouts from JSON object and adds them to workroom
    private void addWorkouts(WorkoutHistory wo, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("workouts");
        for (Object json : jsonArray) {
            JSONObject nextWorkout = (JSONObject) json;
            addWorkout(wo, nextWorkout);
        }
    }

    // MODIFIES: wo
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addWorkout(WorkoutHistory wo, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        LocalDate date = LocalDate.parse(jsonObject.getString("date"));
        int duration = jsonObject.getInt("duration");
        Workout workout = new Workout(name, date, duration);
        wo.addWorkout(workout);
    }
}
