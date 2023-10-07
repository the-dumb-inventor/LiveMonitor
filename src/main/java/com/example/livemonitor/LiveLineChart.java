package com.example.livemonitor;

import javafx.animation.AnimationTimer;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class LiveLineChart extends LineChart<Number, Number> {
    private static final int MAX_DATA_POINTS = 1100;
    private int xSeriesData = 0;
    private XYChart.Series<Number, Number> series;

    private final Queue<XYChart.Data<Number, Number>> dataBuffer = new LinkedList<>();

    public LiveLineChart(String name) {
        super(new NumberAxis(0, MAX_DATA_POINTS, 0), new NumberAxis(0, 1024, 0));

        this.series = new XYChart.Series<>();

        setAnimated(false);
        setHorizontalGridLinesVisible(true);
        getData().add(series);
        prepareTimeline();
        loadDataFromFileToBuffer(name);
    }

    private void logError(Exception ex) {
        // Log the error using a logging framework
        System.err.println(ex.getMessage());
    }

    private void notifyUser(String message) {
        // Implement a mechanism to notify the user, e.g., a status bar, dialog, etc.
    }

    private void prepareTimeline() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                synchronized (dataBuffer) {
                    if (!dataBuffer.isEmpty()) {
                        int pointsToAdd = 2;  // or any other number based on your needs
                        for (int i = 0; i < pointsToAdd && !dataBuffer.isEmpty(); i++) {
                            XYChart.Data<Number, Number> dataPoint = dataBuffer.poll();
                            series.getData().add(dataPoint);
                            ((NumberAxis) getXAxis()).setLowerBound(dataPoint.getXValue().intValue() - 1099);
                            ((NumberAxis) getXAxis()).setUpperBound(dataPoint.getXValue().intValue());
                        }

                        // If there are more than 500 points plotted, remove the oldest one
                        if (series.getData().size() > 1100) {
                            series.getData().remove(0);
                        }


                    }
                }
            }
        }.start();
    }
    private void loadDataFromFileToBuffer(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            synchronized (dataBuffer) {
                for (String line : lines) {
                    int value = (int) Double.parseDouble(line.trim());
                    dataBuffer.add(new XYChart.Data<>(xSeriesData++, value));
                }
            }
        } catch (Exception e) {
            logError(e);
            notifyUser("Error reading data from the file.");
        }
    }



}
