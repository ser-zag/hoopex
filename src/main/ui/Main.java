package ui;

import model.Event;
import model.EventLog;

public class Main {
    public static void main(String[] args) {
        WorkoutTrackerUI ui = new WorkoutTrackerUI();
        ui.start();
        printAllEvents();
    }

    private static void printAllEvents() {
        System.out.println("\nEvents logged during this session:");
        for (Event event : EventLog.getInstance().getAllEvents()) {
            System.out.println(event);
        }
    }
}
