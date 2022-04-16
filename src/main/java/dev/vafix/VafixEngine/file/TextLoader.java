package dev.vafix.VafixEngine.file;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URL;

public class TextLoader {

    private static Logger logger = LogManager.getLogger(TextLoader.class);

    public static String load(String path) {
        URL url = TextLoader.class.getClassLoader().getResource(path);

        if (url == null) {
            logger.error("File not found: " + path);
            return null;
        }

        File file;
        FileReader fr = null;
        try {
            file = new File(url.toURI());
            fr = new FileReader(file);
        } catch (Exception e) {
            logger.error("File could not be loaded: " + path);
            return null;
        }

        StringBuilder str = new StringBuilder();
        try (BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                str.append(line).append("\n");
            }
        } catch (Exception e) {
            logger.error("File could not be read properly: " + path);
            return null;
        }
        return str.toString();
    }

}
