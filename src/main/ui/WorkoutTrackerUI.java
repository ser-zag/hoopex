package ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import model.Workout;
import model.WorkoutHistory;


import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

public class WorkoutTrackerUI {

    private static final String JSON_STORE = "./data/workouthistory.json";

    private WorkoutHistory workoutHistory;
    private Scanner scanner;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public WorkoutTrackerUI() {
        workoutHistory = new WorkoutHistory();
        scanner = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    @SuppressWarnings("methodlength")
    public void start() {
        int choice = 0;

        do {
            printMenu();
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addWorkout();
                    break;
                case 2:
                    viewWorkoutHistory();
                    break;
                case 3:
                    trackProgress();
                    break;
                case 4:
                    saveWorkOutHistory();
                    break;
                case 5:
                    loadWorkOutHistory();
                    break;
                case 6:
                    trackGoal();
                    break;
                case 7:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

        } while (choice != 7);
    }

    private void printMenu() {
        System.out.println("Please choose an option:");
        System.out.println("1. Add completed workout");
        System.out.println("2. View workout history");
        System.out.println("3. Track progress");
        System.out.println("4. Save progress");
        System.out.println("5. Load workout history");
        System.out.println("6. Track goal");
        System.out.println("7. Exit program");
    }

    private void addWorkout() {
        System.out.println("Enter workout name:");
        String name = scanner.next();

        System.out.println("Enter workout date (yyyy-mm-dd):");
        String dateStr = scanner.next();
        LocalDate date = LocalDate.parse(dateStr);

        System.out.println("Enter workout duration (in minutes):");
        int duration = scanner.nextInt();

        Workout workout = new Workout(name, date, duration);
        workoutHistory.addWorkout(workout);

        System.out.println("Workout added to history.");
    }

    private void viewWorkoutHistory() {
        List<Workout> workouts = workoutHistory.getWorkouts();

        if (workouts.isEmpty()) {
            System.out.println("No workouts found.");
            return;
        }

        System.out.println("Workout history:");

        for (Workout workout : workouts) {
            System.out.println(workout);
        }
    }

    private void trackProgress() {
        System.out.println("Total workout duration: " + workoutHistory.getTotalDuration() + " minutes");
    }

    private void trackGoal() {
        System.out.println("Enter goal duration (in minutes):");
        int goal = scanner.nextInt();

        int remaining = goal - workoutHistory.getTotalDuration();

        if (remaining > 0) {
            System.out.println("You are " + remaining + " minutes away from your goal.");
        } else {
            System.out.println("Congratulations, you have reached your goal!");
        }
    }

    // EFFECTS: saves the workout history to file
    private void saveWorkOutHistory() {
        try {
            jsonWriter.open();
            jsonWriter.write(workoutHistory);
            jsonWriter.close();
            System.out.println("Saved workout history to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workout history from file
    private void loadWorkOutHistory() {
        try {
            workoutHistory = jsonReader.read();
            System.out.println("Loaded workout history from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}