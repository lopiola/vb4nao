package pl.edu.agh.toik.vb4nao;

import pl.edu.agh.toik.vb4nao.http.Parser;
import pl.edu.agh.toik.vb4nao.nao.NAODictionary;
import pl.edu.agh.toik.vb4nao.nao.*;
import pl.edu.agh.toik.vb4nao.util.Logger;
import pl.edu.agh.toik.vb4nao.util.PhoneticIndexer;

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;

/**
 * Created by lopiola on 18.05.14.
 */
public class Application {
    private static Logger logger = new Logger(Application.class);

    private AbstractTTS tts;
    private AbstractSTT stt;
    private Parser parser;


    public void start(boolean startInConsole) {
        logger.info("vb4nao is starting...");

        Properties props = new Properties();
        try {
            props.load(new FileReader("properties.cfg"));
        } catch (IOException e) {
            logger.error("Cannot read properties.cfg");
            e.printStackTrace();
        }

        if (startInConsole) {
            tts = new ConsoleTTS();
            stt = new ConsoleSTT();
        } else {
            tts = new NaoTTS(props.getProperty("nao.ip", "127.0.0.1"),
                    Integer.parseInt(props.getProperty("nao.port", "9559")));

            stt = new ConsoleSTT();
//            stt = new NaoSTT(props.getProperty("nao.ip", "127.0.0.1"),
//                    Integer.parseInt(props.getProperty("nao.port", "9559")));
        }

        parser = new Parser();
        logger.info("vb4nao started!");
        mainLoop();
        System.exit(0);
    }

    enum State {
        STANDBY,
        LISTEN_PREDEFINED,
        LISTEN_DICTATED,
        CONFIRM_DICTATED,
        LIST_SECTIONS,
        CHOOSE_SECTION,
        READ_SECTION,
        EXITING
    }

    private void mainLoop() {
        String word;
        String website = "";
        State state = State.STANDBY;
        String[][] sections = null;
        int selectedSection = -1;
        PhoneticIndexer phoneticIndexer = null;

        while (state != State.EXITING) {
            switch (state) {
                case STANDBY:
                    tts.say(NAODialogue.STANDBY_PROMPT);
                    word = waitForWord(NAODictionary.standbyDictionary);
                    if (word.equals(NAODictionary.COMMAND_EXIT)) state = State.EXITING;
                    else if (word.equals(NAODictionary.COMMAND_PREDEFINED)) state = State.LISTEN_PREDEFINED;
                    else if (word.equals(NAODictionary.COMMAND_DICTATE)) state = State.LISTEN_DICTATED;
                    break;

                case LISTEN_PREDEFINED:
                    tts.say(NAODialogue.LISTEN_PREDEFINED_PROMPT);
                    word = waitForWord(NAODictionary.listenPredefinedDictionary);
                    if (word.equals(NAODictionary.COMMAND_EXIT)) state = State.EXITING;
                    else if (word.equals(NAODictionary.COMMAND_START)) state = State.STANDBY;
                    else {
                        website = NAODictionary.getPredefinedWebsite(word);
                        state = State.LIST_SECTIONS;
                    }
                    break;

                case LISTEN_DICTATED:
                    tts.say(NAODialogue.LISTEN_DICTATED_PROMPT);
                    LinkedList<String> letters = new LinkedList<>();
                    while (true) {
                        word = waitForWord(NAODictionary.listenDictatedDictionary);
                        if (word.equals(NAODictionary.COMMAND_EXIT)) {
                            state = State.EXITING;
                            break;
                        } else if (word.equals(NAODictionary.COMMAND_START)) {
                            state = State.STANDBY;
                            break;
                        } else if (word.equals(NAODictionary.COMMAND_OKAY)) {
                            break;
                        } else {
                            tts.say(word);
                            letters.add(word);
                        }
                    }
                    if (state == State.LISTEN_DICTATED) {
                        website = lettersToWebsite(letters);
                        state = State.CONFIRM_DICTATED;
                    }
                    break;

                case CONFIRM_DICTATED:
                    tts.say(NAODialogue.CONFIRM_DICTATED + spell(website));
                    word = waitForWord(NAODictionary.confirmDictatedDictionary);
                    if (word.equals(NAODictionary.COMMAND_EXIT)) state = State.EXITING;
                    else if (word.equals(NAODictionary.COMMAND_START)) state = State.STANDBY;
                    else if (word.equals(NAODictionary.COMMAND_OKAY)) state = State.LIST_SECTIONS;
                    else if (word.equals(NAODictionary.COMMAND_NO)) state = State.LISTEN_DICTATED;
                    break;

                case LIST_SECTIONS:
                    sections = parser.parse(website);
                    if (sections == null) {
                        tts.say(NAODialogue.CANNOT_READ_WEBSITE);
                        state = State.STANDBY;
                    } else {
                        tts.say(NAODialogue.READING_SECTIONS);
                        phoneticIndexer = new PhoneticIndexer(sections.length);
                        for (int i = 0; i < sections.length; i++) {
                            tts.say(phoneticIndexer.identifierFor(i));
                            tts.say(sections[i][0]);
                        }
                        state = State.CHOOSE_SECTION;
                    }
                    break;

                case CHOOSE_SECTION:
                    tts.say(NAODialogue.CHOOSE_SECTION_PROMPT);
                    word = waitForWord(phoneticIndexer.getDictionary());
                    if (word.equals(NAODictionary.COMMAND_EXIT)) state = State.EXITING;
                    else if (word.equals(NAODictionary.COMMAND_START)) state = State.STANDBY;
                    else if (word.equals(NAODictionary.COMMAND_AGAIN)) state = State.LIST_SECTIONS;
                    else {
                        selectedSection = phoneticIndexer.indexOfIdentifier(word);
                        state = State.READ_SECTION;
                    }
                    break;

                case READ_SECTION:
                    tts.say(NAODialogue.READ_SECTION_PROMPT);
                    tts.say(sections[selectedSection][1]);
                    state = State.LIST_SECTIONS;
                    break;

                case EXITING:
                    break;
            }

        }
        tts.say(NAODialogue.GOODBYE);
    }

    private String waitForWord(String[] dictionary) {
        stt.setDictionary(dictionary);
        while (true) {
            String word = stt.pollWord();
            if (word == null) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                return word;
            }
        }
    }

    private String lettersToWebsite(LinkedList<String> letters) {
        StringBuilder sb = new StringBuilder("http://www.");
        for (String letter : letters) {
            sb.append(NAODictionary.getPhoneticLetter(letter));
        }
        return sb.toString();
    }

    private String spell(String word) {
        // Strip the protocol and www
        int index = 0;
        if (word.startsWith("http://")) index = 7;
        if (word.startsWith("http://www.")) index = 11;
        word = word.substring(index);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            sb.append(word.charAt(i));
            sb.append(" ");
        }
        return sb.toString();
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
        boolean startInConsole = (args.length == 1) && (args[0].equals("console"));
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
        application.start(startInConsole);
    }
}
