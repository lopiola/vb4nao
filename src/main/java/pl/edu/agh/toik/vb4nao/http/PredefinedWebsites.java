package pl.edu.agh.toik.vb4nao.http;

import java.util.HashMap;

/**
 * Created by lopiola on 18.05.14.
 */
public class PredefinedWebsites {
    private static HashMap<String, String> sites = new HashMap<>();
    static {
        sites.put("w3c", "http://w3c.org");
        sites.put("apple", "http://apple.com");
        sites.put("onet", "http://onet.pl");
        sites.put("bbc", "http://bbc.co.uk");
    }

    public static String get(String key) {
        return sites.get(key);
    }
}
