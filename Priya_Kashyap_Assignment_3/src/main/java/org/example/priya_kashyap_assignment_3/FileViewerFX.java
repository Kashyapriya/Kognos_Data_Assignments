package org.example.priya_kashyap_assignment_3;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FileViewerFX extends Application {

    // declare the TableView
    private TableView<DataRow> tableView;
    private ObservableList<DataRow> data;

    public static void main(String[] args) {
        launch(args); // launches the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX TableView Example"); // Set the window title

        // Initialize the TableView and the list
        tableView = new TableView<>();
        data = FXCollections.observableArrayList();

        // Create the columns for the table: Keyword, Row, and Column
        TableColumn<DataRow, String> keywordColumn = new TableColumn<>("Keyword");
        keywordColumn.setCellValueFactory(cellData -> cellData.getValue().keywordProperty());

        TableColumn<DataRow, Integer> rowColumn = new TableColumn<>("Row");
        rowColumn.setCellValueFactory(cellData -> cellData.getValue().rowProperty().asObject());

        TableColumn<DataRow, Integer> colColumn = new TableColumn<>("Column");
        colColumn.setCellValueFactory(cellData -> cellData.getValue().columnProperty().asObject());

        // Add the columns to the TableView
        tableView.getColumns().addAll(keywordColumn, rowColumn, colColumn);
        tableView.setItems(data); // Link the TableView with our data list

        // Create input fields for Keyword, Row, and Column
        TextField keywordField = new TextField();
        keywordField.setPromptText("Keyword"); // Hint text for the input field
        TextField rowField = new TextField();
        rowField.setPromptText("Row");
        TextField colField = new TextField();
        colField.setPromptText("Column");

        // Create buttons for different actions: Add, Update, Remove, Clear
        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button removeButton = new Button("Remove");
        Button clearButton = new Button("Clear");

        // Set actions for the buttons
        addButton.setOnAction(e -> addRow(keywordField, rowField, colField)); // Call addRow() when Add button is clicked
        updateButton.setOnAction(e -> updateRow(keywordField, rowField, colField)); // Call updateRow() when Update button is clicked
        removeButton.setOnAction(e -> removeRow()); // Call removeRow() when Remove button is clicked
        clearButton.setOnAction(e -> clearTable()); // Call clearTable() when Clear button is clicked

        // Organize the input fields and buttons in a horizontal box
        HBox controlBox = new HBox(5, keywordField, rowField, colField, addButton, updateButton, removeButton, clearButton);

        // Organize the whole layout in a BorderPane
        BorderPane root = new BorderPane();
        root.setTop(controlBox); // Place the input fields and buttons at the top
        root.setCenter(tableView); // Place the table at the center

        // Set up the scene (the window content) with our layout and show it
        Scene scene = new Scene(root, 800, 400); // 800x400 is the size of the window
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // This method adds a new row to the table with the input from the fields
    private void addRow(TextField keywordField, TextField rowField, TextField colField) {
        String keyword = keywordField.getText();
        String rowText = rowField.getText();
        String colText = colField.getText();

        // Check if all input fields are filled
        if (keyword.isEmpty() || rowText.isEmpty() || colText.isEmpty()) {
            showAlert("Input Error", "Please fill in all fields."); // Show error if any field is empty
            return;
        }

        int row;
        int col;
        try {
            // Try to convert the row and column input to integers
            row = Integer.parseInt(rowText);
            col = Integer.parseInt(colText);
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Row and Column must be valid integers."); // Show error if conversion fails
            return;
        }

        // Add the new row to the data list
        data.add(new DataRow(keyword, row, col));
        clearInputFields(keywordField, rowField, colField); // Clear input fields after adding
    }

    // This method updates the selected row with the new data from the input fields
    private void updateRow(TextField keywordField, TextField rowField, TextField colField) {
        DataRow selectedRow = tableView.getSelectionModel().getSelectedItem(); // Get the selected row in the table
        if (selectedRow != null) {
            // Update the selected row with new data
            selectedRow.setKeyword(keywordField.getText());
            selectedRow.setRow(Integer.parseInt(rowField.getText()));
            selectedRow.setColumn(Integer.parseInt(colField.getText()));
            tableView.refresh(); // Refresh the table to show the updated data
        }
    }

    // This method removes the selected row from the table
    private void removeRow() {
        DataRow selectedRow = tableView.getSelectionModel().getSelectedItem(); // Get the selected row in the table
        if (selectedRow != null) {
            data.remove(selectedRow); // Remove the selected row from the data list
        }
    }

    // This method clears all the rows from the table
    private void clearTable() {
        data.clear(); // Clear all data from the data list
    }

    // This method clears the input fields after adding or updating a row
    private void clearInputFields(TextField keywordField, TextField rowField, TextField colField) {
        keywordField.clear();
        rowField.clear();
        colField.clear();
    }

    // This method shows an alert with an error message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR); // Create an alert of type ERROR
        alert.setTitle(title); // Set the title of the alert
        alert.setHeaderText(null); // No header for the alert
        alert.setContentText(message); // Set the content (message) of the alert
        alert.showAndWait(); // Show the alert and wait for the user to close it
    }

    // This inner class represents a row of data
    public static class DataRow {
        private final SimpleStringProperty keyword;
        private final SimpleIntegerProperty row;
        private final SimpleIntegerProperty column;

        public DataRow(String keyword, int row, int column) {
            this.keyword = new SimpleStringProperty(keyword); // Store the keyword
            this.row = new SimpleIntegerProperty(row); // Store the row number
            this.column = new SimpleIntegerProperty(column); // Store the column number
        }

        public String getKeyword() {
            return keyword.get();
        }

        public void setKeyword(String keyword) {
            this.keyword.set(keyword);
        }

        public SimpleStringProperty keywordProperty() {
            return keyword;
        }

        public int getRow() {
            return row.get();
        }

        public void setRow(int row) {
            this.row.set(row);
        }

        public SimpleIntegerProperty rowProperty() {
            return row;
        }

        public int getColumn() {
            return column.get();
        }

        public void setColumn(int column) {
            this.column.set(column);
        }

        public SimpleIntegerProperty columnProperty() {
            return column;
        }
    }
}

