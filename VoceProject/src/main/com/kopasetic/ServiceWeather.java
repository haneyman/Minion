package com.kopasetic;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;


public class ServiceWeather {
    public String lastJson;
    ServiceResult serviceResult;

    public ServiceWeather() {
    }

    public  ServiceResult getWeatherFromWeatherUnderground(String words) throws Exception {
        String location = "";
        String wordsCleaned = words.trim().toUpperCase();
        serviceResult = new ServiceResult();
        serviceResult.setWordsIn(wordsCleaned);
        if (wordsCleaned.contains("WEATHER")) {
            if (wordsCleaned.length() > 8)
                location = wordsCleaned.substring(8);
            System.out.println("weather location: " + location);
            processRequest(location, false);
        } else if (wordsCleaned.contains("FORECAST")) {
            if (wordsCleaned.length() > 9)
                location = wordsCleaned.substring(9);
            System.out.println("forecast location: " + location);
            processRequest(location, true);
        } else {
            throw new Exception("Unknown words in getWeatherFromWeatherUnderground:" + words);
        }
        return serviceResult;
    }

    private void processRequest(String location, boolean forecast) {
        HashMap locationMap = new HashMap<String, String>();
        locationMap.put("CONCORD", "CA/Concord");
        locationMap.put("SAN FRANCISCO", "CA/San_Francisco");
        locationMap.put("WALNUT CREEK", "CA/Walnut Creek");
        locationMap.put("CONCORD", "CA/Concord");
        locationMap.put("TRUCKEE", "CA/Truckee");
        locationMap.put("NORTHSTAR", "39.241095,-120.139343");
        locationMap.put("KIRKWOOD", "38.667552,-120.064567");
        locationMap.put("HEAVENLY", "38.917283,-119.902287");
        locationMap.put("BRECKENRIDGE", "39.472809,-106.102240");

        String query;
        System.out.println( "Retrieving Weather Underground Weather..." );

        if (locationMap.containsKey(location.trim().toUpperCase())) {
            query = (String) locationMap.get(location.trim().toUpperCase());
        } else {
            location = "wellllll I don't know that location but Concord";
            query = "CA/Concord";
        }
        //current
        //   http://api.wunderground.com/api/509920514f25c566/conditions/q/CA/Truckee.json
        String url = "http://api.wunderground.com/api/509920514f25c566/conditions/q/" + query + ".json" ;
        String jsonResult = connectToAPI(url);
        String result = "The current weather in " + location + " is " + parseJsonCurrent(jsonResult);
        if (forecast) {
            //forecast
            url = "http://api.wunderground.com/api/509920514f25c566/forecast/q/" + query + ".json";
            jsonResult = connectToAPI(url);
            result += ", The forecast is " + parseJsonForecast(jsonResult);
        }
        result = result.replace("mph", " miles per hour ");
        result = result.replace(" SW ", "southwest");
        result = result.replace(" SE ", "southeast");
        result = result.replace(" NW ", "northwest");
        result = result.replace(" NE ", "northeast");
        result = result.replace(" N ", "north");
        result = result.replace(" S ", "south");
        result = result.replace(" E ", "east");
        result = result.replace(" W ", "west");
        if (result.contains("snow")) {
            result += "  there is snow in the forecast, yipppeeeee";
        }

        serviceResult.setResult(result);
        return ;
    }

    private String connectToAPI(String url) {
        URLConnection conn = null;
        try {
            System.out.println("Connecting to weather underground url: " + url);
            conn = new URL(url).openConnection();
            System.out.println("  Success!");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        String jsonResult = "";
        try {
            jsonResult = convertStreamToString(conn.getInputStream());
            System.out.println("   Weather underground results: " + jsonResult);
            lastJson = jsonResult;
            return jsonResult;
        } catch (IOException e) {
            e.printStackTrace();
            return "I had a problem getting the forecast.";
        }
    }

    public  String parseJsonCurrent(String json) {
        String result = "";
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(json);
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject currentObservation = (JSONObject) jsonObject.get("current_observation");
            result = "the weather is " + (String) currentObservation.get("weather")
                    + " temperature is " + currentObservation.get("temp_f") + " degrees"
                    + " with wind of " + currentObservation.get("wind_mph") + " miles per hour"
                    + " precipitation today is " + currentObservation.get("precip_today_in") + " inches "
                    + " precipitation in the last hour is " + currentObservation.get("precip_1hr_in") + " inches ";
            System.out.println("Parsed result: " + result);
            serviceResult.setShow((String) currentObservation.get("forecast_url"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String parseJsonForecast(String json) {
        String result = "";
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(json);
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject txt_forecast = (JSONObject)((JSONObject)jsonObject.get("forecast")).get("txt_forecast");
            JSONArray forecastdays = (JSONArray) txt_forecast.get("forecastday");
            for (Object forecast : forecastdays) {
                JSONObject daily = (JSONObject) forecast;
                result += daily.get("title") + " will be " + daily.get("fcttext") + ", ";
            }
            //show = (String) currentObservation.get("forecast_url");
            System.out.println("Parsed result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    //***************** open weather
    public static String apiKey = "6fd3bbb04b9660126f86525de05e1f44";
    //http://openweathermap.org/current#zip
    // http://api.openweathermap.org/data/2.5/weather?zip=94518,us&APPID=6fd3bbb04b9660126f86525de05e1f44
    public static String get() {
        System.out.println( "Retrieving OpenWeatherMap Weather..." );
        String url = "http://api.openweathermap.org/data/2.5/weather?zip=94518,us&APPID=" + apiKey ;
        URLConnection conn = null;
        try {
            conn = new URL(url).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String result = convertStreamToString(conn.getInputStream());
            System.out.println(result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "I dunno the weather";

    }
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
    /* sample return
    {"coord":{"lon":-122.06,"lat":37.95},"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01n"}],"base":"stations","main":{"temp":296.21,"pressure":1011,"humidity":39,"temp_min":294.26,"temp_max":298.15},"wind":{"speed":3.08,"deg":239,"gust":8.22},"rain":{},"clouds":{"all":0},"dt":1474503089,"sys":{"type":3,"id":62714,"message":0.0036,"country":"US","sunrise":1474552593,"sunset":1474596253},"id":5383720,"name":"Pleasant Hill","cod":200}
     */

    //Weather underground
    /*
    http://api.wunderground.com/weather/api/d/docs?d=data/index

    509920514f25c566

    http://api.wunderground.com/api/509920514f25c566/conditions/q/CA/San_Francisco.json
     */

}
