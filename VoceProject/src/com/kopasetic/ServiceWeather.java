package com.kopasetic;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
//http://openweathermap.org/current#zip
public class ServiceWeather {
    public static String apiKey = "6fd3bbb04b9660126f86525de05e1f44";
//http://api.openweathermap.org/data/2.5/weather?zip=94518,us&APPID=6fd3bbb04b9660126f86525de05e1f44
    public static String get() {
        System.out.println( "Retrieving OpenWeatherMap Weather..." );
        String url = "api.openweathermap.org/data/2.5/weather?zip=94518" + ",us" + "&APPID={" + apiKey + "}";
        URLConnection conn = null;
        try {
            conn = new URL(url).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String result = conn.getInputStream().toString();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "I dunno the weather";

    }
}
