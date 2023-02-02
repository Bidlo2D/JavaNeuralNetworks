package SimpleClasses.Dates.Converters;

import SimpleClasses.Dates.Converters.Enums.FormatText;
import SimpleClasses.Dates.Converters.Enums.LanguageStemmer;
import SimpleClasses.Dates.Converters.Enums.TokenType;
import SimpleClasses.Dates.Converters.Exceptions.NoDirectoryException;
import SimpleClasses.Dates.Converters.Other.Normalization;
import SimpleClasses.Dates.Converters.Other.PorterStemmer.PorterStemmer;
import SimpleClasses.Dates.Converters.Other.PorterStemmer.PorterStemmerEN;
import SimpleClasses.Dates.Converters.Other.PorterStemmer.PorterStemmerRU;
import SimpleClasses.Dates.Converters.Other.RangeNorm;
import SimpleClasses.Signal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConverterText {
    public List<Signal> dates = new ArrayList();
    private PorterStemmer porterStemmer;
    private TokenType type;
    private RangeNorm range;
    private LanguageStemmer language;

    public ConverterText(String pathDir, TokenType type, LanguageStemmer language, RangeNorm range) throws NoDirectoryException {
        this.range = range;
        this.type = type;
        this.language = language;
        TypeStemmer();
        File dir = new File(pathDir);
        if (!dir.isDirectory()) {
            throw new NoDirectoryException("Путь не является директорией!");
        }
        File[] files = dir.listFiles();

        for(int b = 0; b < files.length; b++) {
            if(isText(files[b])){
                dates.add(ReadText(files[b]));
            }
        }
    }
    private void TypeStemmer(){
        switch (language){
            case EN:
                porterStemmer = new PorterStemmerEN();
                break;
            case RU:
                porterStemmer = new PorterStemmerRU();
                break;
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
        List<String> listWord = new ArrayList<>();
        List<Double> tokenWord = new ArrayList<>();
        String strStemmer = porterStemmer.StemWord(str);
        String[] words = strStemmer.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
        for (String word : words) {
            Double s = 0.0;
            if(listWord.contains(word)){ continue; }
            else{ listWord.add(word); }
            for (String word1 : words) {
                if(word.equalsIgnoreCase(word1)){
                    s += 1.0;
                }
            }
            tokenWord.add(s);
        }
        Signal signal = new Signal(tokenWord.size(),1,1, answer);
        signal.setValueVector(tokenWord);
        Normalization.NormalSignal(signal, range);
        return signal;
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
        Normalization.NormalSignal(signal, range);
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
        Normalization.NormalSignal(signal, range);
        return signal;
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
