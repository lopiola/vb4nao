package pl.edu.agh.toik.vb4nao.http;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import pl.edu.agh.toik.vb4nao.util.Logger;

import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by lopiola on 18.05.14.
 */
public class Parser {
    Logger logger = new Logger(this.getClass());

    public String parse(String url) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Aby zmniejszyc czas parsowania
            factory.setNamespaceAware(false);
            factory.setValidating(false);
            factory.setFeature("http://xml.org/sax/features/namespaces", false);
            factory.setFeature("http://xml.org/sax/features/validation", false);
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

            Document doc = factory.newDocumentBuilder().parse(url);
            Element html = doc.getDocumentElement();
            Element head = findElement(html, "head");
            Element title = findElement(head, "title");

            return title.getTextContent();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Element findElement(Element element, String tag) {
        NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node currentNode = nodes.item(i);
            if (currentNode instanceof Element) {
                Element currentElement = (Element) currentNode;
                if (currentElement.getTagName().equals(tag)) {
                    return (Element) currentNode;
                }
            }
        }
        return null;
    }
}
