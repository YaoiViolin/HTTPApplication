package HttpApplicationClasses;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StatisticsHandler {
    private static final Logger LOG = Logger.getLogger(HtmlFileCreator.class.getName());
    private static int createdFilesCount = 0;

    public static void printStatistics(HtmlParser parser) {
        Map<String, Integer> words = parser.getWords();
        for (Entry<String, Integer> pair : words.entrySet()) {
            System.out.println(pair.getKey() + " - " + pair.getValue());
        }
    }

    public static void saveStatistics(HtmlParser parser) {
        Map<String, Integer> words = parser.getWords();
        try {
            String path = createStatisticsFile(parser);
            File file = new File(path);

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for (Entry<String, Integer> pair : words.entrySet()) {
                String str = pair.getKey() + " - " + pair.getValue() + "\n";
                writer.write(str);
            }
            writer.close();
            LOG.log(Level.INFO, "file created at " + path);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error occurred while saving statistics", e);
        }


    }

    public static String createStatisticsFile(HtmlParser parser) { //создает файл в корне D:/, если файл с таким именем уже есть, создает другой
        try {
            String source = (parser.getUrl().replaceAll("http[s]?", "")).replaceAll("\\W+", "");
            if (source.length() >= 150) //чтобы имя файла точно влезло
                source = source.substring(0, 150);

            File file = new File("C:/" + source + createdFilesCount + ".txt");
            if (!(file.createNewFile())) {

                createdFilesCount++;
                createStatisticsFile(parser);
            }
            return "C:/" + source + createdFilesCount + ".txt";
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error occurred while creating statistics file.", e);
        }
        return null;
    }
}
