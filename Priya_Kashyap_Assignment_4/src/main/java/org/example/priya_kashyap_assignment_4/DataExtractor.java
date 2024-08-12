package org.example.priya_kashyap_assignment_4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataExtractor {

    // Method to read data from a file and return a list of DataRow objects
    public List<DataRow> readDataFromFile(File file) {
        List<DataRow> dataList = new ArrayList<>(); // List to store the extracted data

        // Try-with-resources to automatically close the BufferedReader
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            // Read each line from the file
            while ((line = br.readLine()) != null) {
                // Split the line by commas
                String[] values = line.split(",");
                // Check if the line has at least 2 values
                if (values.length < 2) {
                    System.err.println("Skipping line due to insufficient data: " + line);
                    continue; // Skip this line if there isn't enough data
                }
                try {
                    // Parse the depth and inclination from the line
                    double depth = Double.parseDouble(values[0].trim());
                    double inclination = Double.parseDouble(values[1].trim());
                    // Create a DataRow object and add it to the list
                    dataList.add(new DataRow(depth, inclination));
                } catch (NumberFormatException e) {
                    // Handle the case where the number format is incorrect
                    System.err.println("Skipping line due to invalid number format: " + line);
                }
            }
        } catch (IOException e) {
            // Handle the IO exceptions that occur while reading the file
            e.printStackTrace();
        }
        return dataList; // Return the list of data rows
    }

    // Inner class to represent a single data row with depth and inclination
    public static class DataRow {
        private final double depth;
        private final double inclination;

        public DataRow(double depth, double inclination) {
            this.depth = depth;
            this.inclination = inclination;
        }

        public double getDepth() {
            return depth; // Getter for depth
        }

        public double getInclination() {
            return inclination; // Getter for inclination
        }
    }
}



