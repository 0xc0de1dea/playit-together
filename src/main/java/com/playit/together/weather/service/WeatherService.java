package com.playit.together.weather.service;

import com.playit.together.weather.entity.Weather;
import com.playit.together.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {
    @Value("${openweathermap.key}")
    private String apiKey;

    private final WeatherRepository weatherRepository;

    private HashMap<String, Object> parseWeather(String jsonString){
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        } catch (ParseException e){
            throw new RuntimeException(e);
        }

        HashMap<String, Object> resultMap = new HashMap<>();
        JSONObject mainData = (JSONObject) jsonObject.get("main");
        resultMap.put("temp", mainData.get("temp"));
        JSONArray weatherArray = (JSONArray) jsonObject.get("weather");
        JSONObject weatherData = (JSONObject) weatherArray.get(0);
        resultMap.put("main", weatherData.get("main"));
        resultMap.put("icon", weatherData.get("icon"));

        return resultMap;
    }

    private String getWeatherString(String region) throws MalformedURLException {
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + region + "&appid=" + apiKey;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            BufferedReader br;

            if (responseCode == 200){
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }

            br.close();

            return response.toString();
        } catch (Exception e){
            return "failed to get response";
        }
    }

    private Weather getWeatherFromApi(String region) throws MalformedURLException {
        String weatherData = getWeatherString(region);

        HashMap<String, Object> parsedWeather = parseWeather(weatherData);

        Weather weather = Weather.builder()
                .region(region)
                .temperature((Double) parsedWeather.get("temp"))
                .condition(parsedWeather.get("main").toString())
                .recordedAt(LocalDate.now())
                .build();

        return weather;
    }

    private Weather getWeather(String region, LocalDate recordedAt) throws MalformedURLException {
        List<Weather> weatherListFromDB = weatherRepository.findAllByRegionAndRecordedAt(region, recordedAt);

        if (weatherListFromDB.isEmpty()) {
            return getWeatherFromApi(region);
        } else {
            return weatherListFromDB.get(0);
        }
    }
}
