package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MudWeightConverter {
    // Conversion factor from lbm/galUS to kg/m3
    private static final double CONVERSION_FACTOR = 119.8264;

    public static void main(String[] args) throws URISyntaxException {
        // Get the path of the input CSV file from resources folder
        String inputFilePath = Paths.get(MudWeightConverter.class.getClassLoader()
                .getResource("Mud_Weight.csv").toURI()).toString();

        // Define the path for the output CSV file
        String outputFilePath = "converted_files/Mud_Weight_Converted.csv";

        // Create the output directory if it doesn't exist
        File outputFile = new File(outputFilePath);
        outputFile.getParentFile().mkdirs(); // This makes sure the directory exists

        try (
                // Create a reader to read the input CSV file
                Reader reader = new FileReader(inputFilePath);
                // Create a writer to write to the output CSV file
                FileWriter writer = new FileWriter(outputFilePath);
                // Set up the CSVPrinter to write with headers
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Depth", "Mud Weight (kg/m3)"));
        ) {
            // List to store the CSV records
            List<CSVRecord> records = new ArrayList<>();
            // Parse the CSV file
            Iterable<CSVRecord> allRecords = CSVFormat.DEFAULT.parse(reader);

            // Skip the first two rows (headers) and collect the rest
            int skippedRows = 0;
            for (CSVRecord record : allRecords) {
                if (skippedRows < 2) {
                    skippedRows++;
                } else {
                    records.add(record);
                }
            }

            // Process each record
            for (CSVRecord record : records) {
                try {
                    // Get the depth value from the first column
                    String depth = record.get(0).trim();
                    // Get the mud weight value from the second column
                    double mudWeightLbmGalUS = Double.parseDouble(record.get(1).trim());
                    // Convert the mud weight to kg/m3
                    double mudWeightKgM3 = mudWeightLbmGalUS * CONVERSION_FACTOR;

                    // Write the depth and converted mud weight to the new CSV file
                    csvPrinter.printRecord(depth, mudWeightKgM3);
                } catch (NumberFormatException e) {
                    // Print a message if the data is invalid and skip the record
                    System.err.println("Skipping invalid data row: " + record.toString());
                }
            }

            // Print a success message when done
            System.out.println("Conversion completed successfully!");

        } catch (IOException e) {
            // Print the error if something goes wrong
            e.printStackTrace();
        }
    }
}









