package HttpApplicationClasses;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HtmlParser {
    private static final Logger LOG = Logger.getLogger(HtmlParser.class.getName());

    private final Map<String, Integer> words = new HashMap<>();
    private final File LOCAL_HTML_FILE;

    private String url;

    public HtmlParser(File file) { //исползовать для локально сохраненного html-файла
        LOCAL_HTML_FILE = file;
    }

    public HtmlParser(HtmlFileCreator HTMLFileInstance) { //использовать, когда локально сохраненного html-файла ещё нет, есть только ссылка на ресурс
        this.LOCAL_HTML_FILE = new File(HTMLFileInstance.saveToFile());
        this.url = HTMLFileInstance.getURL();
    }

    //****************************

    protected String getUrl() {
        return url;
    }

    public Map<String, Integer> getWords() {
        return words;
    }


    public void parse() {  //в локальном с html-файле ищет текстовую информацию
        Elements elements = getElementsFromHtmlFile(LOCAL_HTML_FILE);
        try {
            if (elements == null) throw new NullPointerException();
            else {
                LOG.log(Level.INFO, "Start parsing...");

                for (Element e : elements) {
                    if (e.hasText())
                        parseString(e.ownText());
                }
                LOG.log(Level.INFO, "Parsing completed");
            }
        } catch (NullPointerException e) {
            LOG.log(Level.SEVERE, "Nothing to parse!", e);
        }
    }


    //****************************
    //далее только приватные методы

    private Elements getElementsFromHtmlFile(File file) {
        try {
            Document document = Jsoup.parse(file, null);
            return document.getAllElements();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error occurred while parsing", e);
        }
        return null;
    }

    private void parseString(String string) {   //получает на вход строку, из которой убирает все знаки препинания, кроме указанных в regex
        String[] str = string.split("[^a-zA-Zа-яА-Я-#+ЁёїЇ]+");
        for (String s : str) {
            updateMap(s.toUpperCase(Locale.ROOT));
        }
    }

    private void updateMap(String key) {    //добавляет в таблицу новое слово, либо обновляет статистику об уже существующем

        if (!(key.equals(" ") || key.equals("") || key.equals("-"))) {
            if (words.containsKey(key)) {
                words.replace(key, words.get(key) + 1);
            } else words.put(key, 1);
        }
    }
}
