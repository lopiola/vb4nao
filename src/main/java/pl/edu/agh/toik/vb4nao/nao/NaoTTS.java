package pl.edu.agh.toik.vb4nao.nao;

import com.aldebaran.proxy.ALTextToSpeechProxy;

/**
 * Created by lopiola on 18.05.14.
 */
public class NaoTTS implements AbstractTTS {
    private ALTextToSpeechProxy tts;

    public NaoTTS(String ip, int port) {
        tts = new ALTextToSpeechProxy(ip, port);
    }

    @Override
    public void say(String text) {
        tts.say(text);
    }

    @Override
    public void cleanUp() {
        //tts.exit();
    }
}
