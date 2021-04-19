package HttpApplicationClasses;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HtmlParserTest {

    @Test
    void parse() {
        File file = new File("/TestHTMLFile.txt");
        HtmlParser parser = new HtmlParser(file);
        Map<String, Integer> actualResults;

        Map<String, Integer> expectedResults = new HashMap<>();
        expectedResults.put("НАЗВАНИЕ", 1);
        expectedResults.put("ДОКУМЕНТА", 1);
        expectedResults.put("ЗДРАВСТВУЙ", 1);
        expectedResults.put("МИР", 1);
        expectedResults.put("WEB-ДОКУМЕНТ", 1);
        expectedResults.put("ЗДЕСЬ", 1);
        expectedResults.put("HELLO", 1);
        expectedResults.put("УРА", 3);
        expectedResults.put("РАСПОЛОЖЕН", 1);

        parser.parse();
        actualResults = parser.getWords();

        assertEquals(expectedResults, actualResults);
    }
}