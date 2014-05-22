package pl.edu.agh.toik.vb4nao.http;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.edu.agh.toik.vb4nao.util.Logger;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by lopiola on 18.05.14.
 */
public class Parser {
    Logger logger = new Logger(this.getClass());

    public String parse(String url) {
        try {
            Document doc = Jsoup.connect(url).get();

            // get text
            Elements text = doc.select("p, h1, h2, h3, h4, h5, span");

            Iterator<Element> it = text.iterator();
            while(it.hasNext() ) {
                Element e = it.next();
                // add interpunction to distinct between elements
                if(!e.text().endsWith(".") && !e.text().endsWith("!") && !e.text().endsWith(":")) {
                    e.appendText(".");
                }
                // add an additional space if there is none
                e.appendText(" ");

                // filter to short or obsolete paragraphs
                if(e.tagName().equals("p") && (e.text().length() < 5 || e.text().contains("Copyright"))){
                    it.remove();
                }
            }

            StringBuilder content = new StringBuilder(doc.title() + ". " + text.text());

            // Refining the final string
            for(int i = 2; i < content.length(); ++i) {
                if(content.substring(i-2,i+1).equals(". .")) {
                     content.replace(i-2, i+1, ".");
                }
            }
            return content.toString();
        } catch (IOException e) {
            System.out.println("Could not access the website");
            e.printStackTrace();
            return null;
        }
    }
}
