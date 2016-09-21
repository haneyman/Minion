package com.kopasetic;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

//import org.apache.log4j.Logger;

// api doco:  http://voce.sourceforge.net/api/java/classvoce_1_1_speech_interface.html
// http://cmusphinx.sourceforge.net/doc/sphinx4/edu/cmu/sphinx/jsgf/JSGFGrammar.html

public class Main {
    public static void main(String[] argv)
    {
        System.out.println("starting main...");
        voce.SpeechInterface.init("./lib", true, true,
                "./grammar", "main");

        System.out.println("Minion engine initialized.");
        voce.SpeechInterface.synthesize("I am minion.");

        boolean quit = false;
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

                // Check if the string contains 'quit'.
                if (-1 != s.indexOf("quit"))
                {
                    quit = true;
                } else if (-1 != s.indexOf("hello world")) {
                    System.out.println("hello back at you.");
                    voce.SpeechInterface.synthesize("well hello to you too beautiful!");
                } else if (-1 != s.indexOf("weather")) {
                    System.out.println(ServiceWeather.get());
                    //voce.SpeechInterface.synthesize("well hello to you too beautiful!");
                }

                System.out.println("You said: " + s);
                //voce.SpeechInterface.synthesize(s);
            }
        }

        voce.SpeechInterface.destroy();
        voce.SpeechInterface.stopSynthesizing();
        voce.SpeechInterface.destroy();
        System.exit(0);

    }

}
