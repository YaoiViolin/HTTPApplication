package HttpApplicationClasses;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.format;

public class HtmlFileCreator {
    private static final Logger LOG = Logger.getLogger(HtmlFileCreator.class.getName());
    private static int createdFilesCount = 0;
    private final String URL;

    public HtmlFileCreator(String url) {
        this.URL = url;
    }

    protected String getURL() {
        return URL;
    }

    public String saveToFile() { //читает заданный url и записывает его в локальный файл
        int count;
        byte[] buff = new byte[64];
        InputStream is;
        OutputStream out;

        try {
            is = new URL(URL).openStream();
            String filePath = createFile();

            if (filePath != null) {
                out = new FileOutputStream(filePath);
                LOG.log(Level.INFO, "Saving HTML code to file...");

                while ((count = is.read(buff)) != -1) {
                    out.write(buff, 0, count);
                }
                out.close();
                is.close();

                LOG.log(Level.INFO, "File saved at " + filePath);
                return filePath;

            } else throw new IOException();

        } catch (MalformedURLException urlException) {
            LOG.log(Level.SEVERE, "Invalid URL", urlException);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Exception: ", e);
        }
        return null;
    }

    private String createFile() { //создает файл в корне D:/, если файл с таким именем уже есть, создает другой
        try {
            String path = "C:/savedHTMLFile[%s].txt";
            File f = new File(format(path, createdFilesCount));
            if (!(f.createNewFile())) {
                createdFilesCount++;
                createFile();
            }
            return format(path, createdFilesCount);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error occurred while creating file.", e);
        }
        return null;
    }
}
