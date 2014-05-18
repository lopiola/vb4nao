package pl.edu.agh.toik.vb4nao;

/**
 * Created by lopiola on 18.05.14.
 */
public class Test {
    Logger logger = new Logger(this.getClass());

    private static String NAOQI_IP = "127.0.0.1";
    private static int NAOQI_PORT = 9559;

    public Test() {
        logger.error("dupka");


        ALTextToSpeechProxy tts = new ALTextToSpeechProxy(NAOQI_IP, NAOQI_PORT);
        tts.say("Hello, world");
    }

    public static void main(String[] args) {
        new Test();
    }
}
