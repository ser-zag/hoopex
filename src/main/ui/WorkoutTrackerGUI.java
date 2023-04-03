package ui;

import model.Workout;
import model.WorkoutHistory;
import persistence.JsonReader;
import persistence.JsonWriter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Day;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;

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
        URL imageUrl = getClass().getResource("/tobs.jpg");
        ImageIcon icon = new ImageIcon(imageUrl);
        Image backgroundImage = icon.getImage();

        // Replace the existing `initializeComponents()` call with the one that takes the background image
        initializeComponents(backgroundImage);
    }
    // MODIFIES: this
    // EFFECTS: initializes components and background image

    @SuppressWarnings("methodlength")
    private void initializeComponents(Image backgroundImage) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
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
        JPanel mainPanel = new ImagePanel(backgroundImage);
        mainPanel.setLayout(new BorderLayout());
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

    // MODIFIES: this
    // EFFECTS: updates w/o area

    private void updateWorkoutHistoryArea() {
        StringBuilder sb = new StringBuilder();
        for (Workout workout : workoutHistory.getWorkouts()) {
            sb.append(workout.toString()).append("\n");
        }
        workoutHistoryArea.setText(sb.toString());
    }

    // MODIFIES: this
    // EFFECTS: adds w/o action

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

    // MODIFIES: this
    // EFFECTS: loads w/o

    private class LoadWorkoutHistoryAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadWorkoutHistory();
        }
    }

    // MODIFIES: this
    // EFFECTS: saves w/o

    private class SaveWorkoutHistoryAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveWorkoutHistory();
        }
    }


    // MODIFIES: this
    // EFFECTS: opens a graph menu

    private class TrackProgressAction implements ActionListener {
        @Override
        @SuppressWarnings("methodlength")
        public void actionPerformed(ActionEvent e) {
            TimeSeriesCollection dataset = new TimeSeriesCollection();
            TimeSeries series = new TimeSeries("Workout Duration");

            for (Workout workout : workoutHistory.getWorkouts()) {
                // Convert LocalDate to java.util.Date
                Date date = java.sql.Date.valueOf(workout.getDate());
                series.add(new Day(date), workout.getDuration());
            }
            dataset.addSeries(series);

            JFreeChart chart = ChartFactory.createTimeSeriesChart(
                    "Workout Duration vs Date",
                    "Date",
                    "Workout Duration (minutes)",
                    dataset,
                    true,
                    true,
                    false
            );

            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(560, 367));

            JFrame chartFrame = new JFrame("Workout Progress");
            chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            chartFrame.setLocationRelativeTo(null);
            chartFrame.getContentPane().add(chartPanel);
            chartFrame.pack();
            chartFrame.setVisible(true);
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

    // MODIFIES: this
    // EFFECTS: loads w/o

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

    // MODIFIES: this
    // EFFECTS: saves w/o

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