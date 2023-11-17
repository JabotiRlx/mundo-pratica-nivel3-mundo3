/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadastrobd.model.util;

/**
 *
 * @author sendi
 */
import java.util.HashMap;
import java.util.Map;

public class SequenceManager {
    private Map<String, Integer> sequences;

    public SequenceManager() {
        this.sequences = new HashMap<>();
    }
    
    public int getValue(String sequenceName) {
        if (!sequences.containsKey(sequenceName)) {
            sequences.put(sequenceName, 1);
            return 1;
        }

        int nextValue = sequences.get(sequenceName);
        nextValue++;
        sequences.put(sequenceName, nextValue);
        return nextValue;
    }
}
