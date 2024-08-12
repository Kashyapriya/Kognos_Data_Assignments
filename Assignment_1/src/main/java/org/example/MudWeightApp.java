package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

// Main class for the Mud Weight application
public class MudWeightApp {

    private JFrame frame; // Main window of the application
    private ChartPanel chartPanel; // Panel to display the chart
    private JFreeChart chart; // The chart itself
    private XYSeries series;  // Data series to plot on the chart
    private MudWeightData mudWeightData; // Class to handle data loading

    // Constructor to set up the GUI and chart
    public MudWeightApp() {
        mudWeightData = new MudWeightData();  // Create a new MudWeightData object

        frame = new JFrame("Mud Weight Data");  // Create the main window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Close the app when the window is closed
        frame.setSize(500, 600);  // Set the size of the window
        frame.setLayout(new BorderLayout());  // Set layout for the window
        frame.getContentPane().setBackground(Color.BLUE);  // Set background color to blue

        // Create a panel for the buttons at the top
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout()); // Arrange buttons horizontally
        topPanel.setBackground(Color.BLUE);  // Set panel background color to blue

        // Create and style the Display button
        JButton displayButton = new JButton("Display");
        displayButton.setBackground(Color.LIGHT_GRAY);  // Set button background color
        displayButton.setForeground(Color.BLACK);       // Set button text color
        displayButton.setFont(new Font("Arial", Font.BOLD, 14));  // Set button font

        // Create and style the Clear button
        JButton clearButton = new JButton("Clear");
        clearButton.setBackground(Color.LIGHT_GRAY);    // Set button background color
        clearButton.setForeground(Color.BLACK);         // Set button text color
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));  // Set button font

        topPanel.add(displayButton);  // Add Display button to the panel
        topPanel.add(clearButton);    // Add Clear button to the panel

        frame.add(topPanel, BorderLayout.NORTH);  // Add the panel to the top of the window

        // Set up the chart
        series = new XYSeries("Mud Weight Data");  // Create a new series for the data
        XYSeriesCollection dataset = new XYSeriesCollection(series);  // Create a dataset from the series
        chart = ChartFactory.createXYLineChart(
                null, "Mud Weight Data", "Depth",
                dataset, PlotOrientation.VERTICAL,
                false, true, false
        );

        // Customize the appearance of the chart
        chart.getPlot().setBackgroundPaint(Color.BLUE);  // Set the chart background color
        chart.getXYPlot().getRenderer().setSeriesPaint(0, Color.YELLOW);  // Set the line color
        chart.getXYPlot().getDomainAxis().setLabelPaint(Color.BLACK);  // X-axis label color
        chart.getXYPlot().getRangeAxis().setLabelPaint(Color.BLACK);   // Y-axis label color
        chart.getXYPlot().getDomainAxis().setTickLabelPaint(Color.BLACK);  // X-axis tick marks color
        chart.getXYPlot().getRangeAxis().setTickLabelPaint(Color.BLACK);   // Y-axis tick marks color
        chart.getXYPlot().getDomainAxis().setLabelFont(new Font("Arial", Font.BOLD, 12));  // X-axis label font
        chart.getXYPlot().getRangeAxis().setLabelFont(new Font("Arial", Font.BOLD, 12));   // Y-axis label font
        chart.getXYPlot().getDomainAxis().setTickLabelFont(new Font("Arial", Font.PLAIN, 10));  // X-axis tick font
        chart.getXYPlot().getRangeAxis().setTickLabelFont(new Font("Arial", Font.PLAIN, 10));   // Y-axis tick font

        chartPanel = new ChartPanel(chart);  // Create a panel to display the chart
        chartPanel.setBackground(Color.BLUE);  // Set the background color of the chart panel

        frame.add(chartPanel, BorderLayout.CENTER);  // Add the chart panel to the center of the window

        // Set up button actions
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayData();  // Show the data on the chart when Display button is clicked
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearData();  // Clear the chart data when Clear button is clicked
            }
        });

        frame.setVisible(true);  // Make the window visible
    }

    // Method to load and display data on the chart
    private void displayData() {
        try {
            // Load the data from a file
            mudWeightData.loadFromFile();
            // Add each data point to the chart series
            for (MudWeightData.DataPoint point : mudWeightData.getDataPoints()) {
                series.add(point.getWeight(), point.getDepth());
            }
        } catch (IOException e) {
            e.printStackTrace();  // Print any I/O errors
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);  // Handle URI errors
        }
    }

    // Method to clear the data from the chart
    private void clearData() {
        series.clear();  // Remove all data points from the series
    }

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MudWeightApp());  // Start the application on the Event Dispatch Thread
    }
}



