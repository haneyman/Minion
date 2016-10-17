package com.kopasetic;

import org.junit.Before;
import org.junit.Test;


public class TestDesktopUtil {
    Main main;
    DesktopUtil du;

    @Before
    public void setup() {
        main = new Main();
        du = new DesktopUtil();
    }

    @Test
    public void testBrowser() {
        du.browserGoto("www.reddit.com");
    }
}