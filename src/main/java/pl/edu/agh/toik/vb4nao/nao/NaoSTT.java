package pl.edu.agh.toik.vb4nao.nao;

import com.aldebaran.proxy.ALMemoryProxy;
import com.aldebaran.proxy.ALSpeechRecognitionProxy;
import com.aldebaran.proxy.ALTextToSpeechProxy;
import com.aldebaran.proxy.Variant;
import pl.edu.agh.toik.vb4nao.util.Logger;

/**
 * Created by lopiola on 20.05.14.
 */
public class NaoSTT implements AbstractSTT {
    private static Logger logger = new Logger(NaoSTT.class);

    private ALMemoryProxy memory;
    private ALSpeechRecognitionProxy recog;

    private String lastWord;

    public NaoSTT(String ip, int port) {
        memory = new ALMemoryProxy(ip, port);
        recog = new ALSpeechRecognitionProxy(ip, port);

        recog.setAudioExpression(true);
        recog.setVisualExpression(true);
        recog.subscribe("WordRecognized");
        recog.setLanguage("English");
    }

    @Override
    public void cleanUp() {
        recog.unsubscribe("WordRecognized");
        //recog.exit();
        //memory.exit();
    }


    @Override
    public String pollWord(String[] dictionary) {
        // TODO nie wiem czy to bedzie dzialac jak tutaj ustawie
        recog.setVocabulary(dictionary, true);
        Variant words = memory.getData("WordRecognized");

        String word = words.getElement(0).toString();

        if (word != null && !word.equals(lastWord)) {
            //System.out.println("The word is:" + word + ":");
//            for (int i = 0; i < words.getSize(); i += 2) {
//                System.out.println("Word: " + (String) words.getElement(i).toString());
//                System.out.println("Probability: " + (float) words.getElement(i + 1).toFloat());
//            }
            lastWord = word;
            if (!word.equals("") && words.getElement(1).toFloat() > 0.2) {
                return word;
            }
        }

        return null;
    }
}