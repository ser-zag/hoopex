package ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import model.Workout;
import model.WorkoutHistory;

public class WorkoutTrackerUI {

    private WorkoutHistory workoutHistory;
    private Scanner scanner;

    public WorkoutTrackerUI() {
        workoutHistory = new WorkoutHistory();
        scanner = new Scanner(System.in);
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
                    trackGoal();
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

        } while (choice != 5);
    }

    private void printMenu() {
        System.out.println("Please choose an option:");
        System.out.println("1. Add completed workout");
        System.out.println("2. View workout history");
        System.out.println("3. Track progress");
        System.out.println("4. Track goal");
        System.out.println("5. Exit program");
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
}