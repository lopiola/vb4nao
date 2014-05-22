package pl.edu.agh.toik.vb4nao;

import com.aldebaran.proxy.ALTextToSpeechProxy;
import pl.edu.agh.toik.vb4nao.http.Parser;
import pl.edu.agh.toik.vb4nao.http.PredefinedWebsites;
import pl.edu.agh.toik.vb4nao.nao.*;
import pl.edu.agh.toik.vb4nao.util.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by lopiola on 18.05.14.
 */
public class Application {
    private static Logger logger = new Logger(Application.class);

    private AbstractTTS tts;
    private AbstractSTT stt;

    public void start() {
        logger.info("vb4nao is starting");

        Properties props = new Properties();
        try {
            props.load(new FileReader("properties.cfg"));
        } catch (IOException e) {
            logger.error("Cannot read properties.cfg");
            e.printStackTrace();
        }

        tts = new ConsoleTTS();
//        tts = new NaoTTS(props.getProperty("nao.ip", "127.0.0.1"),
//                Integer.parseInt(props.getProperty("nao.port", "9559")));

        stt = new ConsoleSTT();
//        stt = new NaoSTT(props.getProperty("nao.ip", "127.0.0.1"),
//                Integer.parseInt(props.getProperty("nao.port", "9559")));

        mainLoop();
    }

    private void mainLoop() {
        while (true) {
            String word = stt.pollWord();
            if (word == null) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                if (word.equals("finish")) {
                    break;
                }
                logger.info("Recognized word is: " + word);
                logger.debug("Got new task: " + word);
                String title = new Parser().parse(PredefinedWebsites.get(word));
                if (title != null)
                    tts.say("Reading the website: " + title);
                    logger.info("Website content: " + title);
            }
        }
    }

    public void cleanUp() {
        logger.info("vb4nao is terminating - performing cleanup");
        try {
            tts.cleanUp();
        } catch (Exception e) {
            logger.info("Exception while shutting down TTS: \n" + e.toString());
        }
        try {
            stt.cleanUp();
        } catch (Exception e) {
            logger.info("Exception while shutting down STT: \n" + e.toString());
        }
    }

    public static void main(String[] args) {
        final Application application = new Application();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    application.cleanUp();
                } catch (Exception e) {
                    logger.info("Exception while shutting down: \n" + e.toString());
                }
            }
        });
        application.start();
    }
}
