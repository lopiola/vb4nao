package pl.edu.agh.toik.vb4nao.nao;

import pl.edu.agh.toik.vb4nao.util.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by lopiola on 18.05.14.
 */
public class ConsoleSTT extends Thread implements AbstractSTT {
    private Logger logger = new Logger(this.getClass());

    private ConcurrentLinkedQueue<String> wordQueue;

    private String[] dictionary;

    public ConsoleSTT() {
        wordQueue = new ConcurrentLinkedQueue<>();
        logger.info("Enter words in the console");
        start();
    }

    @Override
    public void run() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String s = br.readLine();
                if (s != null && !s.isEmpty()) {
                    wordQueue.add(s);
                    if (s.equals("finish")) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setDictionary(String[] dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public String pollWord() {
        String word;
        while ((word = wordQueue.poll()) != null) {
            if (Arrays.asList(dictionary).indexOf(word) > -1) {
                return word;
            }
        }
        return null;
    }

    @Override
    public void cleanUp() {
    }
}
