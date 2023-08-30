package com.vega.project;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Hello world!
 */
public class Client {
    public static void main(String[] args) {
        final String name = "Sensor90";
        registerSensor(name);

        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            sendMeasurement(random.nextDouble(), random.nextBoolean(), name);
        }
    }

    private static void registerSensor(String name) {
        final String url = "http://localhost:8080/sensors/registration";

        Map<String, Object> json = new HashMap<>();
        json.put("name", name);

        makePostRequest(url, json);
    }

    private static void makePostRequest(String url, Map<String, Object> json) {
        final RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(json, headers);

        try {
            restTemplate.postForObject(url, request, String.class);

            System.out.println("All be fine!");
        } catch (HttpClientErrorException e) {
            System.out.println("Error!");
            System.out.println(e.getMessage());
        }
    }

    private static void sendMeasurement(double value, boolean raining, String sensorName){
        final String url = "http://localhost:8080/measurements/add";

        Map<String, Object> json = new HashMap<>();
        json.put("value", value);
        json.put("raining", raining);
        json.put("sensor", Map.of("name", sensorName));

        makePostRequest(url, json);
    }
}
