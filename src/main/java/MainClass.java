import HttpApplicationClasses.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static HttpApplicationClasses.StatisticsHandler.printStatistics;
import static HttpApplicationClasses.StatisticsHandler.saveStatistics;
import static java.util.Locale.ROOT;

public class MainClass {
    private static final Logger LOG = Logger.getLogger(MainClass.class.getName());

   static {
        try {
            LogManager.getLogManager().readConfiguration(MainClass.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter the URL in format http(s)//:<your_website.xx>");
        while (true) {
            try {
                String url = reader.readLine();

                if (url.toLowerCase(ROOT).equals("exit"))
                    break;

                HtmlParser parser = new HtmlParser(new HtmlFileCreator(url));
                parser.parse();

                System.out.println("Print statistics? Y/N");
                if (reader.readLine().toUpperCase(ROOT).equals("Y"))
                    printStatistics(parser);

                System.out.println("Save statistics? Y/N");
                if (reader.readLine().toUpperCase(ROOT).equals("Y"))
                    saveStatistics(parser);

                System.out.println("\nTry another URL or type \"exit\" to exit program");
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "It seems like something went wrong. Please try again with valid input data");
            }
        }

    }
}


