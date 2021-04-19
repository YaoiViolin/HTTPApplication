package HttpApplicationClasses;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HtmlFileCreatorTest {

    @Test
    void saveToFile() throws IOException {
        boolean expectation = true;
        boolean reality = true;
        HtmlFileCreator fileCreator = new HtmlFileCreator("https://fiz.1sept.ru/article.php?ID=200800306"); //пример довольно короткого html-файла
        String filePath = fileCreator.saveToFile();
        File actualFile = new File(filePath);
        File expectedFile = new File("/SavingTestingFile.txt"); //короткий html-файл

        BufferedReader readerOfExpFile = new BufferedReader(new FileReader(expectedFile));
        BufferedReader readerOfActFile = new BufferedReader(new FileReader(actualFile));

        while (readerOfExpFile.ready()) {
            if (!(readerOfExpFile.readLine().equals(readerOfActFile.readLine()))) {
                reality = false;
                break;
            }
        }
        assertEquals(expectation, reality);
    }
}