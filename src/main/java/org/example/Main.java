package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Main {

    private static String auth = ""; // Логин и пароль
    private static String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
    private static String refKey = "1f244518-29f3-11ee-ab53-f3d63edf6bf9";


    public static void main(String[] args) throws IOException {
        String json = request();
        //System.out.println(jsonParse(json));
        //System.out.println(jsonParse(json, refKey));
        Data data = jsonParse(json, new Data());
        //System.out.println(data.toString());
        List<Data> dataList = jsonParse(json, new ArrayList<>());
        dataList.forEach(d -> System.out.println(d.toString()));

    }
    //Десериализуем класс Data
    public static Data jsonParse(String json, Data data) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootArray = objectMapper.readTree(json);
        JsonNode valueArray = rootArray.get("value");

        data = objectMapper.treeToValue(valueArray.get(0), Data.class);

        return data;
    }

    //Десериализуем объекты из List<Data> в объекты класса Data
    public static List<Data> jsonParse(String json, List<Data> dataList) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootArray = objectMapper.readTree(json);
        JsonNode valueArray = rootArray.get("value");
        for (JsonNode value : valueArray) {
            Data data = objectMapper.treeToValue(value, Data.class);
            dataList.add(data);
        }

        return dataList;
    }

    //Парсим и находим значением поля
    public static String jsonParse(String json) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootArray = objectMapper.readTree(json);
        JsonNode valueArray = rootArray.get("value");

        String result = valueArray.get(0).path("НаименованиеПолное").asText();

        return result;
    }

    // Парсим и находим значения поля для конкретного Ref_Key
    public static String jsonParse(String json, String ref) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootArray = objectMapper.readTree(json);
        JsonNode valueArray = rootArray.get("value");

        for (JsonNode node : valueArray) {
            if (node.path("Ref_Key").asText().equals(ref)) {
                return node.path("НаименованиеПолное").asText();
            }
        }

        return null;
    }

    // Выполняем запрос к 1С
    public static String request() throws IOException {
        String line = "";
        StringBuilder response = new StringBuilder();
        String numeric = URLEncoder.encode("КлассификаторЕдиницИзмерения", StandardCharsets.UTF_8);
        String baseUrl = "https://1c.svs-tech.pro/UNF/odata/standard.odata/Catalog_" + numeric + "?$format=json";

        // Создаём URL-объект
        URL url = new URL(baseUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // Устанавливаем метод запроса
        connection.setRequestMethod("GET");

        connection.setRequestProperty("Authorization", "Basic " + encodedAuth);

        int responseCode = connection.getResponseCode();


        if (responseCode == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));


            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
        }
        return response.toString();
    }
}