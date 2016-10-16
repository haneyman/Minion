package com.kopasetic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Mark on 10/16/2016.
 */
public class TestMain {
    Main main;
    DesktopUtil du;
    String result;

    @Before
    public void setup() {
        main = new Main();
        du = new DesktopUtil();
    }

    @Test
    public void testAll() {
        try {
            main.processWords("weather heavenly", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(main.getPrevServiceResult().getResult().contains("degrees"));

        assertTrue(main.show() != null);//not sure how to test this
    }
}
