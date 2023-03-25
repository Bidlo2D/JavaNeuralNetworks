package SimpleClasses.Dates.Converters;

import SimpleClasses.ComputingUnits.INeuron;
import SimpleClasses.Dates.Converters.Enums.FormatText;
import SimpleClasses.Dates.Converters.Enums.TokenType;
import SimpleClasses.Dates.Converters.Exceptions.NoDirectoryException;
import SimpleClasses.Dates.Converters.Other.Normalization;
import SimpleClasses.Dates.Converters.Other.PorterStemmer.PorterStemmer;
import SimpleClasses.Dates.Converters.Other.RangeNorm;
import SimpleClasses.Dates.Converters.Representation.RepresentationText;
import SimpleClasses.ComputingUnits.NeuronFC;
import SimpleClasses.Signal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConverterText<L extends PorterStemmer> {
    public List<Signal> dates = new ArrayList();
    private int maxlen;
    private PorterStemmer porterStemmer;
    private TokenType type;
    private RangeNorm range;

    public ConverterText(String pathDir, TokenType type, PorterStemmer porterStemmer, RangeNorm range, int maxlen) throws NoDirectoryException, ClassNotFoundException {
        this.type = type;
        this.maxlen = maxlen;
        this.porterStemmer = porterStemmer;
        this.range = range;
        File dir = new File(pathDir);
        if (!dir.isDirectory()) {
            throw new NoDirectoryException("Путь не является директорией!");
        }
        File[] files = dir.listFiles();

        for(int b = 0; b < files.length; b++) {
            if(isText(files[b])){
                var result = padSignal(ReadText(files[b]));
                dates.add(result);
            }
        }
    }

    private Signal ReadText(File file) {
        try (FileInputStream fin = new FileInputStream(file)) {
            var name = file.getName();
            int answer = Integer.parseInt(name.split("\\.")[0].split(" - ")[1]);
            // Read text
            int i = -1;
            String Input = "";
            while((i=fin.read())!=-1){
                Input += (char)i;
            }
            // Tokenization
            switch (type) {
                case Symbol:
                    return addTokenSymbol(Input, answer);
                case Word:
                    return addTokenWord(Input, answer);
                case Proposal:
                    return addTokenProposal(Input, answer);
                default:
                    return null;
            }
        } catch (IOException e) {
            return null;
        }
    }

    private Signal addTokenWord(String str, int answer) {
        int i = 0;
        String[] words = str.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
        words = Arrays.stream(words).map(w ->  w = porterStemmer.StemWord(w)).toArray(String[]::new);
        // Bag of Words
        var respective = RepresentationText.BagOfWords(words, answer);
        Normalization.NormalSignal(respective, range);
        return respective;
    }

    private Signal addTokenSymbol(String str, int answer) {
        int i = 0;
        int size = str.toCharArray().length;
        Signal signal = new Signal(size,1,1, answer);
        for (char ch : str.toCharArray()) {
            Double s = 0.0;
            var s1 = Character.toString(ch);
            for (char ch1 : str.toCharArray()) {
                var s2 = Character.toString(ch1);
                if(s1.equalsIgnoreCase(s2)){
                    s += 1.0;
                }
            }
            signal.setValueSignal(i,0,0, s); i++;
        }
        return signal;
    }

    private Signal addTokenProposal(String str, int answer) {
        int i = 0;
        var mass = str.split("(?<=[a-z])\\.\\s+");
        Signal signal = new Signal(mass.length,1,1, answer);
        for (String word : mass) {
            Double s = 0.0;
            for (String word1 : mass) {
                if(word.equalsIgnoreCase(word1)){
                    s += 1.0;
                }
            }
            signal.setValueSignal(i,0,0, s); i++;
        }
        return signal;
    }

    private Signal padSignal(Signal input) {
        if(input.fullSize() > maxlen){
            var mass = input.getCloneSignals();
            List<INeuron> list = new ArrayList(Arrays.asList(mass));
            while(list.size() != maxlen){
                list.remove(list.size() - 1);
            }
            return new Signal(list, input.right);
        }
        if(input.fullSize() < maxlen){
            var mass = input.getCloneSignals();
            List<INeuron> list = new ArrayList(Arrays.asList(mass));
            while(list.size() < maxlen){
                list.add(new NeuronFC(0));
            }
            return new Signal(list, input.right);
        }
        return input;
    }

    private boolean isText(File file) {
        for (FormatText format : FormatText.values()) {
            if (file.getName().endsWith("." + format.toString())) {
                return true;
            }
        }
        return false;
    }
}
