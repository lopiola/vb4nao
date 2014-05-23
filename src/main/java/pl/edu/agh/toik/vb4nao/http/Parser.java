package pl.edu.agh.toik.vb4nao.http;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import de.l3s.boilerpipe.extractors.DefaultExtractor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.edu.agh.toik.vb4nao.util.Logger;

import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.BreakIterator;
import java.util.*;

/**
 * Created by lopiola on 18.05.14.
 */
public class Parser {
    Logger logger = new Logger(this.getClass());

    public static final int HEADER_THRESHOLD = 6;

    //    String[][] result = {
    //            {"section 0 title", "section 0 content"},
    //            {"section 1 title", "section 1 content"},
    //            ...
    //    };

    public String[][] parse(String string_url) {
        try {
            URL url = new URL(string_url);
            String text = DefaultExtractor.INSTANCE.getText(url);
            text = text.replaceAll("\n",".\n");
//            StringBuilder text = new StringBuilder(DefaultExtractor.INSTANCE.getText(url));

            // Breaking down into sentences and small filtering
            LinkedList<String> sentences = new LinkedList<>();
            BreakIterator it = BreakIterator.getSentenceInstance(Locale.US);
            it.setText(text);
            int start = it.first();
            for (int end = it.next(); end != BreakIterator.DONE; start = end, end = it.next()) {
                String sentence = text.substring(start, end).trim();
//                System.out.println("> " + sentence);
                if(sentence.length() > 3) {
                    sentences.add(sentence);
                }
            }

            // Get sections
            HashMap<String, String> sections = new LinkedHashMap<>();
            ListIterator<String> iterator = sentences.listIterator();
            String current_header, first_sentence;
            StringBuilder current_section = new StringBuilder();

            // Choice of the first header
            if(iterator.hasNext()) {
                first_sentence = iterator.next();
            } else {
                logger.error("Website empty");
                return null;
            }
            if(first_sentence.split(" ").length < HEADER_THRESHOLD) {
                current_header = first_sentence;
            } else {
                current_header = "Beginning.";
                current_section.append(first_sentence);
            }

            // Header extractions
            while (iterator.hasNext()) {
                String sentence = iterator.next();
                if(sentence.split(" ").length < HEADER_THRESHOLD && current_section.length() != 0) {
                    sections.put(current_header, current_section.toString());
                    current_header = sentence;
                    current_section = new StringBuilder();
                } else {
                    current_section.append(sentence);
                }
            }
            sections.put(current_header, current_section.toString());

            // Create an array from a map
            String[][] array = new String[sections.size()][2];
            int i = 0;
            for (Map.Entry<String, String> entry : sections.entrySet()) {
                array[i][0] = entry.getKey();
                array[i++][1] = entry.getValue();
//                System.out.println("Header: " + entry.getKey());
//                System.out.println("Text: " + entry.getValue());
//                System.out.println();
            }

            return array;


//            Document doc = Jsoup.connect(url).get();
//            // get text
//            Elements text = doc.select("p, h1, h2, h3, h4, h5, span");
//
//            Iterator<Element> it = text.iterator();
//            while (it.hasNext()) {
//                Element e = it.next();
//                // add interpunction to distinct between elements
//                if (!e.text().endsWith(".") && !e.text().endsWith("!") && !e.text().endsWith(":")) {
//                    e.appendText(".");
//                }
//                // add an additional space if there is none
//                e.appendText(" ");
//
//                // filter to short or obsolete paragraphs
//                if (e.tagName().equals("p") && (e.text().length() < 5 || e.text().contains("Copyright"))) {
//                    it.remove();
//                }
//            }
//
//
//            StringBuilder content = new StringBuilder(doc.title() + ". " + text.text());
//
//            // Refining the final string
//            for (int i = 2; i < content.length(); ++i) {
//                if (content.substring(i - 2, i + 1).equals(". .")) {
//                    content.replace(i - 2, i + 1, ".");
//                }
//            }
//            return new String[][]{
//                    {content.substring(content.indexOf(".")), content.toString()}
//            };
        } catch (BoilerpipeProcessingException | MalformedURLException e) {
            e.printStackTrace();
            logger.error("Exception during parsing");
            return null;
        }

    }
}
