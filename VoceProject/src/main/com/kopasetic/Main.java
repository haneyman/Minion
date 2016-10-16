package com.kopasetic;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

//import org.apache.log4j.Logger;

// api doco:  http://voce.sourceforge.net/api/java/classvoce_1_1_speech_interface.html
// http://cmusphinx.sourceforge.net/doc/sphinx4/edu/cmu/sphinx/jsgf/JSGFGrammar.html

public class Main {
    private String lastWords;
    private ServiceWeather serviceWeather;
    private DesktopUtil desktopUtil;
    private ServiceResult prevServiceResult;
    boolean quit = false;

    public static void main(String[] argv) {
        Main main = new Main();
        try {
            main.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Main() {
        serviceWeather = new ServiceWeather();
        desktopUtil = new DesktopUtil();
        prevServiceResult = new ServiceResult();
    }

    private void start() throws Exception {
        System.out.println("starting main...");
        voce.SpeechInterface.init("./lib", true, true,
                "./grammar", "main");

        System.out.println("Minion engine initialized.");
        voce.SpeechInterface.synthesize("I am minion.");

        while (!quit)
        {
            try
            {
                Thread.sleep(300);
            }
            catch (InterruptedException e)
            {
            }

            while (voce.SpeechInterface.getRecognizerQueueSize() > 0)
            {
                String s = voce.SpeechInterface.popRecognizedString();
                System.out.println("You said: " + s);
                processWords(s,true);
                //voce.SpeechInterface.synthesize(s);
            }
        }

        voce.SpeechInterface.destroy();
        voce.SpeechInterface.stopSynthesizing();
        voce.SpeechInterface.destroy();
        System.exit(0);

    }

    public void processWords(String words, boolean speak) throws Exception {
        if (-1 != words.indexOf("quit"))
        {
            //quit = true;
        } else if (-1 != words.indexOf("hello world")) {
            prevServiceResult = new ServiceResult();
            prevServiceResult.setWordsIn(words);
            prevServiceResult.setResult("well hello to you too beautiful!");
            prevServiceResult.setShow("www.cnn.com");
        } else if (-1 != words.indexOf("weather")) {
            prevServiceResult = serviceWeather.getWeatherFromWeatherUnderground(words);
        } else if (-1 != words.indexOf("forecast")) {
            prevServiceResult = serviceWeather.getWeatherFromWeatherUnderground(words);
        } else if (-1 != words.indexOf("giants")) {
            System.out.println("giants");
        } else if (-1 != words.indexOf("show")) {
            show();
            return;
        } else if (-1 != words.indexOf("exit")) {
            voce.SpeechInterface.synthesize("Later dude.  Be cool.");
            quit = true;
            return;
        } else if (-1 != words.indexOf("you suck")) {
            voce.SpeechInterface.synthesize("eat me");
            return;
        } else if (-1 != words.indexOf("repeat")) {
        } else {
            prevServiceResult.setResult("Sorry, I do not understand.");
        }


        if (prevServiceResult.getResult().length() > 0) {
            if (speak) {
                voce.SpeechInterface.synthesize(prevServiceResult.getResult());
            } else {
                System.out.println("Voice DISABLED, response: " + prevServiceResult.getResult());
            }
        }
    }

    public Process show() {
        Process process = desktopUtil.browserGoto(prevServiceResult.getShow());
        //process.
        return process;
    }

    public String getLastWords() {
        return lastWords;
    }

    public void setLastWords(String lastWords) {
        this.lastWords = lastWords;
    }

    public ServiceResult getPrevServiceResult() {
        return prevServiceResult;
    }

    public void setPrevServiceResult(ServiceResult prevServiceResult) {
        this.prevServiceResult = prevServiceResult;
    }
}
