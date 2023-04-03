package ui;

import model.Workout;
import model.WorkoutHistory;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

public class WorkoutTrackerGUI extends JFrame {

    private static final String JSON_STORE = "./data/workouthistory.json";

    private WorkoutHistory workoutHistory;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JTextArea workoutHistoryArea;

    public WorkoutTrackerGUI() {
        super("Basketball Workout Tracker");
        workoutHistory = new WorkoutHistory();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        initializeComponents();
    }

    @SuppressWarnings("methodlength")
    private void initializeComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem loadMenuItem = new JMenuItem("Load workout history");
        fileMenu.add(loadMenuItem);
        loadMenuItem.addActionListener(new LoadWorkoutHistoryAction());

        JMenuItem saveMenuItem = new JMenuItem("Save workout history");
        fileMenu.add(saveMenuItem);
        saveMenuItem.addActionListener(new SaveWorkoutHistoryAction());

        JMenu trackMenu = new JMenu("Track");
        menuBar.add(trackMenu);

        JMenuItem progressMenuItem = new JMenuItem("Track progress");
        trackMenu.add(progressMenuItem);
        progressMenuItem.addActionListener(new TrackProgressAction());

        JMenuItem goalMenuItem = new JMenuItem("Track goal");
        trackMenu.add(goalMenuItem);
        goalMenuItem.addActionListener(new TrackGoalAction());

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        workoutHistoryArea = new JTextArea();
        workoutHistoryArea.setEditable(false);
        mainPanel.add(new JScrollPane(workoutHistoryArea), BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel(new FlowLayout());
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        JButton addWorkoutButton = new JButton("Add Workout");
        bottomPanel.add(addWorkoutButton);
        addWorkoutButton.addActionListener(new AddWorkoutAction());

        setVisible(true);
    }

    private void updateWorkoutHistoryArea() {
        StringBuilder sb = new StringBuilder();
        for (Workout workout : workoutHistory.getWorkouts()) {
            sb.append(workout.toString()).append("\n");
        }
        workoutHistoryArea.setText(sb.toString());
    }

    private class AddWorkoutAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = JOptionPane.showInputDialog("Enter workout name:");
            String dateStr = JOptionPane.showInputDialog("Enter workout date (yyyy-mm-dd):");
            LocalDate date = LocalDate.parse(dateStr);
            int duration = Integer.parseInt(JOptionPane.showInputDialog("Enter workout duration (in minutes):"));

            Workout workout = new Workout(name, date, duration);
            workoutHistory.addWorkout(workout);
            updateWorkoutHistoryArea();
        }
    }

    private class LoadWorkoutHistoryAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadWorkoutHistory();
        }
    }

    private class SaveWorkoutHistoryAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveWorkoutHistory();
        }
    }

    private class TrackProgressAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String message = "Total workout duration: " + workoutHistory.getTotalDuration() + " minutes.\n";
            JOptionPane.showMessageDialog(null, message,
                    "Progress Report", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class TrackGoalAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int goalDuration = Integer.parseInt(JOptionPane.showInputDialog("Enter your weekly"
                    +
                    " workout duration goal (in minutes):"));
            int progressDuration = workoutHistory.getTotalDuration();
            String message;
            if (progressDuration >= goalDuration) {
                message = "Congratulations! You have reached your weekly workout goal.\n";
            } else {
                message = "Keep going! You are " + (goalDuration - progressDuration)
                        +
                        " minutes away from your weekly workout goal.\n";
            }
            JOptionPane.showMessageDialog(null, message,
                    "Goal Tracker", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void loadWorkoutHistory() {
        try {
            workoutHistory = jsonReader.read();
            updateWorkoutHistoryArea();
            JOptionPane.showMessageDialog(null, "Loaded workout history from "
                    +
                    JSON_STORE, "Load", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading workout history: "
                    +
                    e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveWorkoutHistory() {
        try {
            jsonWriter.open();
            jsonWriter.write(workoutHistory);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, "Saved workout history to " + JSON_STORE,
                    "Save", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error saving workout history: "
                    +
                    e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new WorkoutTrackerGUI();
    }
}