package com.example.livemonitor;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class HelloController {

    @FXML
    private VBox chartContainer1; // VBox to contain fillSessionChart

    @FXML
    private VBox chartContainer2; // VBox to contain cleanSessionChart

    @FXML
    public void initialize() {
        LiveLineChart fillChart = new LiveLineChart("v6_data(2).txt");
        LiveLineChart cleanChart = new LiveLineChart("v6_data(2).txt");
        // Add the LiveLineCharts to the VBoxes


        VBox.setVgrow(fillChart, Priority.ALWAYS);    // Allow the chart to grow vertically

        VBox.setVgrow(cleanChart, Priority.ALWAYS);   // Allow the chart to grow vertically

        chartContainer1.getChildren().add(fillChart);

        chartContainer2.getChildren().add(cleanChart);
    }
}
