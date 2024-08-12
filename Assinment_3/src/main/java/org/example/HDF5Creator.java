package org.example;

import ncsa.hdf.object.Dataset;
import ncsa.hdf.object.Datatype;
import ncsa.hdf.object.h5.H5File;
import ncsa.hdf.hdf5lib.HDF5Constants;
import ncsa.hdf.object.FileFormat;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class HDF5Creator {

   
    public static void createHDF5File(String csvFile, String hdf5File, String datasetName) {
        try {
            // Read data from the CSV file
            List<Float> data = new ArrayList<>();
            try (FileReader reader = new FileReader(csvFile)) {
                // Read the CSV file and use the first row as headers
                Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
                
                // Process each row in the CSV
                for (CSVRecord record : records) {
                    // Get the value from the "Mud Weight (kg/m3) * PI" column
                    String mudWeightStr = record.get("Mud Weight (kg/m3) * PI");
                    
                    // Convert the string to a float and add it to our list
                    data.add(Float.parseFloat(mudWeightStr));
                }
            }

            // Create and open a new HDF5 file
            H5File file = new H5File(hdf5File, FileFormat.CREATE);
            file.open();

            // Define the type of data we're using (float)
            Datatype dtype = file.createDatatype(Datatype.CLASS_FLOAT, 4, Datatype.ORDER_LE, Datatype.NATIVE);

            // Set the size of the dataset based on the number of data points
            long[] dims = {data.size()};

            // Create the dataset in the HDF5 file and write our data to it
            Dataset dataset = file.createScalarDS("/" + datasetName, null, dtype, dims, null, null, 0, data.toArray(new Float[0]));

            // Add extra information to the dataset (metadata)
            dataset.writeMetadata("X unit");
            dataset.writeMetadata("Y unit");

            // Close the HDF5 file to save everything
            file.close();

            // Print a message saying the file was created successfully
            System.out.println("HDF5 file created successfully.");

        } catch (Exception e) {
            // Print any errors that occur
            e.printStackTrace();
        }
    }
}





