package pl.edu.agh.toik.vb4nao.http;

import java.util.HashMap;

/**
 * Created by lopiola on 18.05.14.
 */
public class PredefinedWebsites {
    private static HashMap<String, String> sites = new HashMap<>();
    static {
        sites.put("ted", "http://ted.com");
        sites.put("apple", "http://apple.com");
        sites.put("stack", "http://stackoverflow.com");
        sites.put("bbc", "http://bbc.co.uk");
    }

    public static String get(String key) {
        return sites.get(key);
    }
}
