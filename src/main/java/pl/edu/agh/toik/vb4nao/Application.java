package pl.edu.agh.toik.vb4nao;

import com.aldebaran.proxy.ALTextToSpeechProxy;
import pl.edu.agh.toik.vb4nao.http.Parser;
import pl.edu.agh.toik.vb4nao.http.PredefinedWebsites;
import pl.edu.agh.toik.vb4nao.nao.AbstractSTT;
import pl.edu.agh.toik.vb4nao.nao.AbstractTTS;
import pl.edu.agh.toik.vb4nao.nao.ConsoleSTT;
import pl.edu.agh.toik.vb4nao.nao.NaoTTS;
import pl.edu.agh.toik.vb4nao.util.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by lopiola on 18.05.14.
 */
public class Application {
    private Logger logger = new Logger(this.getClass());

    private AbstractTTS tts;
    private AbstractSTT stt;

    public Application() {
        logger.info("vb4nao is starting");

        Properties props = new Properties();
        try {
            props.load(new FileReader("properties.cfg"));
        } catch (IOException e) {
            logger.error("Cannot read properties.cfg");
            e.printStackTrace();
        }

        tts = new NaoTTS(props.getProperty("nao.ip", "127.0.0.1"),
                Integer.parseInt(props.getProperty("nao.port", "9559")));

        stt = new ConsoleSTT();

        mainLoop();
    }

    private void mainLoop() {
        while(true) {
            String word = stt.pollWord();
            if (word == null) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                logger.debug("Got new task: " + word);
                String title = new Parser().parse(PredefinedWebsites.get(word));
                tts.say("Page title is: " + title);
                logger.info("Page title is: " + title);

            }
        }
    }

    public static void main(String[] args) {
        new Application();
    }
}
