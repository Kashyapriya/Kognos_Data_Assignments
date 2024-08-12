package org.example.priya_kashyap_assignment5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Priya_Kashyap_Assignment_5.fxml"));

        // Load the FXML content into a Parent object
        Parent root = loader.load();

        // Set the title of the application window
        primaryStage.setTitle("Assignment 1");
        primaryStage.setScene(new Scene(root));

        // Display the primaryStage
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}



