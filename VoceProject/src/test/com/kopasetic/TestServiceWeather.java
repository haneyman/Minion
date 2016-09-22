package com.kopasetic;

import org.junit.Test;

public class TestServiceWeather {
    @Test
    public void testGetWeather() {
        String weather = ServiceWeather.get();

    }
}
