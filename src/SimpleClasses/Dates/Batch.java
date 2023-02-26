package SimpleClasses.Dates;

import SimpleClasses.Signal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Batch implements Serializable  {
    public List<MiniBatch> miniBatches = new ArrayList();

    public Batch(){}
    // Images to signals
    public Batch(List<Signal> dates, int sizeMini) {
        int countMini = dates.size() / sizeMini;
        int d = 0;
        // add general
        for(int m = 0; m < countMini; m++){
            MiniBatch miniBatch = new MiniBatch();
            for(int s = 0; s < sizeMini; s++){
                miniBatch.signals.add(dates.get(d)); d++;
            }
            miniBatches.add(miniBatch);
        }
        // add remains
        if(d < dates.size()){
            MiniBatch miniBatch = new MiniBatch();
            for(;d < dates.size(); d++){
                miniBatch.signals.add(dates.get(d)); d++;
            }
            miniBatches.add(miniBatch);
        }
        System.out.println();
    }

    public List<Double> FlatBatch(){
        List<Double> list = new ArrayList<>();
        for(int m = 0; m < miniBatches.size(); m++) {
            for(int s = 0; s < miniBatches.get(m).signals.size(); s++){
                var values = miniBatches.get(m).signals.get(s).getValueVector();
                list.addAll(values);
            }
        }
        return list;
    }

}
