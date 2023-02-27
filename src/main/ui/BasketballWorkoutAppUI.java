package ui;

import java.util.Scanner;
import Model.*;

public class BasketballWorkoutAppUI {
    private BasketballWorkoutApp app;
    private Scanner scanner;

    public BasketballWorkoutAppUI() {
        app = new BasketballWorkoutApp();
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to the Basketball Workout App!");

        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Please choose an option:");
            System.out.println("1. Add completed basketball workout");
            System.out.println("2. View workout history");
            System.out.println("3. Track workout progress");
            System.out.println("4. Track progress towards goals");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addWorkout();
                    break;
                case 2:
                    viewWorkoutHistory();
                    break;
                case 3:
                    trackWorkoutProgress();
                    break;
                case 4:
                    trackProgressTowardsGoals();
                    break;
                case 5:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void addWorkout() {
        System.out.println("Please enter the details of your completed basketball workout:");

        // get workout details from user input
        // create a new Workout object
        // add the Workout to the app
    }

    private void viewWorkoutHistory() {
        System.out.println("Your workout history:");

        // get workout history from the app
        // display workout history to the user
    }

    private void trackWorkoutProgress() {
        System.out.println("Your workout progress:");

        // get workout progress from the app
        // display workout progress to the user
    }

    private void trackProgressTowardsGoals() {
        System.out.println("Your progress towards your goals:");

        // get progress towards goals from the app
        // display progress towards goals to the user
    }
}