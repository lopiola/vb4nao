package pl.edu.agh.toik.vb4nao.nao;

import com.aldebaran.proxy.ALTextToSpeechProxy;

/**
 * Created by lopiola on 18.05.14.
 */
public class ConsoleTTS implements AbstractTTS {
    @Override
    public void say(String text) {
        System.out.println("Nao says: " + text);
    }
}
