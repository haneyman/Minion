package com.kopasetic;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestServiceWeather {
    ServiceWeather sw;

    @Before
    public void setup() {
//        main = new Main();
        sw = new ServiceWeather();
    }


    @Test
    public void testGetWeather() {
        String weather = ServiceWeather.get();
    }


    @Test
    public void testJsonParse() {
        try {
            String json = readFile("src/test/resources/weatherUnderground.json",Charset.defaultCharset());
            String result = sw.parseJsonCurrent(json);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static String readFile(String path, Charset encoding)  throws IOException {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, encoding);
    }
}
