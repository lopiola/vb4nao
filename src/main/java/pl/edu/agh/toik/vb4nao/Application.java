package pl.edu.agh.toik.vb4nao;

import com.aldebaran.proxy.ALTextToSpeechProxy;
import pl.edu.agh.toik.vb4nao.http.Parser;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by lopiola on 18.05.14.
 */
public class Application {
    Logger logger = new Logger(this.getClass());

    public Application() {
        logger.info("vb4nao is starting");

        Properties props = new Properties();
        try {
            props.load(new FileReader("properties.cfg"));
        } catch (IOException e) {
            logger.error("Cannot read properties.cfg");
            e.printStackTrace();
        }

        ALTextToSpeechProxy tts = new ALTextToSpeechProxy(
                props.getProperty("nao.ip", "127.0.0.1"),
                Integer.parseInt(props.getProperty("nao.port", "9559")));

        String title = new Parser().parse("http://w3.org");

        tts.say("Page title is: " + title);

        logger.info("Page title is: " + title);
    }

    public static void main(String[] args) {
        new Application();
    }
}
