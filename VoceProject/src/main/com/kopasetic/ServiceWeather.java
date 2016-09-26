package com.kopasetic;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
//http://openweathermap.org/current#zip
public class ServiceWeather {
    public static String apiKey = "6fd3bbb04b9660126f86525de05e1f44";
//http://api.openweathermap.org/data/2.5/weather?zip=94518,us&APPID=6fd3bbb04b9660126f86525de05e1f44
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
    509920514f25c566
    http://api.wunderground.com/api/509920514f25c566/conditions/q/CA/San_Francisco.json
     */

    public static String getWeatherFromWeatherUnderground() {
        System.out.println( "Retrieving Weather Underground Weather..." );
        String url = "http://api.wunderground.com/api/509920514f25c566/conditions/q/CA/San_Francisco.json" ;
        URLConnection conn = null;
        try {
            conn = new URL(url).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String result = convertStreamToString(conn.getInputStream());
            System.out.println("Weather underground results: " + result);
            return(parseJson(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "I dunno the weather";

    }

    public static String parseJson(String json) {
/*
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
*/
        String result = "";
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(json);
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject currentObservation = (JSONObject) jsonObject.get("current_observation");
            result = "the weather is " + (String) currentObservation.get("weather")
                    + " temperature is " + currentObservation.get("temp_f") + " degrees"
                    + " with wind of " + currentObservation.get("wind_mph") + " m p h";
            System.out.println("Parsed result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
