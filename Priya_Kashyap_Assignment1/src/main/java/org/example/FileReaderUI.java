package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class FileReaderUI extends JFrame {

    private JTextField filePathTextField; // show the file path
    private JTable dataTable; // display file data
    private DefaultTableModel tableModel; // manage table data

    public FileReaderUI() {
        // Set up the frame
        setTitle("File Reader"); // Set title of window
        setSize(600, 400); // Set size of window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the application when the window is closed
        setLayout(new BorderLayout()); //arranging components

        // Create a panel for the file path and browse button
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout()); // Arrange components in a line

        JLabel fileLabel = new JLabel("File Path:"); // Label for the file path text field
        filePathTextField = new JTextField(30); // Text field to display the file path
        JButton browseButton = new JButton("Browse"); // Button to open the file chooser

        // Add components to the panel
        panel.add(fileLabel);
        panel.add(filePathTextField);
        panel.add(browseButton);

        // Add the panel to the top of the frame
        add(panel, BorderLayout.NORTH);

        // Set up the table to display file data
        tableModel = new DefaultTableModel(); // Create a model to manage table data
        dataTable = new JTable(tableModel); // Create a table with the model
        JScrollPane scrollPane = new JScrollPane(dataTable); // Add scroll functionality to the table
        add(scrollPane, BorderLayout.CENTER); // Add the table to the center of the frame

        // Add an action listener to the browse button
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(); // Create a file chooser dialog
                int result = fileChooser.showOpenDialog(FileReaderUI.this); // Show the dialog and get the result
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile(); // Get the selected file
                    filePathTextField.setText(selectedFile.getAbsolutePath()); // Display the file path in the text field
                    loadFileData(selectedFile.getAbsolutePath()); // Load and display the file data
                }
            }
        });
    }

    private void loadFileData(String filePath) {
        // Read the file and get the data
        List<String[]> data = FileReaderUtil.readTextFile(filePath);
        tableModel.setRowCount(0); // Clear any existing rows
        tableModel.setColumnCount(0); // Clear any existing columns

        if (!data.isEmpty()) {
            // Set up columns and rows for the table
            String[] columnNames = data.get(0); // First row contains column names
            tableModel.setColumnIdentifiers(columnNames); // Set the column names

            // Add rows to the table
            for (int i = 1; i < data.size(); i++) {
                tableModel.addRow(data.get(i)); // Add each row of data
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FileReaderUI frame = new FileReaderUI(); // Create an instance of the FileReaderUI
            frame.setVisible(true); // Make the frame visible
        });
    }
}

