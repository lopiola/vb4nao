package pl.edu.agh.toik.vb4nao.http;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pl.edu.agh.toik.vb4nao.util.Logger;

import java.io.IOException;

/**
 * Created by lopiola on 18.05.14.
 */
public class Parser {
    Logger logger = new Logger(this.getClass());

    public String parse(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            return title;
        } catch (IOException e) {
            System.out.println("Could not access the website");
            e.printStackTrace();
            return null;
        }
    }
}
