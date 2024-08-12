package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// Class to handle mud weight data
public class MudWeightData {

    private List<DataPoint> dataPoints;  // List to store data points

    // Constructor to initialize the data points list
    public MudWeightData() {
        dataPoints = new ArrayList<>();
    }

    // Method to load data from a CSV file
    public void loadFromFile() throws IOException, URISyntaxException {
        String line;  // Variable to store each line read from the file
        String csvSplitBy = ",";  // comma separated values

        // Read the CSV file from the resources folder
        try (BufferedReader br = Files.newBufferedReader(Paths.get(getClass().getClassLoader()
                .getResource("Mud_Weight.csv").toURI()))) {
            // Skip the header line of the CSV file
            br.readLine();

            // Read each line of the CSV file
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);  // Split the line into parts

                // Check if both values are numeric before parsing them
                if (isNumeric(data[0]) && isNumeric(data[1])) {
                    double depth = Double.parseDouble(data[0]);  // Convert depth value to double
                    double weight = Double.parseDouble(data[1]);  // Convert weight value to double
                    dataPoints.add(new DataPoint(depth, weight));  // Add new DataPoint to the list
                }
            }
        }
    }

    // Method to check if a string is numeric
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);  // Try to parse the string as a double
            return true;  // Return true if successful
        } catch (NumberFormatException e) {
            return false;  // Return false if parsing fails
        }
    }

    // Method to get the list of data points
    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }

    // Inner class to hold individual data points
    public static class DataPoint {
        private double depth;  // Depth value
        private double weight;  // Weight value

        // Constructor to initialize a DataPoint object
        public DataPoint(double depth, double weight) {
            this.depth = depth;
            this.weight = weight;
        }

        // Getter method for depth
        public double getDepth() {
            return depth;
        }

        // Getter method for weight
        public double getWeight() {
            return weight;
        }
    }
}



