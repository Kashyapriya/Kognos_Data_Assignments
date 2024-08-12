package org.example;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class MudWeightProcessor {

    public static void main(String[] args) throws URISyntaxException {
        String inputFilePath = Paths.get(MudWeightProcessor.class.getClassLoader()
                .getResource("src/main/resources/Mud_Weight_Converted.csv").toURI()).toString();

        String outputFilePath = "Mud_Weight_PI.csv";

        try (CSVParser parser = new CSVParser(new FileReader(inputFilePath), CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            FileWriter writer = new FileWriter(outputFilePath);

            writer.write("Depth,Mud Weight (kg/m3)\n"); // Write header to the output file

            for (CSVRecord record : parser) {
                String depth = record.get("Depth");
                String mudWeight = record.get("Mud Weight (kg/m3)");

                // Multiply the Mud Weight by Math.PI
                double mudWeightValue = Double.parseDouble(mudWeight);
                double mudWeightPI = mudWeightValue * Math.PI;

                // Write the processed values to the output file
                writer.write(depth + "," + mudWeightPI + "\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



