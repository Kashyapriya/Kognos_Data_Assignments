package org.example;

import ncsa.hdf.hdf5lib.H5;
import ncsa.hdf.hdf5lib.HDF5Constants;
import ncsa.hdf.hdf5lib.exceptions.HDF5Exception;

public class HDF5AttributeAdder {

    // Constants
    public static final int H5F_ACC_RDWR = 0x00000002; // Read/Write access
    public static final int H5P_DEFAULT = 0; // Default property list

    public static void main(String[] args) {
        String hdf5File = "Mud_Weight_PI.h5";
        String datasetName = "DATA"; // Dataset to which attributes will be added

        try {
            // Open the HDF5 file
            int fileId = H5.H5Fopen(hdf5File, H5F_ACC_RDWR, H5P_DEFAULT);
            System.out.println("HDF5 file opened successfully.");

            // Open the existing dataset
            int datasetId = H5.H5Dopen(fileId, datasetName);

            // Define the dataspace for the attribute (scalar attribute)
            int attrSpaceId = H5.H5Screate_simple(1, new long[] {1}, null);

            // Define the datatype for the attribute (string)
            int attrTypeId = H5.H5Tcopy(HDF5Constants.H5T_NATIVE_INT);

            // Create the attribute
            int attrId = H5.H5Acreate(datasetId, "Description", attrTypeId, attrSpaceId, H5P_DEFAULT);

            // Write the attribute data
            String description = "Mud Weight Data";
            H5.H5Awrite(attrId, HDF5Constants.H5T_NATIVE_INT, description);

            // Close attribute, dataspace, and dataset
            H5.H5Aclose(attrId);
            H5.H5Sclose(attrSpaceId);
            H5.H5Tclose(attrTypeId);
            H5.H5Dclose(datasetId);

            // Close the file
            H5.H5Fclose(fileId);
            System.out.println("Attribute added and HDF5 file closed successfully.");
        } catch (HDF5Exception e) {
            e.printStackTrace();
        }
    }
}

