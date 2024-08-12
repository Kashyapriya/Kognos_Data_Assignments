package org.example;

public class Main {
    public static void main(String[] args) {
        String inputFile = "Mud_Weight.csv";
        String outputFile = "Mud_Weight_PI.csv";
        String hdf5File = "Mud_Weight.h5";
        String datasetName = "DATA";

        // Process CSV and create Mud_Weight_PI.csv
        MudWeightProcessor.processMudWeight(inputFile, outputFile);

        // Create HDF5 file with DATA node
        HDF5Creator.createHDF5File(outputFile, hdf5File, datasetName);
    }
}
