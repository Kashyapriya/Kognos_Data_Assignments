package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;

public class MainFrame extends JFrame {

    private JTextField filePathField; // Display the selected file path
    private JTextArea searchField; // For entering keywords to search
    private JTable resultsTable; // Table to display search results
    private Excel_Reader excelReader; // To perform the search

    public MainFrame() {
        excelReader = new Excel_Reader(); // Initialize Excel_Reader
        initUI(); // Set up the user interface
    }

    private void initUI() {
        setTitle("Excel Search"); // Window title
        setSize(700, 500); // Window size
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Close the application when the window is closed
        setLayout(new BorderLayout()); 

        // Panel at the top for selecting a file and entering keywords
        JPanel northPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(); 
        gbc.insets = new Insets(5, 5, 5, 5); 

        // Components for selecting a file
        JLabel fileLabel = new JLabel("Data Source:"); // Label for the file selection
        filePathField = new JTextField(40); // Show the selected file path
        JButton browseButton = new JButton("Browse"); // Button to open the file chooser

        // Action when the "Browse" button is clicked
        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(); // File chooser dialog
            int option = fileChooser.showOpenDialog(this); // Show the dialog and capture the user's choice
            if (option == JFileChooser.APPROVE_OPTION) { // If the user selects a file
                File selectedFile = fileChooser.getSelectedFile(); // Get the selected file
                filePathField.setText(selectedFile.getAbsolutePath()); // Show the file path in the text field
            }
        });

        // Add file selection components to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; 
        northPanel.add(fileLabel, gbc);

        gbc.gridx = 1;
        northPanel.add(filePathField, gbc);

        gbc.gridx = 2;
        northPanel.add(browseButton, gbc);

        // Components for entering and searching keywords
        JLabel searchLabel = new JLabel("Keyword:"); // keyword input
        searchField = new JTextArea(2, 40); //entering keywords
        searchField.setLineWrap(true); // Allow long keywords to wrap to the next line
        JScrollPane searchScrollPane = new JScrollPane(searchField); // Scroll pane for the text area

        JButton searchButton = new JButton("Search"); //To search

        // Action when the "Search" button is clicked
        searchButton.addActionListener(e -> {
            String filePath = filePathField.getText(); //file path
            String keywords = searchField.getText(); // entered keywords
            if (!filePath.isEmpty() && !keywords.isEmpty()) { 
                List<SearchResult> results = excelReader.searchExcelFile(filePath, keywords); // Search the file
                updateTable(results, filePath); // Update the table with the results
            } else {
                // Show a message if the user hasn't selected a file or entered keywords
                JOptionPane.showMessageDialog(this, "Please select a file and enter keywords to search.");
            }
        });

        // Add keyword search components to the panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        northPanel.add(searchLabel, gbc);

        gbc.gridx = 1;
        northPanel.add(searchScrollPane, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.NORTH; // Align the button to the top
        northPanel.add(searchButton, gbc);

        // Add the north panel to the top of the main window
        add(northPanel, BorderLayout.NORTH);

        // Table for displaying search results
        resultsTable = new JTable(new DefaultTableModel(
                new Object[]{"Keyword", "File Name", "Sheet", "Row Number", "Column Number", "File Path"}, 0));
        add(new JScrollPane(resultsTable), BorderLayout.CENTER); // Add the table to the center of the main window

        setVisible(true); // Make the window visible
    }

    // Method to update the table with the search results
    private void updateTable(List<SearchResult> results, String filePath) {
        DefaultTableModel model = (DefaultTableModel) resultsTable.getModel();
        model.setRowCount(0);  // Clear any existing rows 
        String fileName = new File(filePath).getName(); // Get the name of the selected file

        // Add each search result to the table
        for (SearchResult result : results) {
            model.addRow(new Object[]{
                    result.getKeyword(),
                    fileName,
                    "Sheet1",  //sheet name
                    result.getRow(),
                    result.getColumn(),
                    filePath
            });
        }
    }

    // Main method to start the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new); // Create and show the main window
    }
}


