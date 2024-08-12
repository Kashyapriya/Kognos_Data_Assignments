package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MudWeightProcessor {

    public static void main(String[] args) {
        // Define the input and output file paths
        String inputFile = "src/main/resources/Mud_Weight_Converted.csv";
        String outputFile = "Mud_Weight_PI.csv";

        try {
            // Create a list to store the processed records
            List<String[]> recordsWithPi = new ArrayList<>();

            // Read the CSV file
            try (FileReader reader = new FileReader(inputFile)) {
                // Parse the CSV file and treat the first row as header
                @SuppressWarnings("deprecation")
				Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
                
                // Loop through each record in the CSV file
                for (CSVRecord record : records) {
                    // Get the value of the "Mud Weight (kg/m3)" column
                    String mudWeightStr = record.get("Mud Weight (kg/m3)");
                    
                    // Convert the mud weight from string to double
                    double mudWeight = Double.parseDouble(mudWeightStr);
                    
                    // Calculate mud weight multiplied by PI
                    double mudWeightPi = mudWeight * Math.PI;

                    // Add the depth and calculated value to the list of processed records
                    recordsWithPi.add(new String[]{record.get("Depth"), String.valueOf(mudWeightPi)});
                }
            }

            // Write the processed data to a new CSV file
            try (FileWriter writer = new FileWriter(outputFile)) {
                // Write the header row to the new CSV file
                writer.append("Depth,Mud Weight (kg/m3) * PI\n");
                
                // Write each processed record to the CSV file
                for (String[] record : recordsWithPi) {
                    writer.append(String.join(",", record)).append("\n");
                }
            }

            // Print a message to indicate the processing is complete
            System.out.println("Processing completed. Data saved to " + outputFile);

        } catch (IOException e) {
            // Print any errors that occur during file reading/writing
            e.printStackTrace();
        }
    }

    // This method is not used but could be used to process the mud weight data
    public static void processMudWeight(String inputFile, String outputFile) {
    }
}



