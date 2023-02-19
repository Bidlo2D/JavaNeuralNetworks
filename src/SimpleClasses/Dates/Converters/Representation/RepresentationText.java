package SimpleClasses.Dates.Converters.Representation;

import SimpleClasses.ComputingUnits.INeuron;
import SimpleClasses.Signal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RepresentationText {
    public static Signal BagOfWords(String[] words, int answer){
        HashMap<String, Double> wordToCount = new HashMap<>();
        for (String word : words) {
            if (!wordToCount.containsKey(word))
            {
                wordToCount.put(word, 0.0);
            }
            wordToCount.put(word, wordToCount.get(word) + 1.0);
        }
        Signal<INeuron> signal = new Signal(wordToCount.size(),1,1, answer);
        List<Double> listValues = new ArrayList(wordToCount.values());
        signal.setValueVector(listValues);
        return signal;
    }

    public static Signal OneHotEncodingWords(String[] words, int answer){
        return null;
    }
    public static Signal OneHotEncodingWord(String word){

        return null;
    }
}
