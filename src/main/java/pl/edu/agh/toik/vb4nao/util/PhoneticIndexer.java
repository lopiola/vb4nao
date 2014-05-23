package pl.edu.agh.toik.vb4nao.util;

import pl.edu.agh.toik.vb4nao.nao.NAODictionary;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by lopiola on 23.05.14.
 */

// Allows indexing with NATO phonetic alphabet, like
// alpha -> 0
// bravo -> 1
// ...
public class PhoneticIndexer {
    private String[] keys;
    private HashMap<String, Integer> mapping;
    private int size;

    public PhoneticIndexer(int size) {
        if (size > 26) throw new IllegalArgumentException("size cannot be bigger than 26");
        mapping = new HashMap<>();

        this.size = size;
        keys = new String[size];
        LinkedList<String> keySet = new LinkedList<>(NAODictionary.phoneticAlphabet.keySet());
        keySet.remove("dot");
        Collections.sort(keySet);
        for (int i = 0; i < size; i++) {
            String current = keySet.removeFirst();
            keys[i] = current;
            mapping.put(current, i);
        }
    }

    public String identifierFor(int i) {
        return keys[i];
    }

    public int indexOfIdentifier(String id) {
        return mapping.get(id);
    }

    public String[] getDictionary() {
        return NAODictionary.concat(
                keys,
                new String[]{
                        NAODictionary.COMMAND_EXIT,
                        NAODictionary.COMMAND_START,
                        NAODictionary.COMMAND_AGAIN
                }
        );
    }
}
