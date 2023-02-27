package model;

import java.time.Duration;
import java.time.LocalDate;

// Represents a completed basketball workout, including the date, duration, and notes about the workout.

public class Workout {
    private LocalDate date;
    private Duration duration;
    private String notes;

    public Workout(LocalDate date, Duration duration, String notes) {
        // Requires: date is not null, duration is not null, notes is not null.
        // Modifies: this object.
        // Effects: creates a new Workout object with the specified date, duration, and notes.
        this.date = date;
        this.duration = duration;
        this.notes = notes;
    }

    public LocalDate getDate() {
        // Effects: returns the date of this workout.
        return date;
    }

    public Duration getDuration() {
        // Effects: returns the duration of this workout.
        return duration;
    }

    public String getNotes() {
        // Effects: returns the notes of this workout.
        return notes;
    }
}
