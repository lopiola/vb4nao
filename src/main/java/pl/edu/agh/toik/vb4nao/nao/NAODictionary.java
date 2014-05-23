package pl.edu.agh.toik.vb4nao.nao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lopiola on 18.05.14.
 */
public class NAODictionary {
    public static final String COMMAND_EXIT = "exit"; // Exits the application
    public static final String COMMAND_START = "start"; // Goes to the main menu
    public static final String COMMAND_OKAY = "okay"; // Goes to the main menu
    public static final String COMMAND_NO = "no"; // Goes to the main menu
    public static final String COMMAND_PREDEFINED = "predefined"; // Allows choice of predefined website
    public static final String COMMAND_DICTATE = "dictate"; // Allows dictating of website address
    public static final String COMMAND_AGAIN = "again"; // Read section names again

    private static final HashMap<String, String> sites = new HashMap<>();

    static {
        sites.put("ted", "http://ted.com");
        sites.put("apple", "http://apple.com");
        sites.put("stack", "http://stackoverflow.com");
        sites.put("bbc", "http://bbc.co.uk");
        sites.put("wiki", "http://en.wikipedia.org/wiki/Main_Page");
        sites.put("tips", "http://www.sitepoint.com/screen-reader-usability-tips/");
    }


    public static String getPredefinedWebsite(String key) {
        return sites.get(key);
    }

    public static final HashMap<String, String> phoneticAlphabet = new HashMap<>();

    static {
        phoneticAlphabet.put("alpha", "a");
        phoneticAlphabet.put("bravo", "b");
        phoneticAlphabet.put("charlie", "c");
        phoneticAlphabet.put("delta", "d");
        phoneticAlphabet.put("echo", "e");
        phoneticAlphabet.put("foxtrot", "f");
        phoneticAlphabet.put("golf", "g");
        phoneticAlphabet.put("hotel", "h");
        phoneticAlphabet.put("india", "i");
        phoneticAlphabet.put("juliet", "j");
        phoneticAlphabet.put("kilo", "k");
        phoneticAlphabet.put("lima", "l");
        phoneticAlphabet.put("mike", "m");
        phoneticAlphabet.put("november", "n");
        phoneticAlphabet.put("oscar", "o");
        phoneticAlphabet.put("papa", "p");
        phoneticAlphabet.put("quebec", "q");
        phoneticAlphabet.put("romeo", "r");
        phoneticAlphabet.put("sierra", "s");
        phoneticAlphabet.put("tango", "t");
        phoneticAlphabet.put("uniform", "u");
        phoneticAlphabet.put("victor", "v");
        phoneticAlphabet.put("whiskey", "w");
        phoneticAlphabet.put("x-ray", "x");
        phoneticAlphabet.put("yankee", "y");
        phoneticAlphabet.put("zulu", "z");
        phoneticAlphabet.put("dot", "."); // For dictating websites
    }

    public static String getPhoneticLetter(String key) {
        return phoneticAlphabet.get(key);
    }


    public static final String[] standbyDictionary = {
            COMMAND_EXIT,
            COMMAND_PREDEFINED,
            COMMAND_DICTATE
    };

    public static final String[] listenPredefinedDictionary = concat(
            sites.keySet().toArray(new String[sites.keySet().size()]),
            new String[]{
                    COMMAND_EXIT,
                    COMMAND_START
            }
    );

    public static final String[] listenDictatedDictionary = concat(
            phoneticAlphabet.keySet().toArray(new String[phoneticAlphabet.keySet().size()]),
            new String[]{
                    COMMAND_EXIT,
                    COMMAND_START,
                    COMMAND_OKAY
            }
    );

    public static final String[] confirmDictatedDictionary = {
            COMMAND_EXIT,
            COMMAND_START,
            COMMAND_OKAY,
            COMMAND_NO
    };

    public static String[] concat(String[] first, String[] second) {
        List<String> both = new ArrayList<>(first.length + second.length);
        Collections.addAll(both, first);
        Collections.addAll(both, second);
        return both.toArray(new String[both.size()]);
    }
}
