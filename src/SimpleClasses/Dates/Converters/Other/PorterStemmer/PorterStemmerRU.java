package SimpleClasses.Dates.Converters.Other.PorterStemmer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PorterStemmerRU implements PorterStemmer{

    String PERFECTIVE_GERUNDS ="((ив|ивши|ившись|ыв|ывши|ывшись)|((?<=[ая])(в|вши|вшись)))$";
    String ADJECTIVE ="(ее|ие|ые|ое|ими|ыми|ей|ий|ый|ой|ем|им|ым|ом|его|ого|ему|ому|их|ых|ую|юю|ая|яя|ою|ею)$";
    String PARTICIPLE ="((ивш|ывш|ующ)|((?<=[ая])(ем|нн|вш|ющ|щ)))$";
    String REFLEXIVES ="(с[яь])$";
    String VERB ="((ила|ыла|ена|ейте|уйте|ите|или|ыли|ей|уй|ил|ыл|им|ым|ен|ило|ыло|ено|ят|ует|уют|ит|ыт|ены|ить|ыть|ишь|ую|ю)|((?<=[ая])(ла|на|ете|йте|ли|й|л|ем|н|ло|но|ет|ют|ны|ть|ешь|нно)))$";
    String NOUN ="(а|ев|ов|ие|ье|е|ьё|иями|ями|ами|еи|ии|и|ией|ей|ой|ий|й|иям|ям|ием|ем|ам|ом|о|у|ах|иях|ях|ы|ь|ию|ью|ю|ия|ья|я)$";
    String SUPERLATIVE ="(ейш|ейше)$";
    String DERIVATIONAL ="(ост|ость)$";
    String I ="(и)$";
    String NN ="(нн)$";
    String SOFT_SIGN ="(ь)$";
    
    @Override
    public String StemWord(String word) {
        String stem = word.toLowerCase(Locale.getDefault());
        StringBuilder zText = new StringBuilder (stem);
        StemStep1(zText);
        StemStep2(zText);
        StemStep3(zText);
        StemStep4(zText);
        var result = zText.toString().replace("ё", "е");
        return result;
    }

    public void StemStep1(StringBuilder input) {
        if(!RemoveEnding(PERFECTIVE_GERUNDS, input))
        {
            RemoveEnding(REFLEXIVES, input);
            if(!RemoveEnding(PARTICIPLE, input) ||
                    RemoveEnding(ADJECTIVE, input)){
                if(!RemoveEnding(VERB, input)){
                    RemoveEnding(NOUN, input);
                }
            }
        }
    }

    public void StemStep2(StringBuilder input) {
        RemoveEnding(I, input);
    }

    public void StemStep3(StringBuilder input) {
        RemoveEnding(DERIVATIONAL, input);
    }

    public void StemStep4(StringBuilder input) {
        if(RemoveEnding(NN, input)){
            input.append("н");
        }
        RemoveEnding(SUPERLATIVE, input);
        RemoveEnding(SOFT_SIGN, input);
    }

    private boolean RemoveEnding(String regex, StringBuilder input) {
        Matcher m = Pattern.compile(regex).matcher(input.toString());
        if (m.find()) {
            int indexS= m.toMatchResult().start();
            int indexE= m.toMatchResult().end();
            input.delete(indexS, indexE);
            return true;
        }
        return false;
    }
}
