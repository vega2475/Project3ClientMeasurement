package com.vega.project;

import com.vega.project.DTO.MeasurementDTO;
import com.vega.project.DTO.MeasurementResponse;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Draw {
    public static void main(String[] args) throws IOException {
        List<Double> measurements = getValues();
        double[] days = new double[measurements.size()];
        Arrays.setAll(days, i -> i + 1);
        double[] values = new double[measurements.size()];
        for (int i = 0; i < measurements.size(); i++) {
            values[i] = measurements.get(i);
        }
        XYChart chart = QuickChart.getChart("Measurements", "DAY", "VALUE", "temperatures" , days, values);

        new SwingWrapper<>(chart).displayChart();

        BitmapEncoder.saveBitmap(chart, "/home/vega/IdeaProjects/Project3ClientMeasurement/Measurements", BitmapEncoder.BitmapFormat.PNG);
    }

    private static List<Double> getValues(){
        final RestTemplate restTemplate = new RestTemplate();
        final String url = "http://localhost:8080/measurements";

        MeasurementResponse measurementResponse = restTemplate.getForObject(url, MeasurementResponse.class);
        if(measurementResponse == null || measurementResponse.getMeasurements() == null){
            return Collections.emptyList();
        }

        return measurementResponse.getMeasurements().stream().map(MeasurementDTO::getValue).collect(Collectors.toList());
    }
}
