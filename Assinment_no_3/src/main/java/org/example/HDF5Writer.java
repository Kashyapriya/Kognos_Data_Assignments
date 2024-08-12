package org.example;

import ncsa.hdf.hdf5lib.H5;
import ncsa.hdf.hdf5lib.HDF5Constants;
import ncsa.hdf.hdf5lib.exceptions.HDF5Exception;

public class HDF5Writer {

    public static void main(String[] args) {
        String hdf5File = "Mud_Weight_PI.h5";

        try {
            // Open the HDF5 file
            int fileId = H5.H5Fopen(hdf5File, HDF5Constants.H5F_ACC_RDWR, HDF5Constants.H5P_DEFAULT);

            // Define the dimensions of the dataset
            long[] dims = new long[] { 10 }; // Example dimension
            int spaceId = H5.H5Screate_simple(1, dims, null); // Create a dataspace

            // Define the datatype for the dataset
            int typeId = H5.H5Tcopy(HDF5Constants.H5T_NATIVE_FLOAT);

            // Create the dataset
            int datasetId = H5.H5Dcreate(fileId, "DATA", typeId, spaceId, HDF5Constants.H5P_DEFAULT);

            // Prepare data to write
            float[] data = new float[] {1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f};

            // Write data to the dataset
            int memSpaceId = H5.H5Screate_simple(1, dims, null); // Create memory space
            H5.H5Dwrite(datasetId, HDF5Constants.H5T_NATIVE_FLOAT, memSpaceId, spaceId, HDF5Constants.H5P_DEFAULT, data);

            // Close the memory space
            H5.H5Sclose(memSpaceId);

            // Add an attribute to the dataset
            int attrSpaceId = H5.H5Screate_simple(1, new long[] {1}, null);
            int attrTypeId = H5.H5Tcopy(HDF5Constants.H5T_NATIVE_INT);
            int attrId = H5.H5Acreate(datasetId, "Description", attrTypeId, attrSpaceId, HDF5Constants.H5P_DEFAULT);

            // Write the attribute
            String description = "Mud Weight Data";
            H5.H5Awrite(attrId, HDF5Constants.H5T_NATIVE_INT, description);

            // Close attribute and dataspace
            H5.H5Aclose(attrId);
            H5.H5Sclose(attrSpaceId);
            H5.H5Tclose(attrTypeId);

            // Close the dataset
            H5.H5Dclose(datasetId);

            // Close the file
            H5.H5Fclose(fileId);

            System.out.println("HDF5 file updated successfully.");
        } catch (HDF5Exception e) {
            e.printStackTrace();
        }
    }
}



