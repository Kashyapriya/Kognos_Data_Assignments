package org.example.priya_kashyap_assignment_4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;


import java.io.File;
import java.util.List;

public class DataPlotterApp extends Application {

    private LineChart<Number, Number> lineChart; // This will show our plot
    private NumberAxis xAxis; // X-axis for inclination
    private NumberAxis yAxis; // Y-axis for depth
    private DataExtractor dataExtractor = new DataExtractor(); // Class to read data from file

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Data Plotter"); // Window title

        // Create the X and Y Axis
        xAxis = new NumberAxis(0, 90, 10); // X-axis ranges from 0 to 90,
        xAxis.setLabel("Inclination"); // Label for X-axis

        yAxis = new NumberAxis(); // Y-axis will be set later
        yAxis.setLabel("Depth"); // Label for Y-axis

        // Create the LineChart
        lineChart = new LineChart<>(xAxis, yAxis); // Line chart with X and Y axis
        lineChart.setTitle("Depth vs. Inclination"); // Title of the chart

        // File chooser to select the text file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Data File"); // Title for the file chooser

        // Create UI components for changing axis ranges
        Label xAxisMinLabel = new Label("X Min:"); // Label for minimum X value
        TextField xAxisMinField = new TextField("0"); // Field to enter minimum X value
        Label xAxisMaxLabel = new Label("X Max:"); // Label for maximum X value
        TextField xAxisMaxField = new TextField("90"); // Field to enter maximum X value

        Label yAxisMinLabel = new Label("Y Min:"); // Label for minimum Y value
        TextField yAxisMinField = new TextField("0"); // Field to enter minimum Y value
        Label yAxisMaxLabel = new Label("Y Max:"); // Label for maximum Y value
        TextField yAxisMaxField = new TextField("400"); // Field to enter maximum Y value

        Button applyButton = new Button("Apply"); // Button to apply new axis ranges
        Button loadFileButton = new Button("Load File"); // Button to load data from file

        // Set actions for buttons
        applyButton.setOnAction(e -> updateAxisRanges(
                Integer.parseInt(xAxisMinField.getText()), // Get min X value
                Integer.parseInt(xAxisMaxField.getText()), // Get max X value
                Integer.parseInt(yAxisMinField.getText()), // Get min Y value
                Integer.parseInt(yAxisMaxField.getText())  // Get max Y value
        ));

        loadFileButton.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(primaryStage); // Open file chooser dialog
            if (file != null) {
                List<DataExtractor.DataRow> dataRows = dataExtractor.readDataFromFile(file); // Read data from selected file
                plotData(dataRows); // Plot the data
            }
        });

        // Arrange UI components in a horizontal box
        HBox controlBox = new HBox(5, xAxisMinLabel, xAxisMinField, xAxisMaxLabel, xAxisMaxField,
                yAxisMinLabel, yAxisMinField, yAxisMaxLabel, yAxisMaxField, applyButton, loadFileButton);

        // Set up the layout of the application
        BorderPane root = new BorderPane();
        root.setTop(controlBox); // Put the control box at the top
        root.setCenter(lineChart); // Put the line chart in the center

        // Create the scene and set it on the stage
        Scene scene = new Scene(root, 800, 600); // Scene size 800x600
        primaryStage.setScene(scene); // Set the scene for the stage
        primaryStage.show(); // Show the stage
    }

    // Method to update the axis ranges
    private void updateAxisRanges(int xMin, int xMax, int yMin, int yMax) {
        xAxis.setLowerBound(xMin); // Set minimum X value
        xAxis.setUpperBound(xMax); // Set maximum X value
        yAxis.setLowerBound(yMax); // Set maximum Y value (depth axis is reversed)
        yAxis.setUpperBound(yMin); // Set minimum Y value
    }

    // Method to plot data on the chart
    private void plotData(List<DataExtractor.DataRow> dataRows) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Depth-Inclination"); // Name of the data series

        for (DataExtractor.DataRow row : dataRows) {
            series.getData().add(new XYChart.Data<>(row.getInclination(), row.getDepth())); // Add data points
        }

        lineChart.getData().clear(); // Remove any existing data from the chart
        lineChart.getData().add(series); // Add the new data series
    }

    // Main method to launch the application
    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}

