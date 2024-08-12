package org.example.priya_kashyap_assignment5;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Assignment1Controller {

    @FXML private TextField filePathTextField; // TextField to show the file path
    @FXML private TableView<String[]> dataTable; // TableView to display data from the file
    @FXML private TableColumn<String[], String> column1; // Column for the first data field
    @FXML private TableColumn<String[], String> column2; // Column for the second data field
    @FXML private Button browseButton; // Button to open file chooser dialog

    // ObservableList to hold the data read from the file
    private ObservableList<String[]> data = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Set up how each column gets its data from the data array
        column1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        column2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
        // Add more columns here if needed

        // Link the TableView with the ObservableList
        dataTable.setItems(data);
    }

    @FXML
    private void handleBrowse() {
        // Create a FileChooser to let the user select a file
        FileChooser fileChooser = new FileChooser();
        // Show the file chooser dialog and get the selected file
        File file = fileChooser.showOpenDialog(browseButton.getScene().getWindow());

        if (file != null) {
            // Set the file path in the text field
            filePathTextField.setText(file.getAbsolutePath());
            // Load data from the selected file
            loadData(file);
        }
    }

    private void loadData(File file) {
        // Clear any existing data in the table
        data.clear();

        // Read the file and add each line to the data list
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            // Read each line from the file
            while ((line = reader.readLine()) != null) {
                // Split the line into values based on comma 
                String[] values = line.split(",");
                // Add the values to the data list
                data.add(values);
            }
        } catch (IOException e) {
            // Print the exception
            e.printStackTrace();
        }
    }
}


